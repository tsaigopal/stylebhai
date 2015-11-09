package com.wacaw.stylebhai.spring;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

@Component("transactionManagerFactoryBean")
public class TransactionManagerFactoryBean  implements FactoryBean<JpaTransactionManager>, BeanFactoryAware {
	
	private JpaTransactionManager transactionManager;
	
	@Autowired
	private EntityManagerFactory emFactory;
	
	@Override
	public JpaTransactionManager getObject() throws Exception {
		if (transactionManager == null) {
			transactionManager = new JpaTransactionManager(emFactory);
		}
		return transactionManager;
	}

	@Override
	public Class<?> getObjectType() {
		return JpaTransactionManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory)
			throws BeansException {
		try {
			transactionManager = beanFactory.getBean(JpaTransactionManager.class);
		} catch (Exception e) {
			//not found
		}
		System.out.println("trxMananger:" + emFactory);
	}
}
