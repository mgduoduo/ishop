package com.zerol.crm.entry;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_PRODUCT")
public class Product implements Serializable {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROD_ID")
	private long prodID;

	@Column(name = "PROD_NO")
	private String prodNo;

	@Column(name = "PROD_NAME")
	private String prodName;

	@Column(name = "BACKUP_COUNT")
	private Integer backupCount;

	@Column(name = "PROD_DESC")
	private String prodDesc;

	@Column(name = "PROD_PRICE")
	private Integer prodPrice;

	@Column(name = "SUPPLIER")
	private String supplier;

	@Column(name = "REMARK")
	private String remark;

//	@OneToMany(cascade={CascadeType.MERGE, CascadeType.REFRESH},fetch=FetchType.LAZY)
////	@JoinTable(name="TB_TXN_REL", joinColumns = {@JoinColumn(name="EXPRESS_NO", referencedColumnName = "EXPRESS_NO")})
//	@JoinColumn(name="PROD_NO", referencedColumnName="PROD_NO")
//	private List<TxnRel> txnRel = new ArrayList<TxnRel>();

	public Product() {
	}

	public Product(String prodNo, String prodName) {
		this.prodNo = prodNo;
		this.prodName = prodName;
	}

	public Product(String prodNo, String prodName, int backupCount, String prodDesc, int prodPrice, String supplier, String remark) {
		this.prodNo = prodNo;
		this.prodName = prodName;
		this.backupCount = backupCount;
		this.prodDesc = prodDesc;
		this.prodPrice = prodPrice;
		this.supplier = supplier;
		this.remark = remark;
	}

	public long getProdID() {
		return prodID;
	}

	public void setProdID(long prodID) {
		this.prodID = prodID;
	}

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getBackupCount() {
		return backupCount;
	}

	public void setBackupCount(Integer backupCount) {
		this.backupCount = backupCount;
	}

	public Integer getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Integer prodPrice) {
		this.prodPrice = prodPrice;
	}
}
