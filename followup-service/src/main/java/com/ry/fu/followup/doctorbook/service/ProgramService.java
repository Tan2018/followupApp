package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;

/**
 * Created by Jam on 2017/12/11.
 *
 */
public interface ProgramService {

   /* ResponseData queryProgramDetailByGroupId(ReqInfo reqInfo);*/

   public ResponseData queryProgramDetailByGroupId(ReqInfo reqInfo);


   public ResponseData queryProgramByGroupId(ReqInfo reqInfo) throws Exception;


}
