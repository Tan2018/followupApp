package com.ry.fu.esb.medicaljournal.service;

import com.ry.fu.esb.common.exception.ESBException;
import com.ry.fu.esb.common.exception.SystemException;
import com.ry.fu.esb.common.response.ResponseData;

public interface CheckDetailsService {

    public ResponseData CheckDetails(Long lisLableNo) throws SystemException,ESBException;
}
