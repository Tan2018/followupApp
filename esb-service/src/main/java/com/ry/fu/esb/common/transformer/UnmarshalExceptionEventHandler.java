package com.ry.fu.esb.common.transformer;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * JAXB2 unmarshal操作抛出异常的处理器
 * 
 * <P> 返回false表示将异常继续抛出
 */
public class UnmarshalExceptionEventHandler implements ValidationEventHandler {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		return false;
	}

}
