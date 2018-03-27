package com.huanxun.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet("/FailServlet")
public class FailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("ok");
		String params = ServletUtils.getParameters(request);
		System.out.println("fail:" + params);
		File file = new File("/root/zhd-fail.log");
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream output = new FileOutputStream(file,true);
		IOUtils.write(params, output);
		System.out.println("end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
