package com.test.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Http与Servlet工具类.
 *
 */
public class ServletUtils {

	private ServletUtils() {
	}

	/**
	 * 有条件使用，XML和JSON的所有子节点不能有重复，否则会覆盖 zhd
	 * 
	 * @param map
	 * @param json
	 * @throws DocumentException
	 */
	public static Map<String, String> getParameters(HttpServletRequest request) throws IOException, DocumentException {
		String encoding = request.getCharacterEncoding();
		if (encoding == null) {
			encoding = "UTF-8";
		} else if (encoding.equals("")) {
			encoding = "UTF-8";
		}
		String line = IOUtils.toString(request.getInputStream(), encoding);
		Map<String, String> ret = new HashMap<String, String>();

		if (StringUtils.isNotBlank(line)) {
			if (line.trim().startsWith("<")) {
				// XML
				SAXReader reader = new SAXReader();
				Document doc = reader.read(new ByteArrayInputStream(line.getBytes("UTF-8")));
				insertMapByElement(ret, doc.getRootElement());
			} else if (line.trim().startsWith("{")) {
				// JSONObject
				JSONObject json = JSONObject.fromObject(line);
				insertMapByJSONObject(ret, json);
			} else if (line.trim().startsWith("[")) {
				// JSONArray
				JSONArray json = JSONArray.fromObject(line);
				insertMapByJSONArray(ret, json);
			} else if (line.contains("{") && line.contains("}")) {
				line = line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1);
				JSONObject json = JSONObject.fromObject(line);
				insertMapByJSONObject(ret, json);
			} else if (line.contains("<") && line.contains(">")) {
				line = line.substring(line.indexOf("<"), line.lastIndexOf(">") + 1);
				SAXReader reader = new SAXReader();
				Document doc = reader.read(new ByteArrayInputStream(line.getBytes("UTF-8")));
				insertMapByElement(ret, doc.getRootElement());
			} else {
				String[] kvs = line.split("&");
				for (String kv : kvs) {
					String[] kva = kv.split("=");
					if (kva.length == 2) {
						ret.put(kva[0], kva[1]);
					}
				}
			}

		}
		Enumeration<String> enumer = request.getParameterNames();
		if (enumer.hasMoreElements()) {
			while (enumer.hasMoreElements()) {
				String paramName = enumer.nextElement();
				if (StringUtils.isNotBlank(paramName)) {
					String paramValue = request.getParameter(paramName);
					ret.put(paramName, paramValue);
				}
			}
		}

		return ret;
	}

	public static void insertMapByJSONObject(Map<String, String> map, JSONObject json) {
		Iterator<String> keys = json.keys();
		JSONObject jo = null;
		JSONArray ja = null;
		Object obj = null;
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			obj = json.get(key);
			if (obj instanceof JSONObject) {
				jo = (JSONObject) obj;
				insertMapByJSONObject(map, jo);
			} else if (obj instanceof JSONArray) {
				ja = (JSONArray) obj;
				insertMapByJSONArray(map, ja);
			} else if (obj != null) {
				map.put(key, obj + "");
			}
		}

	}

	private static void insertMapByJSONArray(Map<String, String> map, JSONArray json) {
		Object obj = null;
		if (json != null) {
			for (int i = 0; i < json.size(); i++) {
				obj = json.get(i);
				if (obj instanceof JSONObject) {
					insertMapByJSONObject(map, (JSONObject) obj);
				}
				if (obj instanceof JSONArray) {
					insertMapByJSONArray(map, (JSONArray) obj);
				}
			}
		}
	}

	private static void insertMapByElement(Map<String, String> map, Element element) {
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			String key = element.getName();
			String value = element.getText();
			map.put(key, value);
		} else {
			Iterator<Element> node = element.elementIterator();
			while (node.hasNext()) {
				Element child = node.next();
				insertMapByElement(map, child);
			}
		}

	}

	public static Map<String, String> getMapByParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> enumer = request.getParameterNames();
		if (enumer.hasMoreElements()) {
			while (enumer.hasMoreElements()) {
				String paramName = enumer.nextElement();
				if (StringUtils.isNotBlank(paramName)) {
					String paramValue = request.getParameter(paramName);
					params.put(paramName, paramValue);
				}
			}
		} else {
			try {
				params = getParameters(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return params;
	}

	public static void main(String[] args) {
		String line = "result={\"msg\"=123}";
		line = line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1);
		System.out.println(line);
	}

	public static Map<String, String> getMapByParams(HttpServletRequest request, String type) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> enumer = request.getParameterNames();
		if (enumer.hasMoreElements()) {
			while (enumer.hasMoreElements()) {
				String paramName = enumer.nextElement();
				if (StringUtils.isNotBlank(paramName)) {
					String paramValue = request.getParameter(paramName);
					params.put(paramName, paramValue);
				}
			}
		} else {
			try {
				params = getParameters(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return params;
	}

	public static Map<String, String> getMapByString(String line) throws IOException, DocumentException {
		Map<String, String> ret = new HashMap<String, String>();
		try {
			if (StringUtils.isNotBlank(line)) {
				if (line.trim().startsWith("<")) {
					// XML
					SAXReader reader = new SAXReader();
					Document doc = reader.read(new ByteArrayInputStream(line.getBytes("UTF-8")));
					insertMapByElement(ret, doc.getRootElement());
				} else if (line.trim().startsWith("{")) {
					// JSONObject
					JSONObject json = JSONObject.fromObject(line);
					insertMapByJSONObject(ret, json);
				} else if (line.trim().startsWith("[")) {
					// JSONArray
					JSONArray json = JSONArray.fromObject(line);
					insertMapByJSONArray(ret, json);
				} else if (line.contains("{") && line.contains("}")) {
					line = line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1);
					JSONObject json = JSONObject.fromObject(line);
					insertMapByJSONObject(ret, json);
				} else if (line.contains("<") && line.contains(">")) {
					line = line.substring(line.indexOf("<"), line.lastIndexOf(">") + 1);
					SAXReader reader = new SAXReader();
					Document doc = reader.read(new ByteArrayInputStream(line.getBytes("UTF-8")));
					insertMapByElement(ret, doc.getRootElement());
				} else {
					String[] kvs = line.split("&");
					for (String kv : kvs) {
						String[] kva = kv.split("=");
						if (kva.length == 2) {
							ret.put(kva[0], kva[1]);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	public static Map<String, String> getMapByLine(String line) throws IOException, DocumentException {
		Map<String, String> retMap = new HashMap<String, String>();
		try {
			if (StringUtils.isNotBlank(line)) {
				String[] kvs = line.split("&");
				for (String kv : kvs) {
					String[] kva = kv.split("=");
					if (kva.length == 2) {
						retMap.put(kva[0], kva[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retMap;
	}

	public static String getReqLine(HttpServletRequest request) throws IOException {
		StringBuffer buffer = new StringBuffer();
		Enumeration<String> enumer = request.getParameterNames();
		if (enumer.hasMoreElements()) {
			while (enumer.hasMoreElements()) {
				String paramName = enumer.nextElement();
				if (StringUtils.isNotBlank(paramName)) {
					String paramValue = request.getParameter(paramName);
					buffer.append(paramName);
					buffer.append("=");
					buffer.append(paramValue);
					buffer.append("&");
				}
			}
		} else if (request.getInputStream() != null) {
			buffer.append(IOUtils.toString(request.getInputStream()));
		}
		return buffer.toString();
	}

}

