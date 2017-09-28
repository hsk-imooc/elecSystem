package com.rupeng.elec.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rupeng.elec.dao.IDao;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecText;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/beans.xml")
public class DaoTest {

	@Autowired
	@Qualifier("textDao")
	private IDao textDao;

	@Autowired
	@Qualifier("userDao")
	private IDao userDao;

	@Test
	public void testAdd() {
		ElecText text = new ElecText();
		text.setTextComment("test add。。。");
		text.setTextName("abc");
		text.setTextDate(new Date());

		textDao.addOrUpdate(text);

	}

	@Test
	public void testAddAll() {
		List<ElecText> list = new ArrayList<ElecText>();

		ElecText text = new ElecText();
		text.setTextComment("test addAll。。。888");
		ElecText text2 = new ElecText();
		text2.setTextComment("test addAll。。。999");

		list.add(text);
		list.add(text2);

		textDao.addOrUpdateAll(list);
	}

	@Test
	public void testUpdate() {
		ElecText text = (ElecText) textDao
				.findById("402881e6583055920158305593c60001");
		text.setTextComment("test update。。。。");
		textDao.addOrUpdate(text);
	}

	@Test
	public void testUpdateAll() {
		ElecText text = (ElecText) textDao
				.findById("402881e6583055920158305593c60001");
		text.setTextComment("test updateAll。。。1");

		ElecText text2 = (ElecText) textDao
				.findById("402881e6582a721b01582a721fca0000");
		text2.setTextComment("test updateAll。。。2");

		List<ElecText> list = new ArrayList<ElecText>();
		list.add(text2);
		list.add(text);

		textDao.addOrUpdateAll(list);
	}

	@Test
	public void testDeleteById() {
		textDao.deleteById("402881e6582a721b01582a721fca0000");
	}

	@Test
	public void testDeleteByIds() {
		textDao.deleteAllByIds("402881e6583055920158305593c60001",
				"402881e6583055920158305593bc0000");
	}

	@Test
	public void testDelete() {
		/*
		 * ElecText text = new ElecText();
		 * text.setTextId("402881e6582ae5ae01582ae5b0bc0000");
		 * textDao.delete(text);
		 */
	}

	@Test
	public void testDeleteAll() {
		ElecText text = new ElecText();
		text.setTextId("402881e6582afa2f01582afa32460000");
		ElecText text2 = new ElecText();
		text2.setTextId("402881e6582b549a01582b549d3e0000");

		List<ElecText> list = new ArrayList<ElecText>();
		list.add(text);
		list.add(text2);

		textDao.deleteAll(list);
	}

	@Test
	public void testFindAll() {
		System.out.println(textDao.findAll());
	}

	@Test
	public void testFindByConditions() {
		Conditions conditions = new Conditions();

		conditions.addCondition("textName", null, Operator.NOT_IS);
		conditions.addCondition("textName", "aa", Operator.LIKE);
		conditions.addOrderBy("textName", false);
		conditions.addOrderBy("textId", true);

		List<ElecText> list = textDao.findByConditions(conditions);
		for (ElecText elecText : list) {
			System.out.println(elecText);
		}

		System.out.println(conditions.createWhereAndValues().getWhere()
				+ conditions.createOrderByString());
		System.out.println(Arrays.toString(conditions.createWhereAndValues()
				.getValues()));
	}

	@Test
	public void test() {
		Conditions conditions = new Conditions();
		conditions.addCondition("username", "张三", Operator.LIKE);
		int count = userDao.findCountByConditions(conditions);
		System.out.println(count);
	}

}
