package com.mirana.frame.db.base.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月3日上午10:38:15
 */
public class Page<T> {

	public static final int DEFAULT_PAGE_NO    = 1;
	public static final int DEFAULT_PAGE_SIZE  = 15;
	public static final int DEFAULT_TOTAL_PAGE = 1;
	public static final int DEFAULT_TOTAL_ROWS = 0;

	private int     pageno     = DEFAULT_PAGE_NO;// 当前页码
	private int     pagesize   = DEFAULT_PAGE_SIZE;// 每页显示记录数
	private int     totalpage  = DEFAULT_TOTAL_PAGE;// 总页数
	private int     totalrows  = DEFAULT_TOTAL_ROWS;// 总记录数
	private List<T> pageresult = new ArrayList<T>();// 结果集

	public int getPageno () {
		return pageno;
	}

	public void setPageno (int pageno) {
		this.pageno = pageno;
	}

	public int getPagesize () {
		return pagesize;
	}

	public void setPagesize (int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage () {
		return totalpage;
	}

	public void setTotalpage (int totalpage) {
		this.totalpage = totalpage;
	}

	public int getTotalrows () {
		return totalrows;
	}

	public void setTotalrows (int totalrows) {
		this.totalrows = totalrows;
	}

	public List<T> getPageresult () {
		return pageresult;
	}

	public void setPageresult (List<T> pageresult) {
		this.pageresult = pageresult;
	}
}
