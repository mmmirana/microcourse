package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;
import com.mirana.frame.db.base.extend.type.MysqlTypeEnum;

/**
 * Created by Administrator on 2017/12/31.
 */
@Notes("测试dbgenerator")
@TablePlus(name = "mc_test")
public class McTest extends BaseModel {

	@Notes("测试String")
	@ColumnPlus
	private String aa;

	@Notes("测试Text")
	@ColumnPlus(sqlType = MysqlTypeEnum.TEXT)
	private String bb;

	@Notes("测试Blob")
	@ColumnPlus(sqlType = MysqlTypeEnum.BLOB)
	private String cc;

	@Notes("测试int")
	@ColumnPlus
	private int dd;

	@Notes("测试Integer")
	@ColumnPlus
	private Integer ee;

	@Notes("测试long")
	@ColumnPlus
	private long ff;

	@Notes("测试Long")
	@ColumnPlus
	private Long gg;


	public String getAa () {
		return aa;
	}

	public void setAa (String aa) {
		this.aa = aa;
	}

	public String getBb () {
		return bb;
	}

	public void setBb (String bb) {
		this.bb = bb;
	}

	public String getCc () {
		return cc;
	}

	public void setCc (String cc) {
		this.cc = cc;
	}

	public int getDd () {
		return dd;
	}

	public void setDd (int dd) {
		this.dd = dd;
	}

	public Integer getEe () {
		return ee;
	}

	public void setEe (Integer ee) {
		this.ee = ee;
	}

	public long getFf () {
		return ff;
	}

	public void setFf (long ff) {
		this.ff = ff;
	}

	public Long getGg () {
		return gg;
	}

	public void setGg (Long gg) {
		this.gg = gg;
	}
}
