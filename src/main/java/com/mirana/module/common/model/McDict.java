package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;

@Notes("数据字典")
@TablePlus(name = "mc_dict")
public class McDict extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Notes("类型id")
	@ColumnPlus
	private Integer typeid;

	@Notes("类型名称")
	@ColumnPlus
	private String typename;

	@Notes("不同类型字典id")
	@ColumnPlus
	private Integer dictid;

	@Notes("不同类型字典名称")
	@ColumnPlus
	private String dictname;

	@Notes("图片url")
	@ColumnPlus
	private String imgurl;

	public Integer getTypeid () {
		return typeid;
	}

	public void setTypeid (Integer typeid) {
		this.typeid = typeid;
	}

	public String getTypename () {
		return typename;
	}

	public void setTypename (String typename) {
		this.typename = typename;
	}

	public Integer getDictid () {
		return dictid;
	}

	public void setDictid (Integer dictid) {
		this.dictid = dictid;
	}

	public String getDictname () {
		return dictname;
	}

	public void setDictname (String dictname) {
		this.dictname = dictname;
	}

	public String getImgurl () {
		return imgurl;
	}

	public void setImgurl (String imgurl) {
		this.imgurl = imgurl;
	}

	public int getSort () {
		return sort;
	}

	public void setSort (int sort) {
		this.sort = sort;
	}

}
