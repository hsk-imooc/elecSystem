package com.rupeng.elec.test;

import org.junit.Test;

import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.dao.util.Conditions.WhereAndValues;

public class ConditionTest {

	public static void main(String[] args) {

		Conditions conditions = new Conditions();
		conditions.addCondition("textName", "a", Operator.LIKE);
		conditions.addCondition("textId", "402881e6582fd66301582fd672070000",
				Operator.EQUAL);
		WhereAndValues wv = conditions.createWhereAndValues();
		System.out.println(wv.getWhere());
		for (Object value : wv.getValues()) {
			System.out.println(value);
		}
	}

	@Test
	public void test() {
		Conditions conditions = new Conditions();
		conditions.addOrderBy("textName", false);
		conditions.addOrderBy("textId", true);
		System.out.println(conditions.createOrderByString());
	}

}
