package com.mirana.frame.utils.dbutils;

import com.mirana.frame.base.dao.IBaseDao;
import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.generator.DBHelper;
import com.mirana.frame.utils.SysPropUtils;
import com.mirana.frame.utils.log.LogUtils;
import com.mirana.module.common.model.McTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;

import java.beans.PropertyVetoException;
import java.io.IOException;

/**
 * 目前只支持mysql
 *
 * @Title
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年10月8日下午7:47:05
 */
public class DBPlusGenerator {

	// log4j
	public static final String LOG4J_PATH = DBPlusGenerator.class.getClassLoader().getResource("config/log/log4j.properties").getPath();
	// freemarker tpl
	public static final String FMTPL_PATH = DBPlusGenerator.class.getClassLoader().getResource("fmtpl").getPath();

	// 生成Java文件的项目根路径
	public static final String BASEPATH = "E:\\workspace\\IdeaProjects\\Mirana\\webapp\\";

	/**
	 * 加载Log4j配置文件，filepath为空，传递默认的log4j配置
	 *
	 * @param filepath
	 */
	public static void loadLog4j (String filepath) {
		// 默认路径
		if (StringUtils.isBlank(filepath)) {
			filepath = LOG4J_PATH;
		}
		PropertyConfigurator.configure(filepath);
	}

	/**
	 * 配置基本参数
	 *
	 * @return
	 */
	public static DBHelper configureDBHelper () {

		try {
			// 指定freemarker的模板目录
			String fmTemplateDirpath = FMTPL_PATH;

			// 加载Log4j配置文件
			loadLog4j(LOG4J_PATH);

			// 获取DBHelper实例
			DBHelper dbHelper = DBHelper.getInstance();
			dbHelper.loadJDBC();
			dbHelper.setFmTemplateDir(fmTemplateDirpath);
			return dbHelper;
		} catch (PropertyVetoException e) {
			LogUtils.error("配置DBHelper时发生异常：" + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成代码
	 *
	 * @param modelClass           实体class
	 * @param modelDirPath         模块所在的目录
	 * @param dropTableIfExist     如果存在表，是否删除重新生成
	 * @param deleteDaoIfExist     如果存在Dao，是否删除重新生成
	 * @param deleteServiceIfExist 如果存在Service，是否删除重新生成
	 * @throws IOException
	 */
	public static void generate (Class<? extends BaseModel> modelClass, String modelDirPath, boolean dropTableIfExist, boolean deleteDaoIfExist,
								 boolean deleteServiceIfExist) throws IOException {

		DBHelper dbHelper = configureDBHelper();

		dbHelper.generateTable(modelClass, dropTableIfExist);
		dbHelper.generateDao(modelClass, modelDirPath + SysPropUtils.FILE_SEPARATOR + "dao", deleteDaoIfExist);
		dbHelper.generateService(modelClass, modelDirPath + SysPropUtils.FILE_SEPARATOR + "service", deleteServiceIfExist);

	}

	/**
	 * 根据实体类的注解生成Table，生成Dao、DaoImpl，生成Service、ServiceImpl
	 *
	 * @throws IOException
	 */
	public static void main (String[] args) throws IOException {

		System.out.println(IBaseDao.class.getPackage().getName());

		// 测试
		generate(McTest.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, true, true);
		// // 用户表
		// generate(McUser.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 分类表
		// generate(McCategory.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 课程表
		// generate(McCourse.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 图片表
		// generate(McPicGallery.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 视频表
		// generate(McVideo.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 字典表
		// generate(McDict.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, false, false);
		// // 用户评论表
		// generate(McUsercomment.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, true, true);
		// // 轮播图
		// generate(McPicGallery.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, true, true);
		// // 笔记
		// generate(McNotes.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, true, true);
		// 笔记
		// generate(McFavourate.class, BASEPATH + "src\\main\\java\\com\\mirana\\module\\common", true, true, true);

	}
}
