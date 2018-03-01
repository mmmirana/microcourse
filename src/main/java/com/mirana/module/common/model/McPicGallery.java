package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;

@Notes("WebApp前台图片画廊，图片轮播")
@TablePlus(name = "mc_picgallery")
public class McPicGallery extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Notes("图片显示的标题")
	@ColumnPlus
	private String title;

	@Notes("图片显示的详细信息")
	@ColumnPlus
	private String description;

	@Notes("图片所属组别id")
	@ColumnPlus(name = "group_id")
	private Integer groupId;

	@Notes("图片所属组名称")
	@ColumnPlus(name = "group_name")
	private String groupName;

	@Notes("图片路径，相对web项目根路径")
	@ColumnPlus
	private String picurl;

	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public Integer getGroupId () {
		return groupId;
	}

	public void setGroupId (Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName () {
		return groupName;
	}

	public void setGroupName (String groupName) {
		this.groupName = groupName;
	}

	public String getPicurl () {
		return picurl;
	}

	public void setPicurl (String picurl) {
		this.picurl = picurl;
	}

}
