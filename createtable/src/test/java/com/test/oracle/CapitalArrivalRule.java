package com.test.oracle;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.Gson;

public class CapitalArrivalRule {

	/**
	 * ta编号
	 */
	protected String taNo;
	/**
	 * 产品(基金)代码
	 */
	protected String prodCode;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 更新时间
	 */
	protected Date updateTime;
	/**
	 * 是否有效(1:有效;0:无效)
	 */
	protected String isEnable;
	/**
	 * 备注
	 */

	/**
	 * 业务代码
	 */
	private String businCode;
	/**
	 * 交收日N值
	 */
	private BigDecimal dateN;
	/**
	 * 最晚交收时间点
	 */
	private BigDecimal iTime;
	/**
	 * 处理日期类型
	 */
	private String businDateType;

	public CapitalArrivalRule() {
	}

	public String getBusinCode() {
		return businCode;
	}

	public void setBusinCode(String businCode) {
		this.businCode = businCode;
	}

	public BigDecimal getDateN() {
		return dateN;
	}

	public void setDateN(BigDecimal dateN) {
		this.dateN = dateN;
	}

	public BigDecimal getiTime() {
		return iTime;
	}

	public void setiTime(BigDecimal iTime) {
		this.iTime = iTime;
	}

	public String getBusinDateType() {
		return businDateType;
	}

	public void setBusinDateType(String businDateType) {
		this.businDateType = businDateType;
	}

	protected String remark;

	public String getTaNo() {
		return taNo;
	}

	public void setTaNo(String taNo) {
		this.taNo = taNo;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toGson() {
		return new Gson().toJson(this);
	}

}