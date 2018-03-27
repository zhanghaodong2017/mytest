package com.yibao.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yeepay.HttpClinet.HttpClientUtils;

@WebServlet("/Forward2Servlet")
public class Forward2Servlet extends HttpServlet {
	static File tempPath;

	static {
		File upload = new File("upload");
		if (!upload.exists()) {
			upload.mkdirs();
		}
		tempPath = new File("temp");
		if (!tempPath.exists()) {
			tempPath.mkdirs();
		}
	}

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			System.out.println("MultiPart方式上送参数与文件处理开始~~~~~!");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(40960); // 设置缓冲区大小，这里是4kb
			factory.setRepository(tempPath); // 设置缓冲区目录

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			upload.setSizeMax(1024 * 1024 * 4); // 设置文件大小
			Map<String, String> paramMap = new HashMap<>();
			List<File> listFile = new ArrayList<File>();
			List<FileItem> items = upload.parseRequest(request); // 得到所有的文件
			for (FileItem fileItem : items) {
				// 表单数据
				if (fileItem.isFormField()) {
					paramMap.put(fileItem.getFieldName(), fileItem.getString());
					// 非表单数据 - 文件数据
				} else {
					String fileName = fileItem.getName();
					System.out.println("接收到上传文件--" + fileName);
					if (fileName != null) {
						File savedFile = new File("upload//", fileName);
						// 将文件写入临时
						fileItem.write(savedFile);
						listFile.add(savedFile);
					}
				}
			}
			System.out.println(paramMap);
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("data", paramMap.get("data"));
			String resp = HttpClientUtils.postData(paramMap.get("url"), dataMap, listFile);
			// 打印回应
			System.out.println(resp);
			response.getOutputStream().write(resp.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
