package com.ry.fu.net.common.request;



import com.ry.fu.net.common.utils.StringFormatUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 请求数据适配器
 */
public class RequestDataAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        return StringFormatUtils.formatXMLEscapeToCharacter(v);
    }

    @Override
    public String marshal(String v) throws Exception {
        return v;
    }
}
