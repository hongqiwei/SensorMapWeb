package com.hongqiwei.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongqiwei.Database;



@Controller
public class CommunityController {

	@RequestMapping(value="/community") 
	@ResponseBody
	public String community(@RequestParam("username") String username
			){
	
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
					jsonObject.put("result","fail");
					System.out.println("不存在此用户，不展示朋友圈信息");
				}
				else{
						jsonObject.put("result","success");
						System.out.println("为用户"+username+"展示朋友圈信息");
						
//						JSONArray shareArray = new JSONArray();
//						shareArray.add(list);
//						shareArray.listIterator();  

					}
							
			}
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return jsonObject.toString();
		
	}
	
	
	@RequestMapping(value="/getShareList") 
	@ResponseBody
	public String getShareList(@RequestParam("username") String username){
		
		Database db = new Database();
		Connection conn = db.getConn();	
		JSONObject jsonObject = new JSONObject();	
	
		try{		
			Statement s = conn.createStatement();
			String query = "select * from share";
			ResultSet shareRS = s.executeQuery(query);
			System.out.println("社区分享数据："+shareRS);		
			//List<Object> list = new ArrayList<Object>();  
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
	
			while(shareRS.next()) {		
				Map<String, Object> map = new HashMap<String, Object>(); 
				
				String userName = shareRS.getString("username");
				String sDate = shareRS.getString("sdate");
				String roadName = shareRS.getString("roadname");
				String sensorData = shareRS.getString("sensordata");
				String mDate = shareRS.getString("mdate");
				System.out.print("share: " + userName + sDate + roadName + sensorData + mDate );
				
				map.put("user_name", userName);
				map.put("share_date", sDate);
				map.put("road_name", roadName);
				map.put("sensor_date", sensorData);
				map.put("measure_date", mDate);
				System.out.print("map:" +map);
				list.add(map);
				System.out.print("list:" +list);
			}	
				jsonObject.put("share_list", list);
				jsonObject.put("result","success");
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		    
		
		return jsonObject.toString();
		//return jsonObject.toJSONString();
	}
}
