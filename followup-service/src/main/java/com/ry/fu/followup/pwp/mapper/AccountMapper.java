package com.ry.fu.followup.pwp.mapper;


import com.ry.fu.followup.base.mapper.BaseMapper;
import com.ry.fu.followup.pwp.model.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/1 8:56
 * @description description
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select account_id as accountId, account_name as accountName, account_code as accountCode,account_email as accountEmail, account_mobile as accountMobile ,pwp_org.org_name as orgName from pwp_account,pwp_org where pwp_account.ORG_ID = pwp_org.org_id and account_id = #{accountId}")
    Account findById(@Param("accountId") Long accountId);

    @Select("SELECT count(*) as totalRow FROM pwp_account ")
    Integer gettotalRow();

    @Select("SELECT * FROM ( SELECT a.*, ROWNUM RN FROM (SELECT * FROM pwp_account) a  WHERE ROWNUM <= #{endNum} )WHERE RN >= #{startNum} ")
    List<Account> findByPage(@Param("startNum") Integer startNum, @Param("endNum") Integer endNum);

    Account selectAccount(@Param("accountId") Long accountId);

    Map<String, Object> selectAccount1(@Param("accountId") Long accountId);

    /**
     * 批量插入，Oralce需要设置useGeneratedKeys=false，不然报错
     *  Oracle批量插入：  insert all into table(...) values(...) into table(...) values(...) select * from dual
     *  Mysql批量插入：   insert into table(...) values(...),(...)
     * @param accounts
     * @return
     */
    @Insert("<script>" +
                "insert all " +
                "<foreach collection=\"list\" item=\"account\">" +
                "into pwp_account(account_id, account_name, account_code) " +
                "values(#{account.accountId}, #{account.accountName}, #{account.accountCode})" +
                "</foreach> SELECT * FROM DUAL" +
            "</script>")
    @Options(useGeneratedKeys = false)
    int insertAccounts(List<Account> accounts);

    /**
     * 根据主键查询一个
     * @param accountId
     * @return
     */
    @Results(id = "accountResultTest", value = {
            @Result(property = "accountId", column = "account_id", id = true),
            @Result(property = "accountName", column = "account_name", id = true),
            @Result(property = "accountCode", column = "account_code", id = true)
    })
    @Select("select account_id, account_name, account_code from pwp_account where account_id = #{accountId}")
    Account selectById(Long accountId);

    /**
     * 查询全部，引用上面的Results
     * @return
     */
    @ResultMap("accountResultTest")
    @Select("select account_id, account_name, account_code from pwp_account")
    List<Account> selectAll();

}
