package com.ry.fu.esb.common.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：Boven
 * @Description ：
 * @create ： 2018-02-09 10:21
 **/
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {

    @Override
    public Timestamp unmarshal(Date v) throws Exception {

        return null;
    }

    @Override
    public Date marshal(Timestamp v) throws Exception {
        return null;
    }
}
