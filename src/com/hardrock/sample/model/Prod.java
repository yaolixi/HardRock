package com.hardrock.sample.model;

import com.hardrock.mongo.annotation.ForeignKey;
import com.hardrock.mongo.annotation.PrimaryKey;
import com.hardrock.mongo.annotation.RequiredField;

@PrimaryKey (PK = {"id"})
public class Prod extends SampleMongoObject{
	@RequiredField
	private String name;
	@RequiredField
	private String code;
	@RequiredField
	private int id;
	@RequiredField
	@ForeignKey (foreignClass = Mkt.class, foreignKey = "id")
	private int mktId;
	@RequiredField
	private String prodType;
	@RequiredField
	private boolean isAtomic;
	@RequiredField
	@ForeignKey (foreignClass = Acct.class, foreignKey = "id")
	private int acctId;
	
	public static final String PROD_TYPE_STOCK_A = "S";
	public static final String PROD_TYPE_STOCK_B = "B";
	public static final String PROD_TYPE_FOUNDATION = "J";
	public static final String PROD_TYPE_BOND = "G";
	public static final String PROD_TYPE_ETF = "E";
	public static final String PROD_TYPE_ETF_OPTION = "P";
	public static final String PROD_TYPE_WARRANT = "Q";
	public static final String PROD_TYPE_STOCK_INDEX = "Z";
	public static final String PROD_TYPE_REVERSE_REPO = "N";
	public static final String PROD_TYPE_FUTURE = "F";
	public static final String PROD_TYPE_OTHER = "O";
	
	public static final String[] PROD_TYPE_TEXTS = new String[] { "个股(A股)", "基金", "ETF", "股指", "权证", "债券", "个股(B股)", "逆回购", "期货", "其他" };
    public static final String[] PROD_TYPE_VALUES = new String[] { PROD_TYPE_STOCK_A, PROD_TYPE_FOUNDATION, PROD_TYPE_ETF, PROD_TYPE_STOCK_INDEX, PROD_TYPE_WARRANT, PROD_TYPE_BOND, PROD_TYPE_STOCK_B, PROD_TYPE_REVERSE_REPO, PROD_TYPE_FUTURE, PROD_TYPE_OTHER };
	
    public Prod(){
	}
    
	public Prod(String code, String name){
		this.code = code;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMktId() {
		return mktId;
	}
	public void setMktId(int mktId) {
		this.mktId = mktId;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public boolean getIsAtomic() {
		return isAtomic;
	}
	public void setIsAtomic(boolean isAtomic) {
		this.isAtomic = isAtomic;
	}
	public int getAcctId() {
		return acctId;
	}

	public void setAcctId(int acctId) {
		this.acctId = acctId;
	}
}
