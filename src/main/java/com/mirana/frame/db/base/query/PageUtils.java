package com.mirana.frame.db.base.query;

import com.mirana.frame.base.model.BaseModel;

import java.util.List;

/**
 * Page工具类
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月1日下午3:55:27
 */
public class PageUtils {

	/***
	 * 构建一个Page
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param totalRows
	 * @param result
	 * @return
	 */
	public static <T extends BaseModel> Page<T> buildPage (int pageNo, int pageSize, int totalRows, List<T> result) {
		Page<T> page = new Page<T>();

		page.setPageno(pageNo);// 当前页数
		page.setPagesize(pageSize);// 每页记录数
		page.setPageresult(result);// 当前页结果集

		page.setTotalrows(totalRows);// 总计数个数
		// 如果总记录数为0，则默认为1页，否则根据总记录数和每页显示数计算
		int totalPage = totalRows == 0 ? 1 : (totalRows % pageSize == 0 ? totalRows / pageSize : totalRows / pageSize + 1);
		page.setTotalpage(totalPage);// 总页数
		return page;
	}

}
