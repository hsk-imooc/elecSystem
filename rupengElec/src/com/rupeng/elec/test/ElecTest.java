package com.rupeng.elec.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rupeng.elec.domain.ElecText;
import com.rupeng.elec.service.ElecTextService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/beans.xml")
public class ElecTest {

	public static void main(String[] args) {

		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		ElecText text = new ElecText();
		text.setTextComment("简单测试。。。");
		text.setTextName("zhangsan");
		text.setTextDate(new Date());

		session.save(text);

		tx.commit();
		session.close();
		sessionFactory.close();
	}

	@Autowired
	@Qualifier("elecText")
	private ElecText elecText;

	@Test
	public void springTest() {

		System.out.println(elecText.getTextName());

	}

	@Test
	public void springTest2() {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecText text = (ElecText) applicationContext.getBean("elecText");
		System.out.println(text.getTextName());
	}

	@Test
	public void springHibernateTest() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");

		ElecText text = new ElecText();
		text.setTextComment("简单测试。。。");
		text.setTextName("lisi");
		text.setTextDate(new Date());

		hibernateTemplate.save(text);
	}

	@Test
	public void daoTest1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecTextService textService = (ElecTextService) applicationContext.getBean("textService");

		ElecText text = new ElecText();
		text.setTextComment("简单测试。。。");
		text.setTextName("wangwu");
		text.setTextDate(new Date());

		textService.add(text);
	}

	@Test
	public void daoTest2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		ElecTextService textService = (ElecTextService) applicationContext.getBean("textService");

		ElecText text = textService.findById("402881e6582b549a01582b549d3e0000");
		System.out.println(text.getTextName());
	}
}
