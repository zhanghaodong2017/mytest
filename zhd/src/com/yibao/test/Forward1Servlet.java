package com.yibao.test;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

@WebServlet("/Forward1Servlet")
public class Forward1Servlet extends HttpServlet {
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
			String service = request.getParameter("service");
			String req = request.getParameter("req");
			String sign = request.getParameter("sign");
			InputStream outIn = doPost(url, service, req, sign);
			IOUtils.copy(outIn, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private InputStream doPost(String url, String service, String req, String sign) throws Exception {

		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("utf-8");
		// 设置超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
		PostMethod post = new PostMethod(url);
		post.addParameter("service", service);
		post.addParameter("req", req);
		post.addParameter("sign", sign);
		client.executeMethod(post);
		return post.getResponseBodyAsStream();
	}

}
