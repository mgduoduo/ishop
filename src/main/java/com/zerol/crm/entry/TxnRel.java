package com.zerol.crm.entry;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_TXN_REL")
public class TxnRel implements Serializable {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TXN_ID")
	private int txnID;

	@Column(name = "EXPRESS_NO")
	private String refExpressNO;

	@Column(name = "PROD_NO")
	private String refProdNO;

	@Column(name = "PURCHASE_COUNT")
	private int purchaseCount;

	@Column(name = "DEL_IND")
	private String deleteIndicator;

	@Column(name = "REMARK")
	private String remark;

	//comment
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.REFRESH}, targetEntity=Product.class)
	@JoinColumn(name="PROD_NO", referencedColumnName="PROD_NO", insertable=false, updatable=false)
	private Product product;

	public int getTxnID() {
		return txnID;
	}

	public void setTxnID(int txnID) {
		this.txnID = txnID;
	}

	public int getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public String getDeleteIndicator() {
		return deleteIndicator;
	}

	public void setDeleteIndicator(String deleteIndicator) {
		this.deleteIndicator = deleteIndicator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getRefExpressNO() {
		return refExpressNO;
	}

	public void setRefExpressNO(String refExpressNO) {
		this.refExpressNO = refExpressNO;
	}

	public String getRefProdNO() {
		return refProdNO;
	}

	public void setRefProdNO(String refProdNO) {
		this.refProdNO = refProdNO;
	}
}
