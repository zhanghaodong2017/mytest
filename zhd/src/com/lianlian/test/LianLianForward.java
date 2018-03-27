package com.lianlian.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONObject;

@WebServlet("/LianLianForward")
public class LianLianForward extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String lianlian_oid_partner = "201802050000756011";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter printWriter = response.getWriter();
			String urls = request.getHeader("urls");
			if(urls == null || "".equals(urls)){
				printWriter.append("urls is null");
			}else{
				String reqJson = IOUtils.toString(request.getInputStream());
				String rspJson = HttpUtils.doPost(urls, reqJson, HttpUtils.JSON);
				printWriter.append(rspJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		String reqJson = "{\"bank_code\":\"01050000\",\"trans_code\":\"1001\",\"oid_partner\":\"201803210001656397\",\"time_stamp\":\"20180321164338\",\"risk_item\":\"{\"user_info_full_name\":\"张浩东\",\"user_info_id_no\":\"320382199108110719\",\"frms_client_chnl\":\"APP\",\"user_info_identify_type\":\"1\",\"user_info_bind_phone\":\"18862482655\",\"user_info_identify_state\":\"1\",\"user_info_mercht_userno\":\"dfecc5e00fe848d2a29cbe85c928db03\",\"frms_ip_addr\":\"116.226.184.189\",\"frms_ware_category\":\"2018\",\"user_info_dt_register\":\"20180321164338\"}\",\"sign\":\"KaELYfPc2yQIG03HESxWY/KG1ldddYwV72AQ8z7C3jy9tGDiGg8kuwgb01Ogfr4gdlF6TOwnNeKvo1V7BoRp7ex96kpN5+L9zWb1IUIJlTICNBONkn0kWdHOqByPG17H1yND+5k1NYbh8yOOYGTe0eAiwHmp8KPe7QfvfBEgoCQ=\",\"dt_order\":\"20180321164338\",\"api_version\":\"V1.0\",\"no_order\":\"366kktkkqn\",\"id_no\":\"320382199108110719\",\"card_no\":\"6217002000036389833\",\"bind_mob\":\"18862482655\",\"id_type\":\"0\",\"acct_name\":\"张浩东\",\"sign_type\":\"RSA\"}";
		String urls ="http://test.yintong.com.cn/fundapi/signapply";
		String rspJson = HttpUtils.doPost(urls , reqJson, HttpUtils.JSON);
		System.out.println(rspJson);
	}
	


}
