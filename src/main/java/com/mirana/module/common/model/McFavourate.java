package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;
import com.mirana.frame.db.base.extend.type.MysqlTypeEnum;

@Notes("用户收藏")
@TablePlus(name = "mc_favourate")
public class McFavourate extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Notes("课程id")
	@ColumnPlus
	private Long courseid;

	@Notes("课程名称")
	@ColumnPlus
	private String coursename;

	@Notes("课程所属分类id")
	@ColumnPlus
	private Long categoryid;

	@Notes("课程所属分类名称")
	@ColumnPlus
	private String categoryname;

	@Notes("课程所属用户id")
	@ColumnPlus
	private Long userid;

	@Notes("收藏者userid")
	@ColumnPlus
	private Long favourateuserid;

	@Notes("收藏者手机号")
	@ColumnPlus
	private String favourateuserphone;

	@Notes("收藏备注")
	@ColumnPlus(name = "remarks", sqlType = MysqlTypeEnum.TEXT, length = "500")
	private String remarks;

	public Long getCourseid () {
		return courseid;
	}

	public void setCourseid (Long courseid) {
		this.courseid = courseid;
	}

	public String getCoursename () {
		return coursename;
	}

	public void setCoursename (String coursename) {
		this.coursename = coursename;
	}

	public Long getCategoryid () {
		return categoryid;
	}

	public void setCategoryid (Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname () {
		return categoryname;
	}

	public void setCategoryname (String categoryname) {
		this.categoryname = categoryname;
	}

	public Long getUserid () {
		return userid;
	}

	public void setUserid (Long userid) {
		this.userid = userid;
	}

	public Long getFavourateuserid () {
		return favourateuserid;
	}

	public void setFavourateuserid (Long favourateuserid) {
		this.favourateuserid = favourateuserid;
	}

	public String getFavourateuserphone () {
		return favourateuserphone;
	}

	public void setFavourateuserphone (String favourateuserphone) {
		this.favourateuserphone = favourateuserphone;
	}

	public String getRemarks () {
		return remarks;
	}

	public void setRemarks (String remarks) {
		this.remarks = remarks;
	}

}
