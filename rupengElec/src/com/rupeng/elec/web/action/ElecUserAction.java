package com.rupeng.elec.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rupeng.elec.dao.util.Conditions;
import com.rupeng.elec.dao.util.Conditions.Operator;
import com.rupeng.elec.domain.ElecUser;
import com.rupeng.elec.service.ElecUserService;
import com.rupeng.elec.util.DataTablesPageBean;
import com.rupeng.elec.util.Dictionary;
import com.rupeng.elec.util.POIUtils;

/**
 * 用户action
 * 
 * @author LeeGossHK
 * 
 */
public class ElecUserAction extends ActionSupport implements ModelDriven<ElecUser> {

	private InputStream inputStream;

	private String rememberMe;// 接收记住我

	private String checkCode;// 接收验证码

	private String sEcho; // 客户端请求次数,对服务器端无实际意义,但服务器需按原值返回给客户端
	private int iDisplayStart; // 要显示的第一条数据在数据库中的索引
	private int iDisplayLength; // 每页显示的数据条数

	private ElecUserService userService;
	private ElecUser user = new ElecUser();

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public ElecUser getModel() {
		return user;
	}

	public void setUserService(ElecUserService userService) {
		this.userService = userService;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * 显示userHome.jsp
	 * 
	 * @return
	 */
	public String home() {
		return "home";
	}

	/**
	 * 显示userAdd.jsp
	 * 
	 * @return
	 */
	public String addPage() {
		return "addPage";
	}

	/**
	 * 添加用户的方法
	 * 
	 * @return
	 */
	public String add() {
		user.setIsDelete(false);
		userService.add(user);
		return "addSuccess";
	}

	/**
	 * 检查登陆账号唯一性
	 * 
	 * @return
	 * @throws IOException
	 */
	public String checkAccountUnique() throws IOException {
		String account = user.getAccount();
		Boolean isUnique = userService.checkAccountUnique(account);
		ServletActionContext.getResponse().getWriter().write(isUnique.toString());
		return NONE;
	}

	/**
	 * 分也显示数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public String page() throws IOException {
		Conditions conditions = new Conditions();
		conditions.addCondition("username", user.getUsername(), Operator.LIKE);

		/**
		 * 生成pageBean
		 */
		DataTablesPageBean<ElecUser> pageBean = userService.page(sEcho, iDisplayStart, iDisplayLength, conditions);

		/**
		 * 生成json字符串响应给浏览器
		 */
		System.out.println(pageBean);
		Gson gson = new Gson();
		String jsonResult = gson.toJson(pageBean);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(jsonResult);
		System.out.println(jsonResult);
		return NONE;
	}

	/**
	 * 删除用户的方法
	 * 
	 * @return
	 */
	public String delete() {
		userService.delete(user);
		return "deleteSuccess";
	}

	/**
	 * 显示修改页面，回显数据
	 * 
	 * @return
	 */
	public String userEdit() {
		user = userService.findById(user.getUserId());
		return "userEdit";
	}

	/**
	 * 更新用户信息的方法
	 * 
	 * @return
	 */
	public String update() {
		userService.update(user);
		return "updateSuccess";
	}

	/**
	 * 登陆的方法
	 * 
	 * @return
	 */
	public String login() {

		/**
		 * 验证码校验
		 */
		/*
		 * String _checkCode = (String) ActionContext.getContext().getSession()
		 * .get("checkCode"); if (_checkCode == null ||
		 * _checkCode.trim().length() == 0) { this.addActionError("验证码不能为空");
		 * return "loginFail"; } if (!checkCode.equals(_checkCode)) {
		 * this.addActionError("验证码错误"); return "loginFail"; }
		 */

		/**
		 * 数据有效性检查
		 */
		if (user.getAccount() == null || user.getAccount().trim().length() == 0) {
			this.addActionError("登陆账号不能为空");
			return "loginFail";
		}
		if (user.getPassword() == null || user.getPassword().trim().length() == 0) {
			this.addActionError("密码不能为空");
			return "loginFail";
		}
		Conditions conditions = new Conditions();
		conditions.addCondition("isDelete", false, Operator.EQUAL);
		conditions.addCondition("account", user.getAccount(), Operator.EQUAL);
		conditions.addCondition("password", user.getPassword(), Operator.EQUAL);

		user = userService.login(conditions);

		/**
		 * 登陆成功之前将account和password写入cookie中
		 */
		if (user != null) {
			ActionContext.getContext().getSession().put("user", user);

			if ("yes".equals(rememberMe)) {

				String account = user.getAccount();
				String password = user.getPassword();

				Cookie accountCookie = new Cookie("account", account);
				Cookie passwordCookie = new Cookie("password", password);

				accountCookie.setMaxAge(60 * 60 * 24 * 7);
				passwordCookie.setMaxAge(60 * 60 * 24 * 7);

				accountCookie.setPath("/");
				passwordCookie.setPath("/");

				ServletActionContext.getResponse().addCookie(accountCookie);
				ServletActionContext.getResponse().addCookie(passwordCookie);
			}

			return "loginSuccess";
		}
		this.addActionError("用户名或密码错误");
		return "loginFail";
	}

