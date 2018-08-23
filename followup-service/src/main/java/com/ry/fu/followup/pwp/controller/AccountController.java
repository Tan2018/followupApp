package com.ry.fu.followup.pwp.controller;

import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.pwp.model.Account;
import com.ry.fu.followup.pwp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/2 15:20
 * @description 用户列表，Swagger-demo
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 根据ID查询用户
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData getAccountById (@PathVariable(value = "accountId") Long accountId){
        ResponseData<Account> responseData = new ResponseData();
        Account account = accountService.findById(accountId);
        responseData.setMsg("获取用户ID为"+accountId+"的用户详细信息成功");
        responseData.setStatus(200);
        responseData.setData(account);
        return responseData;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getAccount(@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {
        ResponseData<Pagenation> responseData = new ResponseData();

        Pagenation<List<Account>> pagenation = accountService.findByPage(currentPage, 5);
        responseData.setStatus(200);
        responseData.setMsg("获取用户列表成功");
        responseData.setData(pagenation);
        return responseData;
    }

}
