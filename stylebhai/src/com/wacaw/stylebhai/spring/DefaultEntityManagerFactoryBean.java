package com.wacaw.stylebhai.spring;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Creates a default entity manager if already not created through spring configuration.
 * Just add a META-INF/persistence.xml and it will work.
 * 
 * @author cognizant
 */
@Component
public class DefaultEntityManagerFactoryBean implements FactoryBean<EntityManagerFactory>, BeanFactoryAware, DisposableBean {
	
	private EntityManagerFactory emFactory;
	
	@Override
	public EntityManagerFactory getObject() throws Exception {
		if (emFactory == null) {
			emFactory = Persistence.createEntityManagerFactory(null);
			
		}
		return emFactory;
	}

	@Override
	public Class<?> getObjectType() {
		return EntityManagerFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory)
			throws BeansException {
		try {
		emFactory = beanFactory.getBean(EntityManagerFactory.class);
		} catch (Exception e) 
		{
			//bean not found
		}
		
		System.out.println("emFactory:" + emFactory);
	}
	
	@Override
	public void destroy() throws Exception {
		emFactory.close();
	}
}