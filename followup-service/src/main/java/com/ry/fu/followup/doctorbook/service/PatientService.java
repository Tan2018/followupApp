package com.ry.fu.followup.doctorbook.service;

import com.ry.fu.followup.base.model.ResponseData;
import com.ry.fu.followup.doctorbook.model.ReqInfo;
import org.json.JSONException;


/**
 * Created by Jam on 2017/12/8.
 *
 */
public interface PatientService {

    public ResponseData queryPatientsList(ReqInfo reqInfo);

    public ResponseData queryPatientStatus(ReqInfo reqInfo);

}
