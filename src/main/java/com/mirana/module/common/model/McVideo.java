package com.mirana.module.common.model;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.Notes;
import com.mirana.frame.db.base.anno.TablePlus;

@Notes("流媒体资源")
@TablePlus(name = "mc_video")
public class McVideo extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Notes("资源名称")
	@ColumnPlus
	private String name;

	@Notes("资源详情")
	@ColumnPlus
	private String description;

	@Notes("封面图片地址")
	@ColumnPlus
	private String posterurl;

	@Notes("视频地址")
	@ColumnPlus
	private String videourl;

	@Notes("字幕文件")
	@ColumnPlus
	private String subtitleurl;

	@Notes("是否第三方视频")
	@ColumnPlus(name = "is_third_resource")
	private boolean isThirdResource = false;

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

	public String getVideourl () {
		return videourl;
	}

	public void setVideourl (String videourl) {
		this.videourl = videourl;
	}

	public String getSubtitleurl () {
		return subtitleurl;
	}

	public void setSubtitleurl (String subtitleurl) {
		this.subtitleurl = subtitleurl;
	}

	public boolean isThirdResource () {
		return isThirdResource;
	}

	public void setThirdResource (boolean isThirdResource) {
		this.isThirdResource = isThirdResource;
	}

}
