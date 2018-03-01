package com.mirana.frame.utils;

import com.mirana.frame.exception.UtilsException;
import com.mirana.frame.utils.date.DatePattern;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * 反射工具类
 *
 * @Title
 * @Description
 * @Created Assassin
 * @DateTime 2017/06/18 15:20:08
 */
public class BeanUtils {

	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	/************************ 泛型相关 ************************/

	/**
	 * 获取当前类的第0个泛型的Class
	 *
	 * @param beanClass
	 * @return
	 */
	public static Class<?> getGenericClass (Class<?> beanClass) {
		return getGenericClass(beanClass, 0);
	}

	/**
	 * 获取当前类的第index个泛型的Class
	 *
	 * @param beanClass
	 * @param index
	 * @return
	 */
	public static Class<?> getGenericClass (Class<?> beanClass, int index) {
		log.debug("getGenericClass...");
		Type type = beanClass.getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		return (Class<?>) pt.getActualTypeArguments()[index];
	}

	/************************ 字段数组相关 ************************/

	/**
	 * 获取所有的字段数组（包括父类的字段）
	 *
	 * @param beanClass
	 * @return 字段数组
	 */
	public static Field[] getFields (Class<?> beanClass) {
		Field[] fieldArray = (Field[]) getFieldList(beanClass).toArray(new Field[]{});
		return fieldArray;
	}

	/**
	 * 获取所有的字段数组（包括父类的字段）
	 *
	 * @param beanClass        类
	 * @param excludeModifiers 要排除的修饰符数组
	 * @return 字段数组
	 */
	public static Field[] getFieldsExcludeModifier (Class<?> beanClass, int... excludeModifiers) {
		Field[] fieldArray = (Field[]) getFieldListExcludeModifier(beanClass, excludeModifiers).toArray(new Field[]{});
		return fieldArray;
	}

	/**
	 * 获取包含注解annoClass的所有字段
	 *
	 * @param beanClass 类
	 * @param annoClass 注解类
	 * @return 字段数组
	 */
	public static Field[] getFieldsWithAnno (Class<?> beanClass, Class<? extends Annotation> annoClass) {
		Field[] fieldArray = (Field[]) getFieldListWithAnno(beanClass, annoClass).toArray(new Field[]{});
		return fieldArray;
	}

	/**
	 * 获取标准实体类的字段数组（包括父类的字段）
	 *
	 * @param beanClass 类
	 * @return List数组
	 */
	public static Field[] getFieldsByEntity (Class<?> beanClass) {
		Field[] fieldArray = (Field[]) getFieldListByEntity(beanClass).toArray(new Field[]{});
		return fieldArray;
	}

	/************************ 字段集合相关 ************************/

	/**
	 * 获取所有的字段集合（包括父类的字段）
	 *
	 * @param beanClass
	 * @return List集合
	 */
	public static List<Field> getFieldList (Class<?> beanClass) {
		log.debug("getFieldList...");

		return getFieldListExcludeModifier(beanClass);
	}

	/**
	 * 获取所有的字段（包括父类的字段）
	 *
	 * @param beanClass        类
	 * @param excludeModifiers 要排除的修饰符数组
	 * @return List集合
	 */
	public static List<Field> getFieldListExcludeModifier (Class<?> beanClass, int... excludeModifiers) {
		log.debug("getFieldListExcludeModifier...");

		// 所有字段
		List<Field> fieldList = FieldUtils.getAllFieldsList(beanClass);

		// 要排除的字段
		List<Field> excludeFieldList = new ArrayList<Field>();

		for (Field field : fieldList) {
			// 字段的修饰符
			int modifiers = field.getModifiers();
			for (int excludeModifier : excludeModifiers) {
				// 如果当前字段的修饰符包含被排除的修饰符，则将当前字段放入excludeFieldList中
				if (hasModifier(modifiers, excludeModifier)) {
					excludeFieldList.add(field);
				}
			}
		}

		// 所有的字段移除被排除的字段就是最终的字段集合
		fieldList.removeAll(excludeFieldList);

		return fieldList;

	}

	/**
	 * 获取包含注解annoClass的所有字段集合
	 *
	 * @param modelClass 类
	 * @param annoClass  注解类
	 * @return 字段集合
	 */
	public static List<Field> getFieldListWithAnno (Class<?> modelClass, Class<? extends Annotation> annoClass) {

		// 所有字段
		List<Field> allFieldList = FieldUtils.getAllFieldsList(modelClass);

		// 包含注解的字段
		List<Field> annoFieldList = new ArrayList<Field>();

		for (Field field : allFieldList) {
			Annotation anno = field.getAnnotation(annoClass);
			if (anno != null) {
				annoFieldList.add(field);
			}
		}

		return annoFieldList;

	}

