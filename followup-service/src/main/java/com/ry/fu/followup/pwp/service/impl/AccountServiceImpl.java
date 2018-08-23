package com.ry.fu.followup.pwp.service.impl;

import com.github.pagehelper.PageHelper;
import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.pwp.mapper.AccountMapper;
import com.ry.fu.followup.pwp.model.Account;
import com.ry.fu.followup.pwp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/11/29 20:24
 * @description AccountService
 */
@Service
@CacheConfig(cacheNames = "account")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account findById(Long accountId) {
        return accountMapper.findById(accountId);
    }

    /**
     * 原生分页方式，需要自己传进参数
     * @param currentPage
     * @param pageNum
     * @return
     */
    @Cacheable
    public Pagenation<List<Account>> findByPage(Integer currentPage, Integer pageNum) {
        Integer totalRow = accountMapper.gettotalRow();

        Pagenation<List<Account>> pagenation = new Pagenation(currentPage, totalRow);
        List<Account> accounts = accountMapper.findByPage(pagenation.getStartRow(), pagenation.getEndRow());
        pagenation.setResult(accounts);
        return pagenation;
    }

    /**
     * 使用PageHelper分页
     * @return
     */
    public Pagenation<List<Account>> findByPageHepler(Integer currentPage) {
        Pagenation<List<Account>> pagenation = new Pagenation();

        currentPage = currentPage == 0 ? pagenation.getCurrentPage() : currentPage;
        //PageHelper分页的后一行一定要跟着查询，不然数据会不正常，使用了ThreadLocal
        PageHelper.startPage(currentPage, pagenation.getPageSize());
        pagenation.setResult(accountMapper.selectAll());

        return pagenation;
    }

}
