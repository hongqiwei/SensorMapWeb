package com.hongqiwei.controller;

import java.sql.Connection;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hongqiwei.Database;

@Controller
public class ShareController {
	@RequestMapping(value="/share",produces="text/html;charset=UTF-8") 
	@ResponseBody
	public Map<String, Object> share(@RequestParam("username") String username
			,@RequestParam("sharedate") String sharedate,@RequestParam("roadname") String roadname,
			@RequestParam("sensordata") String sensordata,@RequestParam("measuredate") String measuredate){
		System.out.println("username: " + username);
		
		//JSONObject jsonObject = new JSONObject();
		Map<String, Object> result = new HashMap<String, Object>();
		
		Database db = new Database();
		Connection conn = db.getConn();
		try{
			Statement s = conn.createStatement();
			//ResultSet rs = null;
			String sql = "";
			sql = "insert into share(username,sdate,roadname,sensordata,mdate) "
					+ "values('" + username + "','" + sharedate + "','" + roadname + "',"
							+ "'" + sensordata + "','" + measuredate + "')";
					System.out.print("db operation: " + sql);
					s.execute(sql);
					result.put("result","success");
					System.out.println("分享数据存储成功");
				
	
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			result.put("result","fail");
			System.out.println("分享数据存储失败");
		}			
					
		return result;
	
		}
}
