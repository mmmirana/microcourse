package com.mirana.frame.db.base.extend.type;

public enum MysqlTypeEnum {

	/**
	 * 未知类型
	 */
	UNKENOW("", ""),

	/**
	 * tinyint
	 */
	TINYINT("TINYINT", "6"),

	/**
	 * int
	 */
	INT("INT", "11"),

	/**
	 * bigint
	 */
	BIGINT("BIGINT", "20"),

	/**
	 * float
	 */
	FLOAT("FLOAT", ""),

	/**
	 * double
	 */
	DOUBLE("DOUBLE", ""),

	/**
	 * decimal
	 */
	DECIMAL("DECIMAL", "5,2"),

	/**
	 * bit
	 */
	BIT("BIT", "1"),

	/**
	 * varchar
	 */
	VARCHAR("VARCHAR", "255"),

	/**
	 * text
	 */
	TEXT("TEXT", ""),

	/**
	 * blob
	 */
	BLOB("BLOB", ""),

	/**
	 * datetime
	 */
	DATETIME("DATETIME", "");

	private String name;

	private String length;

	private MysqlTypeEnum (String name, String length) {
		this.name = name;
		this.length = length;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getLength () {
		return length;
	}

	public void setLength (String length) {
		this.length = length;
	}

}
