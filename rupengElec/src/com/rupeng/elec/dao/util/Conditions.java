package com.rupeng.elec.dao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装查询条件
 * 
 * @author LeeGossHK
 * 
 */
public class Conditions {

	/**
	 * 多个排序条件的封装
	 * 
	 * orderby username desc,age asc;
	 */
	private List<OrderBy> orderByList = new ArrayList<OrderBy>();

	/**
	 * 封装排序条件
	 * 
	 * @author LeeGossHK
	 * 
	 */
	class OrderBy {
		private String key;
		private boolean isAsc;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public boolean isAsc() {
			return isAsc;
		}

		public void setAsc(boolean isAsc) {
			this.isAsc = isAsc;
		}

		public OrderBy(String key, boolean isAsc) {
			super();
			this.key = key;
			this.isAsc = isAsc;
		}

		public OrderBy() {
			super();
		}

	}

	/**
	 * 添加排序条件的方法
	 * 
	 * @param key
	 * @param isAsc
	 */
	public void addOrderBy(String key, boolean isAsc) {
		if (key == null || key.trim().length() == 0) {
			return;
		}
		orderByList.add(new OrderBy(key, isAsc));
	}

	/**
	 * 创建排序语句的方法
	 * 
	 * @return
	 */
	public String createOrderByString() {
		if (orderByList.size() == 0) {
			return "";
		}
		StringBuilder obs = new StringBuilder(" order by ");
		for (OrderBy orderBy : orderByList) {
			String key = orderBy.getKey();
			boolean isAsc = orderBy.isAsc();

			obs.append(key).append(isAsc ? " asc" : " desc").append(",");
		}

		return obs.substring(0, obs.length() - 1);

	}

	/**
	 * 一个where语句可以包含多个查询条件，定义一个Condition的集合
	 */
	private List<Condition> conditions = new ArrayList<Condition>();

	/**
	 * 封装运算符的枚举 = != > >= < <= like is not is
	 * 
	 * @author LeeGossHK
	 */
	public enum Operator {
		EQUAL, NOT_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, LIKE, IS, NOT_IS
	}

	/**
	 * 封装单个查询条件
	 * 
	 * @author LeeGossHK
	 * 
	 */
	class Condition {

		private String key;
		private Object value;
		private Operator operator;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public Operator getOperator() {
			return operator;
		}

		public void setOperator(Operator operator) {
			this.operator = operator;
		}

		public Condition(String key, Object value, Operator operator) {
			super();
			this.key = key;
			this.value = value;
			this.operator = operator;
		}

		public Condition() {
			super();
		}

	}

	/**
	 * 封装where语句和占位符的value
	 * 
	 * @author LeeGossHK
	 * 
	 */
	public class WhereAndValues {
		private String where;
		private Object[] values;

		public String getWhere() {
			return where;
		}

		public void setWhere(String where) {
			this.where = where;
		}

		public Object[] getValues() {
			return values;
		}

		public void setValues(Object[] values) {
			this.values = values;
		}

		public WhereAndValues(String where, Object[] values) {
			super();
			this.where = where;
			this.values = values;
		}

		public WhereAndValues() {
			this.where = "";
			this.values = null;
		}

	}

	/**
	 * 添加条件的方法，添加到条件集合的条件必须是严格有效的条件
	 * 
	 * @param key
	 * @param value
	 * @param operator
	 */
	public void addCondition(String key, Object value, Operator operator) {
		// key为空或者长度为0，条件无效
		if (key == null || key.trim().length() == 0) {
			return;
		}
		// value为空，但是操作符不是is或者not is，条件无效
		if (value == null) {
			if (operator != Operator.IS && operator != Operator.NOT_IS) {
				return;
			}
		}
		// value不为空且是字符串，如果value长度为0，条件无效
		if (value != null && value instanceof String) {
			String v = (String) value;
			if (v.trim().length() == 0) {
				return;
			}
		}
		// 操作符为空，条件无效
		if (operator == null) {
			return;
		}

		conditions.add(new Condition(key, value, operator));
	}

	/**
	 * 根据conditions生成where语句和占位符的value的方法
	 * 
	 * @return
	 */
	public WhereAndValues createWhereAndValues() {
		WhereAndValues wv = new WhereAndValues();
		// 如果没有条件，直接返回初始化的wv
		if (conditions.size() == 0) {
			return wv;
		}
		// 有条件则遍历条件
		StringBuilder where = new StringBuilder(" where ");

		List<Object> values = new ArrayList<Object>();
		// 遍历每一个condition
		for (Condition condition : conditions) {
			String key = condition.getKey();
			Object value = condition.getValue();
			Operator operator = condition.getOperator();

			switch (operator) {
			case EQUAL:
				where.append(key).append(" = ?");
				values.add(value);
				break;
			case NOT_EQUAL:
				where.append(key).append(" != ?");
				values.add(value);
				break;
			case GREATER:
				where.append(key).append(" > ?");
				values.add(value);
				break;
			case GREATER_EQUAL:
				where.append(key).append(" >= ?");
				values.add(value);
				break;
			case LESS:
				where.append(key).append(" < ?");
				values.add(value);
				break;
			case LESS_EQUAL:
				where.append(key).append(" <= ?");
				values.add(value);
				break;
			case LIKE:
				where.append(key).append(" like ?");
				values.add("%" + value + "%");
				break;
			case IS:
				where.append(key).append(" is null");
				break;
			case NOT_IS:
				where.append(key).append(" is not null");
				break;
			}
			where.append(" and ");
		}

		wv.setWhere(where.substring(0, where.length() - 5));
		wv.setValues(values.toArray());
		return wv;
	}
}
