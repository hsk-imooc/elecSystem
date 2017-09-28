package com.rupeng.elec.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 单例模式数据字典
 * 
 * @author LeeGossHK
 * 
 */
public class Dictionary {

	private static Dictionary instance;

	// map<groupKey,map<itemKey,itemValue>>
	private Map<String, Map<String, String>> dictMap;
	// map<groupKey,groupValue>
	private Map<String, String> groupMap;
	// map<itemKey,itemValue>
	private Map<String, String> itemMap;

	public Map<String, Map<String, String>> getDictMap() {
		return dictMap;
	}

	public void setDictMap(Map<String, Map<String, String>> dictMap) {
		this.dictMap = dictMap;
	}

	public Map<String, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap) {
		this.groupMap = groupMap;
	}

	public Map<String, String> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, String> itemMap) {
		this.itemMap = itemMap;
	}

	private Dictionary() {

	}

	public static synchronized Dictionary getInstance() {
		if (instance == null) {
			instance = new Dictionary();
			instance.init();
			return instance;
		}
		return instance;
	}

	/**
	 * 初始化方法，用来加载数据字典xml配置文件
	 */
	@Test
	private void init() {
		Map<String, Map<String, String>> dictMap2 = new LinkedHashMap();
		Map<String, String> groupMap2 = new LinkedHashMap<String, String>();
		Map<String, String> itemMap2 = new LinkedHashMap<String, String>();

		// 获得dictionary.xml的File对象
		String filePath = Dictionary.class.getResource("/dictionary.xml")
				.getFile();
		File file = new File(filePath);
		// 解析dictionary.xml文件
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file);
			// 根节点
			Element rootElement = document.getRootElement();

			List<Element> groupElementList = rootElement.elements();
			for (Element groupElement : groupElementList) {

				Map<String, String> tempMap = new LinkedHashMap<String, String>();

				List<Element> itemElementList = groupElement.elements();
				for (Element itemElement : itemElementList) {
					String itemKey = itemElement.attributeValue("key");
					String itemValue = itemElement.attributeValue("value");

					itemMap2.put(itemKey, itemValue);
					tempMap.put(itemKey, itemValue);
				}

				String groupKey = groupElement.attributeValue("key");
				String groupValue = groupElement.attributeValue("value");

				groupMap2.put(groupKey, groupValue);
				dictMap2.put(groupKey, tempMap);
			}

			dictMap = dictMap2;
			groupMap = groupMap2;
			itemMap = itemMap2;

			System.out.println(dictMap);
			System.out.println(groupMap);
			System.out.println(itemMap);
		} catch (DocumentException e) {
			throw new RuntimeException("parse error");
		}
	}

	/**
	 * 重新加载数据字典的方法
	 */
	public static synchronized void reload() {
		if (instance == null) {
			getInstance();
		} else {
			instance.init();
		}
	}
}
