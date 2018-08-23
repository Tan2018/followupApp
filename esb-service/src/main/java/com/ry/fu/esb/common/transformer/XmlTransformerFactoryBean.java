package com.ry.fu.esb.common.transformer;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class XmlTransformerFactoryBean implements FactoryBean<Transformer>,InitializingBean {

	private Jaxb2Marshaller jaxb2Marshaller;
	private Transformer transformer;

	@Override
	public void afterPropertiesSet() throws Exception {
		transformer = new XmlTransformer();
		AbstractTransformer abstractTransformer = (AbstractTransformer) transformer;
		abstractTransformer.setJaxb2Marshaller(jaxb2Marshaller);
	}

	@Override
	public Transformer getObject() throws Exception {
		return this.transformer;
	}

	@Override
	public Class<?> getObjectType() {
		return XmlTransformer.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setJaxb2Marshaller(Jaxb2Marshaller jaxb2Marshaller) {
		this.jaxb2Marshaller = jaxb2Marshaller;
	}

}
