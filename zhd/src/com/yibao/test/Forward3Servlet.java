package com.yibao.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.HttpClinet.HttpClientUtils;

@WebServlet("/Forward3Servlet")
public class Forward3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Content-Type", "text/html; charset=UTF-8");

			String url = request.getParameter("url");
			String data = request.getParameter("data");
			String resp = doPost(url, data);
			response.getOutputStream().write(resp.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String doPost(String url, String data) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("data", data);
		System.out.println("url:"+url);
		System.out.println("data:"+data);
		String resp = HttpClientUtils.postData(url, dataMap);
		System.out.println("resp:"+resp);
		return resp;
	}

}
