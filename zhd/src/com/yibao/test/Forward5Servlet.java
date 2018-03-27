package com.yibao.test;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

/**
 * 转发快钱支付
 * 
 * @author zhanghaodong
 * @version v1.0
 * @date 2017年12月5日 下午4:12:25
 * @work
 */
@WebServlet("/Forward5Servlet")
public class Forward5Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String url = "http://10.0.11.32:6789/kuaiqian/receive";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			InputStream outIn = send(request.getInputStream());
			IOUtils.copy(outIn, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private InputStream send(InputStream in) throws Exception {

		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("utf-8");
		// 设置超时
		client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		PostMethod post = new PostMethod(url);
		post.setRequestEntity(new InputStreamRequestEntity(in));
		client.executeMethod(post);
		return post.getResponseBodyAsStream();
	}

}
