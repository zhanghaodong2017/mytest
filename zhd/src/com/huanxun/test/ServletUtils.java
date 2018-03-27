package com.huanxun.test;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Http与Servlet工具类.
 *
 */
public class ServletUtils {

	private ServletUtils() {
	}

	public static String getParameters(HttpServletRequest request) throws IOException {
		String encoding = request.getCharacterEncoding();
		if (encoding == null) {
			encoding = "UTF-8";
		} else if (encoding.equals("")) {
			encoding = "UTF-8";
		}
		String line = IOUtils.toString(request.getInputStream(), encoding);
		if (StringUtils.isNotBlank(line)) {
			return line;
		}
		StringBuilder ret = new StringBuilder();
		Enumeration<String> enumer = request.getParameterNames();
		if (enumer.hasMoreElements()) {
			while (enumer.hasMoreElements()) {
				String paramName = enumer.nextElement();
				if (StringUtils.isNotBlank(paramName)) {
					String paramValue = request.getParameter(paramName);
					ret.append(paramName).append("=").append(paramValue).append("&");
				}
			}
		}

		return ret.toString();
	}

}
