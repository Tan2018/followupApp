package com.ry.fu.net.common.seq;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/13 19:03
 * @description 自定义序列生成方式
 */
@Component
public class RedisSequenceGenerator {

    protected Interner<String> pool = Interners.newWeakInterner();

    protected Map<String,CacheSequence> seqs = new ConcurrentHashMap<String,CacheSequence>();

    @Autowired
    private RedisIncrementGenerator rig;

    private String[] patterns;

    private int order = 10;

    /**
     * 这个值在单服务器环境下，对性能提升极少，因为redis本身性能足够强大。
     * 但在集群环境下，服务器越多性能提示越明显。
     */
    private int cacheSize = 0; //内存缓存数

    private String storeName = "SQUENCES."; //存到redis中的前缀

    /**此类的业务逻辑必须处在同步锁的代码块中*/
    protected static class CacheSequence{

        private long sequence;
        private int count;

        public void set(int count, long sequence){
            this.count = count;
            this.sequence = sequence;
        }

        public boolean full(){
            return this.count > 0 ? true : false;
        }

        public Long get(){
            if(count <= 0){
                return null;
            }
            long v = this.sequence;
            this.sequence ++;
            this.count --;
            return v;
        }
    }
    public String generate(Object entity, String seqDefid) {
        String key = storeName + seqDefid;
        if(this.cacheSize > 0){
            synchronized (pool.intern(seqDefid)) {
                CacheSequence cseq = seqs.get(seqDefid);
                //存在缓存，切里面还有可以取的值
                if(cseq != null && cseq.full()){
                    return cseq.get().longValue() + "";
                }else{
                    long sv = rig.increment(key, this.cacheSize);
                    if(cseq == null){
                        cseq = new CacheSequence();
                        seqs.put(seqDefid, cseq);
                    }
                    cseq.set(this.cacheSize, sv);
                    return cseq.get() + "";
                }
            }
        }
        long sv = rig.increment(key, 1);
        return sv + "";
    }

    public List<String> generates(List<Object> entity, String seqDefid, int count) {
        //批量缓存暂不考虑缓存
        String key = storeName + seqDefid;
        List<String> ls = new ArrayList<String>();
        long sv = rig.increment(key, count);
        for(int i = 1; i < count; i++){
            ls.add(sv + "");
            sv ++;
        }
        return ls;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
