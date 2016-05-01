package com.hongqiwei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController{
	
	@RequestMapping(value="/login") 
	@ResponseBody
	public String login(@RequestParam("username") String username
			,@RequestParam("password") String password){
		if("user".equals(username)&&"123".equals(password)){
			return "Hello world";
		}else{
			return "fail";
		}
	}

}
