package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;

/**
 * 课程Model
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月4日下午7:36:28
 */
@Notes("课程")
@TablePlus(name = "mc_course")
public class McCourse extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Notes("所属分类")
	@ColumnPlus(name = "categoryid")
	private Long categoryId;

	@Notes("所属分类名称")
	@ColumnPlus(name = "categoryname")
	private String categoryName;

	@Notes("课程名称")
	@ColumnPlus(nullable = false)
	private String name;

	@Notes("详情")
	@ColumnPlus
	private String description;

	@Notes("封面图片url")
	@ColumnPlus
	private String posterurl;

	@Notes("视频id")
	@ColumnPlus
	private Long videoid;

	@Notes("标签ids，json数组存储")
	@ColumnPlus(name = "tagids")
	private String tagIds;

	public Long getCategoryId () {
		return categoryId;
	}

	public void setCategoryId (Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName () {
		return categoryName;
	}

	public void setCategoryName (String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public String getPosterurl () {
		return posterurl;
	}

	public void setPosterurl (String posterurl) {
		this.posterurl = posterurl;
	}

	public Long getVideoid () {
		return videoid;
	}

	public void setVideoid (Long videoid) {
		this.videoid = videoid;
	}

	public String getTagIds () {
		return tagIds;
	}

	public void setTagIds (String tagIds) {
		this.tagIds = tagIds;
	}

}
