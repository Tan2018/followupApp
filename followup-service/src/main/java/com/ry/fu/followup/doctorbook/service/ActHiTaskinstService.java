package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;

/**
 * Created by Tasher on 2018/06/19.
 *
 */
public interface ActHiTaskinstService {

    /**
     *
     * @param reqInfo
     * @return
     */
    public ResponseData queryActHiTaskinstctByProsId(ReqInfo reqInfo);

}
