package com.ry.fu.esb.doctorbook.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ry.fu.esb.common.response.ResponseData;
import com.ry.fu.esb.doctorbook.mapper.HandInformMapper;
import com.ry.fu.esb.doctorbook.model.HandInform;
import com.ry.fu.esb.doctorbook.service.HandInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Tom
 * @Description:
 * @create: 2018/5/7 16:17
 **/
@Service
public class HandInformServiceImpl implements HandInformService{


    @Autowired
    private HandInformMapper handInformMapper;

   /* @Override
    public ResponseData findHandInformInfo(Map<String,Object> map) {
        List<HandInform> list=handInformMapper.selectHandInformInfo(map);
        if(!(list.size()>0)){
            return new ResponseData("200","未查到数据",null);
        }
        Integer count=handInformMapper.selectAppointDoctorCount(map.get("doctorId").toString());
        if(!(count>0)){
            return new ResponseData("200","未查到数据",null);
        }
        int total=0;
        if(count%Integer.valueOf(map.get("pageSize").toString())==0){
           total=count/Integer.valueOf(map.get("pageSize").toString());
        }else{
            total=count/Integer.valueOf(map.get("pageSize").toString())+1;
        }
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (HandInform handInform:list
                    ) {

                if(handInform!=null){
                    if(handInform.getCreateDate()!=null){
                        String date=sdf.format(handInform.getCreateDate());
                        handInform.setOutCreateDate(date);
                    }
                }
            }
        }catch (Exception e){
            return new ResponseData("500","日期转换错误",null);
        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("total",total);
        resultMap.put("list",list);
        return new ResponseData("200","操作成功",resultMap);
    }*/

    @Override
    public ResponseData findHandInformInfo(Map<String,String> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageNum")),Integer.parseInt(map.get("pageSize")));
        List<HandInform> list=handInformMapper.selectHandInformInfo(map.get("doctorId"));
        PageInfo pageInfo = new PageInfo(list);
        if(!(list.size()>0)){
            return new ResponseData("200","未查到数据",null);
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("total",pageInfo.getPages());
        resultMap.put("list",list);
        return new ResponseData("200","操作成功",resultMap);
    }
}
