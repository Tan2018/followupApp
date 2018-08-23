package com.ry.fu.followup.pwp.service;

import com.ry.fu.followup.base.model.Pagenation;
import com.ry.fu.followup.pwp.model.Account;

import java.util.List;

/**
 * @Description
 * @Date 2017/12/7 19:16
 * @Since v1.7
 * @Autor Nick
 */
public interface AccountService {

	public Account findById(Long accountId);

	public Pagenation<List<Account>> findByPage(Integer currentPage, Integer pageNum);

	public Pagenation<List<Account>> findByPageHepler(Integer currentPage);

}
