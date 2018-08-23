package com.ry.fu.esb.pwp.mapper;


import com.ry.fu.esb.common.mapper.BaseMapper;
import com.ry.fu.esb.pwp.model.Account;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @author walker
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    Account findByAccountMobile(@Param("accountMobile") String accountMobile);

    void insertAccount(@Param("accountId") Long accountId, @Param("accountPassword") String accountPassword, @Param("createTime") Date createTime, @Param("accountMobile") String accountMobile,@Param("accountCode") String accountCode);

    int updatePassword(@Param("accountMobile") String accountMobile,@Param("newPassword") String newPassword);
}
