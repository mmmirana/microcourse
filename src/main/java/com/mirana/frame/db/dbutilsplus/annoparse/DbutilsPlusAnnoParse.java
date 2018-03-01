package com.mirana.frame.db.dbutilsplus.annoparse;

import com.mirana.frame.base.model.BaseModel;
import com.mirana.frame.db.base.anno.ColumnPlus;
import com.mirana.frame.db.base.anno.IDPlus;
import com.mirana.frame.db.base.anno.TablePlus;
import com.mirana.frame.db.base.annoparse.BaseDBAnnoParse;
import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * dbutilsPlus的注解解析
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/24 20:35:57
 */
public class DbutilsPlusAnnoParse extends BaseDBAnnoParse {

	private static final Logger log = LoggerFactory.getLogger(DbutilsPlusAnnoParse.class);

	/**
	 * 无惨构造，默认传递的是TablePlus.class, IDPlus.class, ColumnPlus.class三个注解
	 */
	private DbutilsPlusAnnoParse () {
		super();

		this.tableAnnoClazz = TablePlus.class;
		this.pkAnnoClazz = IDPlus.class;
		this.columnAnnoClazz = ColumnPlus.class;

	}

	public static DbutilsPlusAnnoParse getInstance () {
		return DbutilsPlusAnnoParseHolder.ANNOPARSE_INSTANCE;
	}

	@Override
	public String getTablename (Class<? extends BaseModel> modelClass) {
		String tablename = modelClass.getSimpleName();

		// 获取Table注解
		Annotation anno = BeanUtils.getEntityAnno(modelClass, tableAnnoClazz);

		Assert.notNull(anno, "The model'class " + modelClass.getSimpleName() + " must has annotation["
			+ tableAnnoClazz.getSimpleName() + "]");

		// 获取tablename
		TablePlus tablePlus = (TablePlus) anno;

		if (StringUtils.isNotBlank(tablePlus.name())) {
			tablename = tablePlus.name();
		}

		log.debug("[DBAnnoParse] Model's class " + modelClass.getSimpleName() + " mapping Database's tablename is:["
			+ tablename + "]");

		return tablename;
	}

	@Override
	public String getPkColumnName (Field pkField) {
		String pkname = pkField.getName();

		Annotation anno = pkField.getAnnotation(getPkAnnoClazz());
		Assert.notNull(anno, "The Field " + pkname + " must has annotation[" + pkAnnoClazz.getSimpleName() + "]");

		IDPlus idAnno = (IDPlus) anno;
		if (!StringUtils.isBlank(idAnno.name())) {
			pkname = idAnno.name().trim();
		}
		return pkname;
	}

	@Override
	public String[] getColumnNames (Field[] columnFields) {

		List<String> columnlist = new ArrayList<String>();

		for (Field field : columnFields) {

			// 字段名称
			String fieldname = field.getName();

			// 数据库字段名称默认为实体类字段名称
			String columname = fieldname;

			// 获取字段Column注解（如果有注解，并且注解不为null，则获取注解name属性）
			ColumnPlus columnAnno = (ColumnPlus) field.getAnnotation(getColumnAnnoClazz());

			if (columnAnno == null) {
				log.debug("field[ " + fieldname + " ] doesn't have Annotation " + getColumnAnnoClazz());
			}

			if (columnAnno != null && !StringUtils.isBlank(columnAnno.name())) {
				columname = columnAnno.name();
			}

			// 向columnList中添加含有column注解的fieldname
			columnlist.add(columname);

		}

		String[] columns = columnlist.toArray(new String[]{});

		return columns;
	}

	private static final class DbutilsPlusAnnoParseHolder {
		public static final DbutilsPlusAnnoParse ANNOPARSE_INSTANCE = new DbutilsPlusAnnoParse();
	}

}
