package com.ry.fu.net.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: telly
 * @Description:
 * @Date: Create in 14:14 2018/2/9
 */
public class DateAdapater extends XmlAdapter<String,String> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *@Author: telly
     *@Description: XML->JAVA
     *@Date: Create in 14:17 2018/2/9
     */
    @Override
    public String unmarshal(String value) throws Exception {
        Date date = dateFormat.parse(value);
        return dateFormat.format(date);
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
