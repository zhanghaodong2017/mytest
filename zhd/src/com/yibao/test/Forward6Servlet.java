package com.yibao.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

/**
 * 转发苏宁支付
 * 
 * @author zhanghaodong
 * @version v1.0
 * @date 2017年12月5日 下午4:12:25
 * @work
 */
@WebServlet("/Forward6Servlet")
public class Forward6Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			
			StringBuilder builder = new StringBuilder("http://10.0.11.32:6789/suning/receive?");
			Enumeration em = request.getParameterNames();
			while (em.hasMoreElements()) {
				String name = (String) em.nextElement();
				String value = request.getParameter(name);
				builder.append(name);
				builder.append("=");
				builder.append(URLEncoder.encode(value,"UTF-8"));
				builder.append("&");
			}
			System.out.println(builder.toString());
			InputStream outIn = send(builder.toString());
			IOUtils.copy(outIn, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private InputStream send(String url) throws Exception {

		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("utf-8");
		// 设置超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		PostMethod post = new PostMethod(url);
		client.executeMethod(post);
		return post.getResponseBodyAsStream();
	}

}
