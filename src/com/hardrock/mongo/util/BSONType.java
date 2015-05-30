package com.hardrock.mongo.util;

public enum BSONType {
	
	BSON_DOUBLE((byte)1),
	BSON_STRING((byte)2),
	BSON_OBJECT((byte)3),
	BSON_ARRAY((byte)4),
	BSON_BINARY_DATA((byte)5),
	BSON_UNDEFINED((byte)6),
	BSON_OBJECT_ID((byte)7),
	BSON_BOOLEAN((byte)8),
	BSON_DATE((byte)9),
	BSON_NULL((byte)10),
	BSON_REGULAR_EXPRESSION((byte)11),
	BSON_JAVASCRIPT((byte)13),
	BSON_SYMBOL((byte)14),
	BSON_JAVASCRIPT_WITH_SCOPE((byte)15),
	BSON_32_BIT_INTEGER((byte)16),
	BSON_TIMESTAMP((byte)17),
	BSON_64_BIT_INTEGER((byte)18),
	BSON_MIN_KEY((byte)-1),
	BSON_MAX_KEY((byte)127);
	
	private byte type;
	
	private BSONType(byte type){
		this.type = type;
	}
	
	public byte getNumber(){
		return type;
	}
}
