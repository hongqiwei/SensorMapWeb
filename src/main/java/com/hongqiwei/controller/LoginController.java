package com.hongqiwei.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hongqiwei.Database;
//import org.apache.log4j.Logger;
@Controller
public class LoginController{
	
	@RequestMapping(value="/login") 
	@ResponseBody
	public String login(@RequestParam("username") String username
			,@RequestParam("password") String password){
	
		JSONObject jsonObject = new JSONObject();
		
		Database db = new Database();
		Connection conn = db.getConn();
		
		
		try{
			Statement s = conn.createStatement();
			ResultSet rs = null;
			String sql = "";
			sql = "select * from user where username = '" + username + "'";
			System.out.print("db operation: " + sql);
			if(conn != null){
				rs = s.executeQuery(sql);
				if(rs == null){
					//用户不存在
					jsonObject.put("result","fail1");
					System.out.println("不存在此用户，登录失败");
				}
				else{
					while(rs.next()) {
						String psw = rs.getString("password");
						if(psw.equals(password)){
							jsonObject.put("result","success");
							jsonObject.put("access_token", "YES");
							System.out.println("登录成功");
						}
						else{
							jsonObject.put("result","fail2");
							System.out.println("密码不正确，登录失败");
						}
					}
					
				}
				
			}
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/register") 
	@ResponseBody
	public String register(@RequestParam("username") String username
			,@RequestParam("password") String password){
		System.out.println("username: " + username);
		System.out.println("username: " + username);
		JSONObject jsonObject = new JSONObject();
		
		Database db = new Database();
		Connection conn = db.getConn();
		try{
			Statement s = conn.createStatement();
			ResultSet rs = null;
			String sql = "";
			sql = "select * from user where username = '" + username + "'";
			System.out.print("db operation: " + sql);
			if(conn != null){
				rs = s.executeQuery(sql);
				if(rs.getRow() == 0){
					//jsonObject.put("result","success");
					System.out.println("不存在此用户，可以注册");
					sql = "insert into user(username,password) values('" + username + "','" + password + "')";
					System.out.print("db operation: " + sql);
					s.execute(sql);
					jsonObject.put("result","success");
					System.out.println("注册成功");
				}
				else{
					jsonObject.put("result","fail2");
					System.out.println("当前用户名已存在，注册失败");
				}
		}
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			jsonObject.put("result","fail1");
			System.out.println("注册失败");
		}			
					
		return jsonObject.toString();
	
		}
}

