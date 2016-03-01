package com.zerol.crm.entry;

import com.zerol.crm.common.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "TB_TRADE_ORDER")
public class OrderInfo implements Serializable {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRADE_ID")
	private long tradeID;

	@Column(name = "EXPRESS_NO")
	private String expressNO;

	@Column(name = "TOTAL_PRICE")
	private Integer totalPrice;

	@Column(name = "PAYMENT_TYPE")
	private String payType;

	@Column(name = "EXPRESS_CHARGE")
	private Integer expressCharge;

	@Column(name = "EXPRESS_COMPANY")
	private String expressCompany;

	@Column(name = "RECEIVING_ADDRESS")
	private String receiveAdd;//收货人地址
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;//收货人姓名
	
	@Column(name = "CUSTOMER_CONTACT_NO")
	private String customerPhoneNO;//收货人联系电话

	@Column(name = "CUSTOMER_QQ")
	private String customerQQ;//买家QQ

	@Column(name = "CUSTOMER_WW")
	private String customerWW;//买家旺旺
	
	@Column(name = "PURCHASE_DATE")
	private java.sql.Date shopingDate;

	@Column(name = "EXPRESS_STATUS")
	private String expressStatus;
	
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "LS_UPD_TS")
	private java.sql.Date lst_update_dt;

	@Column(name = "REFUND_IND")
	private String refundIndicator;
	
	@Column(name = "REFUND_EXPRESS_NO")
	private String refundExpressNO;
	
	@Column(name = "REFUND_REASON")
	private String refundReason;

	@Column(name = "REFUND_DATE")
	private java.sql.Date refundDt;

	@Column(name = "REFUND_DETAIL")
	private String refundDetail;
	
	@Column(name = "REFUND_STATUS")
	private String refundStatus;

	//comment
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.MERGE},fetch=FetchType.EAGER)
	@JoinColumn(name="EXPRESS_NO", referencedColumnName="EXPRESS_NO")
	private List<TxnRel> txnList = new ArrayList<TxnRel>();

	public long getTradeID() {
		return tradeID;
	}

	public void setTradeID(long tradeID) {
		this.tradeID = tradeID;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getReceiveAdd() {
		return receiveAdd;
	}

	public void setReceiveAdd(String receiveAdd) {
		this.receiveAdd = receiveAdd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNO() {
		return customerPhoneNO;
	}

	public void setCustomerPhoneNO(String customerPhoneNO) {
		this.customerPhoneNO = customerPhoneNO;
	}

	public String getCustomerQQ() {
		return customerQQ;
	}

	public void setCustomerQQ(String customerQQ) {
		this.customerQQ = customerQQ;
	}

	public String getCustomerWW() {
		return customerWW;
	}

	public void setCustomerWW(String customerWW) {
		this.customerWW = customerWW;
	}

	public String getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRefundIndicator() {
		return refundIndicator;
	}

	public void setRefundIndicator(String refundIndicator) {
		this.refundIndicator = refundIndicator;
	}

	public String getRefundExpressNO() {
		return refundExpressNO;
	}

	public void setRefundExpressNO(String refundExpressNO) {
		this.refundExpressNO = refundExpressNO;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundDetail() {
		return refundDetail;
	}

	public void setRefundDetail(String refundDetail) {
		this.refundDetail = refundDetail;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public List<TxnRel> getTxnList() {
		return txnList;
	}

	public void setTxnList(List<TxnRel> txnList) {
		this.txnList = txnList;
	}

	//	private String shopingDateStr;
	public String getShopingDateStr() {
		String shopingDateStr = "";
		if(this.shopingDate != null){
			shopingDateStr = DateUtil.formatDate(this.shopingDate, DateUtil.DEFAULT_DATE_FORMAT);
		}
		return shopingDateStr;
	}

	//	private String lastUpdateTimeStr;
	public String getLastUpdateTimeStr() {
		String lastUpdateTimeStr = "";
		if(this.lst_update_dt != null){
			lastUpdateTimeStr = DateUtil.formatDate(this.lst_update_dt, DateUtil.FORMAT_DATE_TIMESTAMP);
		}
		return lastUpdateTimeStr;
	}

	public Date getShopingDate() {
		return shopingDate;
	}

	public void setShopingDate(Date shopingDate) {
		this.shopingDate = shopingDate;
	}

	public Date getLst_update_dt() {
		return lst_update_dt;
	}

	public void setLst_update_dt(Date lst_update_dt) {
		this.lst_update_dt = lst_update_dt;
	}

	public Date getRefundDt() {
		return refundDt;
	}

	public void setRefundDt(Date refundDt) {
		this.refundDt = refundDt;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getExpressCharge() {
		return expressCharge;
	}

	public void setExpressCharge(Integer expressCharge) {
		this.expressCharge = expressCharge;
	}
}
