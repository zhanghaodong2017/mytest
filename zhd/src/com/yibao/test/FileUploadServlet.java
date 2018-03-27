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

@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static File tempPath = new File("temp");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			System.out.println("MultiPart方式上送参数与文件处理开始~~~~~!");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
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
						File savedFile = new File("F://upload//", fileName);
						// 将文件写入本地
						fileItem.write(savedFile);
						listFile.add(savedFile);
					}
				}
			}
			System.out.println(paramMap);
			// 打印回应
			response.getOutputStream().write("ok2".getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