	/**
	 * 导出报表的方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public String exportExecl() throws IOException {

		Conditions conditions = new Conditions();
		// conditions.addCondition("isDelete", false, Operator.EQUAL);
		conditions.addCondition("username", user.getUsername(), Operator.LIKE);
		List<ElecUser> userList = userService.findByConditions(conditions);

		/**
		 * 封装List<String[]>数据
		 */
		List<String[]> data = new ArrayList<String[]>();
		/**
		 * 先封装表头数据
		 */
		String[] titleData = new String[3];
		titleData[0] = "用户登录账号";
		titleData[1] = "用户名";
		titleData[2] = "性别";
		data.add(titleData);
		/**
		 * 封装真实数据
		 */
		if (userList != null) {
			for (ElecUser elecUser : userList) {
				String account = elecUser.getAccount();
				String username = elecUser.getUsername();
				String gender = Dictionary.getInstance().getItemMap().get(elecUser.getGender());

				String[] rowData = new String[3];
				rowData[0] = account;
				rowData[1] = username;
				rowData[2] = gender;

				data.add(rowData);
			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户报表信息.xls", "UTF-8"));
		ServletOutputStream out = response.getOutputStream();

		POIUtils.exportExecl(data, out);
		return NONE;
		/*
		 * HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet sheet =
		 * workbook.createSheet();
		 * 
		 * // 创建表格头 int rowIndex = 0; int cellIndex = 0;
		 * 
		 * HSSFRow titleRow = sheet.createRow(rowIndex); rowIndex++; HSSFCell
		 * titleCell0 = titleRow.createCell((short) cellIndex); cellIndex++;
		 * HSSFCell titleCell1 = titleRow.createCell((short) cellIndex);
		 * cellIndex++; HSSFCell titleCell2 = titleRow.createCell((short)
		 * cellIndex); cellIndex++;
		 * 
		 * titleCell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * titleCell1.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * titleCell2.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * 
		 * titleCell0.setCellValue("登陆账号"); titleCell1.setCellValue("用户名");
		 * titleCell2.setCellValue("性别");
		 * 
		 * // 表头样式 HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		 * HSSFFont font = workbook.createFont();
		 * font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 * titleCellStyle.setFont(font);
		 * 
		 * titleCell0.setCellStyle(titleCellStyle);
		 * titleCell1.setCellStyle(titleCellStyle);
		 * titleCell2.setCellStyle(titleCellStyle);
		 * 
		 * // 写入真实数据 if (userList != null) { for (ElecUser elecUser : userList)
		 * { cellIndex = 0; HSSFRow row = sheet.createRow(rowIndex++); HSSFCell
		 * cell0 = row.createCell((short) cellIndex); cellIndex++; HSSFCell
		 * cell1 = row.createCell((short) cellIndex); cellIndex++; HSSFCell
		 * cell2 = row.createCell((short) cellIndex);
		 * 
		 * cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
		 * 
		 * cell0.setCellValue(elecUser.getAccount());
		 * cell1.setCellValue(elecUser.getUsername());
		 * cell2.setCellValue(Dictionary.getInstance().getItemMap().get(elecUser
		 * .getGender()));
		 * 
		 * } }
		 */

	}
}
