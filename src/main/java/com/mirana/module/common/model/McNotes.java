package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;
import com.mirana.frame.db.base.extend.type.MysqlTypeEnum;

@Notes("笔记")
@TablePlus(name = "mc_notes")
public class McNotes extends BaseModel {

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

	@Notes("记录者userid")
	@ColumnPlus
	private Long noteuserid;

	@Notes("记录者手机号")
	@ColumnPlus
	private String noteuserphone;

	@Notes("笔记内容")
	@ColumnPlus(name = "content", sqlType = MysqlTypeEnum.TEXT, length = "500")
	private String content;

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

	public Long getNoteuserid () {
		return noteuserid;
	}

	public void setNoteuserid (Long noteuserid) {
		this.noteuserid = noteuserid;
	}

	public String getNoteuserphone () {
		return noteuserphone;
	}

	public void setNoteuserphone (String noteuserphone) {
		this.noteuserphone = noteuserphone;
	}

	public String getContent () {
		return content;
	}

	public void setContent (String content) {
		this.content = content;
	}

}
