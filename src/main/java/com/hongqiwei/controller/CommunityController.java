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
	public Map<String,String> community(@RequestParam("username") String username
			){
	
		//JSONObject jsonObject = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
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
					map.put("result","fail");
					//jsonObject.put("result","fail");
					System.out.println("不存在此用户，不展示朋友圈信息");
				}
				else{
					map.put("result","success");
					//jsonObject.put("result","success");
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
		System.out.println("\n看输出"+JSONObject.toJSONString(map)+"\n");
		return map;
		//System.out.print("\n看输出"+jsonObject.toJSONString());
		//return jsonObject.toJSONString();
	}
	
	
	@RequestMapping(value="/getShareList") 
	@ResponseBody
	public Map<String, Object> getShareList(@RequestParam("username") String username){
		
		Database db = new Database();
		Connection conn = db.getConn();	
		//JSONObject jsonObject = new JSONObject();	
		
		Map<String, Object> sharelist = new HashMap<String, Object>();
		
		Map<String, Object> resultmap = new HashMap<String, Object>();
		
		//List<Object> list = new ArrayList<Object>();  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
		
		try{		
			Statement s = conn.createStatement();
			String query = "select * from share";
			ResultSet shareRS = s.executeQuery(query);
			System.out.println("社区分享数据："+shareRS);		
			
	
			while(shareRS.next()) {		

				String userName = shareRS.getString("username");
				String sDate = shareRS.getString("sdate");
				String roadName = shareRS.getString("roadname");
				String sensorData = shareRS.getString("sensordata");
				String mDate = shareRS.getString("mdate");
				System.out.print("\nshare: " + userName + sDate + roadName + sensorData + mDate );
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user_name", userName);
				map.put("share_date", sDate);
				map.put("road_name", roadName);
				map.put("sensor_data", sensorData);
				map.put("measure_date", mDate);
				System.out.print("\nmap:" +map);
				list.add(map);
				
			}	
				System.out.print("\nlist:" +list);
			
				//sharelist.put("share_list", list);
				resultmap.put("result","success");
				resultmap.put("share_pengyouquan", list);
				conn.close();
				
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		//return jsonObject.toString();
		return resultmap ;
	}
}
