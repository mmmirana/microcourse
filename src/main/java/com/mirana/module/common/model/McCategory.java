package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;

/**
 * 课程分类Model
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月4日下午7:34:51
 */
@Notes("课程分类")
@TablePlus(name = "mc_category")
public class McCategory extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Notes("分类名称")
	@ColumnPlus
	private String name;

	@Notes("图片url")
	@ColumnPlus
	private String imgurl;

	@Notes("分类描述")
	@ColumnPlus
	private String description;

	@Notes("上级分类id")
	@ColumnPlus
	private Long parentid;

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getImgurl () {
		return imgurl;
	}

	public void setImgurl (String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public Long getParentid () {
		return parentid;
	}

	public void setParentid (Long parentid) {
		this.parentid = parentid;
	}

}
