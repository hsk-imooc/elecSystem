package com.rupeng.elec.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.rupeng.elec.util.Dictionary;

/**
 * 初始化数据字典
 * 
 * @author LeeGossHK
 * 
 */
public class InitListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletcontextevent) {
		Dictionary dict = Dictionary.getInstance();
		servletcontextevent.getServletContext().setAttribute("dict", dict);
	}

	public void contextDestroyed(ServletContextEvent servletcontextevent) {

	}

}
