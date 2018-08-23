package com.ry.fu.net.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 14:14 2018/2/9
 */
public class TimeAdapater extends XmlAdapter<String,String> {

    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *@Author: telly
     *@Description: XML->JAVA
     *@Date: Create in 14:17 2018/2/9
     */
    @Override
    public String unmarshal(String value) throws Exception {
        Date date = timeFormat.parse(value);
        return timeFormat.format(date);
    }

    /**
     *@Author: telly
     *@Description: JAVA->XML
     *@Date: Create in 14:16 2018/2/9
     */
    @Override
    public String marshal(String value) throws Exception {
        return value;
    }
}
