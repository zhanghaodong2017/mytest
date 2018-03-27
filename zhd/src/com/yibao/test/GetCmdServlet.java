package com.yibao.test;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetCmdServlet")
public class GetCmdServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println(getIpAddr(request));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Map<String, String[]> map = request.getParameterMap();
			for (String key : map.keySet()) {
				System.out.println("key:" + key);
				System.out.println("value:" + map.get(key)[0]);
			}
			response.getOutputStream().write("ok1".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public String getIpAddr(HttpServletRequest request) {
		    String ip = request.getHeader("x-forwarded-for");
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getHeader("WL-Proxy-Client-IP");
		    }
		    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
		        ip = request.getRemoteAddr();
		    }
		    return ip;
		 }

}
