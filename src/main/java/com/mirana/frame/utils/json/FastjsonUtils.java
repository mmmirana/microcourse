package com.mirana.frame.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.google.gson.JsonSyntaxException;
import com.mirana.frame.utils.date.DatePattern;

import java.lang.reflect.Type;
import java.util.Date;

public class FastjsonUtils {

	private static final SerializerFeature[] features = {
		// 打开循环引用检测，JSONField(serialize=false)不循环
		SerializerFeature.DisableCircularReferenceDetect,
		// 格式日期格式化
		SerializerFeature.WriteDateUseDateFormat,
		// 输出空值字段
		SerializerFeature.WriteMapNullValue,
		// list字段如果为null，输出为[]，而不是null
		// SerializerFeature.WriteNullListAsEmpty,
		// 数值字段如果为null，输出为0，而不是null
		// SerializerFeature.WriteNullNumberAsZero,
		// Boolean字段如果为null，输出为false，而不是null
		// SerializerFeature.WriteNullBooleanAsFalse,
		// 字符类型字段如果为null，输出为""，而不是null
		// SerializerFeature.WriteNullStringAsEmpty
	};
	private static SerializeConfig serializeConfig = new SerializeConfig();

	static {
		ObjectSerializer sdfSerializer = new SimpleDateFormatSerializer(DatePattern.DATETIME_DASH.getPatternStr());
		serializeConfig.put(Date.class, sdfSerializer);
	}

	/**
	 * 私有无参构造
	 */
	private FastjsonUtils () {
		super();
	}

	public static String stringify (Object object) {
		return JSON.toJSONString(object, serializeConfig, features);
	}

	public static <T> T parse (String text, Class<T> clazz) throws JsonSyntaxException {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * 将json字符串转化为对象
	 * <p>
	 * <pre>
	 * <code>
	 * 	McUser user = FastjsonUtils.parse(userJson, new TypeReference<McUser>(){}.getType());
	 * 	Map<String, Object> map = FastjsonUtils.parse(mapJson, new TypeReference<Map<String, Object>>(){}.getType());
	 * </code>
	 * </pre>
	 *
	 * @param text json字符串
	 * @param type 要转换的类型
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static <T> T parse (String text, Type type) throws JsonSyntaxException {
		return JSON.parseObject(text, type);
	}
}
