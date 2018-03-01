package com.mirana.frame.base.model;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.IDPlus;
import com.mirana.frame.db.base.anno.Notes;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title 实体基类
 * @Description
 * @Created Assassin
 * @DateTime 2017年9月11日上午9:50:57
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Notes("FromBaseModel: 自增主键ID，这里命名为" + SysConstants.DB_PRIMARY_KEY)
	@IDPlus(name = SysConstants.DB_PRIMARY_KEY)
	@ColumnPlus
	protected Long id;

	@Notes("FromBaseModel: 排序字段")
	@ColumnPlus(name = "sort")
	protected int sort;

	@Notes("FromBaseModel: 是否启用flag【0:未启用，1:已启用】")
	@ColumnPlus(name = "isenable")
	protected long isEnable = 1;

	@Notes("FromBaseModel: 是否删除flag【0:未删除，1:已删除】")
	@ColumnPlus(name = "isdelete")
	protected long isDelete = 0;

	@Notes("FromBaseModel: 创建者")
	@ColumnPlus(name = "created_by")
	protected String createdBy;

	@Notes("FromBaseModel: 创建时间")
	@ColumnPlus(name = "created_time")
	protected Date createdTime;

	@Notes("FromBaseModel: 更新者 ")
	@ColumnPlus(name = "updated_by")
	protected String updatedBy;

	@Notes("FromBaseModel: 更新时间")
	@ColumnPlus(name = "updated_time")
	protected Date updatedTime;

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public int getSort () {
		return sort;
	}

	public void setSort (int sort) {
		this.sort = sort;
	}

	public long getIsEnable () {
		return isEnable;
	}

	public void setIsEnable (long isEnable) {
		this.isEnable = isEnable;
	}

	public long getIsDelete () {
		return isDelete;
	}

	public void setIsDelete (long isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatedBy () {
		return createdBy;
	}

	public void setCreatedBy (String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime () {
		return createdTime;
	}

	public void setCreatedTime (Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy () {
		return updatedBy;
	}

	public void setUpdatedBy (String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime () {
		return updatedTime;
	}

	public void setUpdatedTime (Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