	/**
	 * 获取标准实体类的字段集合（包括父类的字段）
	 *
	 * @param beanClass 类
	 * @return List集合
	 */
	public static List<Field> getFieldListByEntity (Class<?> beanClass) {
		log.debug("getFieldListByEntity...");

		// 所有字段
		List<Field> allFieldList = FieldUtils.getAllFieldsList(beanClass);

		// 实体类的字段
		List<Field> entityFieldList = new ArrayList<Field>();

		for (Field field : allFieldList) {
			// 字段的修饰符
			int modifiers = field.getModifiers();
			// 如果当前字段的修饰符包含被排除的修饰符，则将当前字段放入excludeFieldList中
			if (modifiers == Modifier.PUBLIC || modifiers == Modifier.PROTECTED || modifiers == Modifier.PRIVATE) {
				entityFieldList.add(field);
			}
		}

		return entityFieldList;

	}

	/**
	 * 获取某个类的某个注解
	 *
	 * @param modelClass
	 * @param annoClass
	 * @return
	 */
	public static Annotation getEntityAnno (Class<?> modelClass, Class<? extends Annotation> annoClass) {

		return modelClass.getAnnotation(annoClass);
	}

	/**
	 * 获取bean的PropertyDescriptor数组
	 *
	 * @param beanClass
	 * @return
	 */
	public static PropertyDescriptor[] getPropDescArray (Class<?> beanClass) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			return beanInfo.getPropertyDescriptors();
		} catch (Exception e) {
			throw new UtilsException("getPropDescArray() failed", e);
		}
	}

	/**
	 * 根据bean的某一个字段获取对应的PropertyDescriptor
	 *
	 * @param beanClass
	 * @param fieldname
	 * @return
	 */
	public static PropertyDescriptor getPropDesc (Class<?> beanClass, String fieldname) {

		PropertyDescriptor[] propDescArray = getPropDescArray(beanClass);
		for (PropertyDescriptor prop : propDescArray) {
			if (prop.getName().equals(fieldname)) {
				return prop;
			}
		}
		return null;
	}

	/**
	 * 获取属性值
	 *
	 * @param fieldname
	 * @return
	 */
	public static Object getPropValue (Object obj, String fieldname) {

		PropertyDescriptor prop = getPropDesc(obj.getClass(), fieldname);
		Method readMethod = prop.getReadMethod();
		try {
			Object propValue = readMethod.invoke(obj);
			return propValue;
		} catch (Exception e) {
			throw new UtilsException("getPropValue() failed!", e);
		}
	}

	/**
	 * 获取属性值
	 *
	 * @param fieldname
	 * @return
	 */
	public static void setPropValue (Object obj, String fieldname, String fieldvalue) {
		log.debug("getPropValue... fieldname: " + fieldname + "; fieldvalue: " + fieldvalue);

		PropertyDescriptor prop = getPropDesc(obj.getClass(), fieldname);
		Method writeMethod = prop.getWriteMethod();
		try {
			writeMethod.invoke(obj, fieldvalue);
		} catch (Exception e) {
			throw new UtilsException("getPropValue() failed!", e);
		}
	}

	/**
	 * 当前class|method|field的修饰符modifiers是否包含对应的修饰符modifier
	 *
	 * @param modifiers
	 * @param modifier
	 * @return
	 */
	private static boolean hasModifier (int modifiers, int modifier) {
		return (modifiers & modifier) != 0;
	}

	/**
	 * 获取class注解
	 *
	 * @param clazz
	 * @param annoClass
	 * @return
	 */
	public static Annotation getAnnotation (Class<?> clazz, Class<? extends Annotation> annoClass) {
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i].annotationType() == annoClass) {
				return annotations[i];
			}
		}
		return null;
	}

	/**
	 * 获取字段注解
	 *
	 * @param field
	 * @return
	 */
	public static Annotation getAnnotation (Field field, Class<? extends Annotation> annoClass) {
		Annotation[] annotations = field.getDeclaredAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i].annotationType() == annoClass) {
				return annotations[i];
			}
		}
		return null;
	}

	/**
	 * 将实体类转化为Map
	 *
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> bean2map (Object bean, boolean isTransNull) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] propertys = propertyUtilsBean.getPropertyDescriptors(bean);
			for (int i = 0; i < propertys.length; i++) {
				PropertyDescriptor property = propertys[i];
				// property name
				String name = property.getName();
				if (!"class".equals(name)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);
					params.put(name, value);
					if (null == value && !isTransNull) {
						params.remove(name);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 将Map转化为Bean
	 *
	 * @param properties
	 * @param clazz
	 * @return
	 */
	public static <T extends Object> T map2bean (Map<String, String[]> properties, Class<T> clazz) {
		try {
			// 创建实例
			T t = clazz.newInstance();
			// 处理时间格式
			DateConverter dateConverter = new DateConverter();
			// 设置日期格式
			dateConverter.setPatterns(
				new String[]{DatePattern.DATETIME_DASH.getPatternStr(), DatePattern.DATE_DASH.getPatternStr()});
			// 注册格式
			ConvertUtils.register(dateConverter, Date.class);
			// 封装数据
			org.apache.commons.beanutils.BeanUtils.populate(t, properties);

			return t;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
