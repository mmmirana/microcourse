package com.mirana.frame.utils.file;

import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.utils.json.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LrcUtils {

	// get lrc artist 艺术家
	public static final int ARTIST_ZONE = 0;
	// get lrc tittle 标题
	public static final int TITTLE_ZONE = 1;
	// get lrc album 专辑
	public static final int ALBUM_ZONE = 2;
	// get lrc author 作者
	public static final int AOTHOR_ZONE = 3;
	// get lrc offset
	public static final int OFFSET_ZONE = 4;
	// get lrc 歌词
	public static final int LRC_ZONE = 5;
	public static final String LRCKEY_TYPE   = "lrckey_type";
	public static final String LRCKEY_TIME   = "lrckey_time";
	public static final String LRCKEY_TIMEMS = "lrckey_timems";
	public static final String LRCKEY_TEXT   = "lrckey_text";
	/**
	 * [ar:艺人名] [ti:曲名] [al:专辑名] [by:编者（指编辑LRC歌词的人）] [offset:时间补偿值] 其单位是毫秒，正值表示整体提前，负值相反。这是用于总体调整显示快慢的。
	 */
	// parse taget artist
	private static final String TagAr = "[ar:";
	// perse taget tittle
	private static final String TagTi = "[ti:";
	// perse target album
	private static final String TagAl = "[al:";
	// perse target author of the lrc
	private static final String TagBy = "[by:";
	// perse taget offset
	private static final String TagOff = "[offset:";

	public static List<Map<String, Object>> resolve (File lrcFile) throws IOException {
		List<Map<String, Object>> lrclist = new ArrayList<Map<String, Object>>();

		List<String> contentlines = FileUtils.readLines(lrcFile, SysConstants.DEFAULT_CHARSET);

		for (String linetext : contentlines) {
			Map<String, Object> lineMap = new LinkedHashMap<String, Object>();
			if (linetext.indexOf(TagAr) != -1) {// whether artist or not
				lineMap.put(LRCKEY_TYPE, ARTIST_ZONE);
				lineMap.put(LRCKEY_TEXT, linetext.substring(linetext.indexOf(':') + 1, linetext.lastIndexOf(']')));
			} else if (linetext.indexOf(TagAl) != -1) {// whether album or not
				lineMap.put(LRCKEY_TYPE, ALBUM_ZONE);
				lineMap.put(LRCKEY_TEXT, linetext.substring(linetext.indexOf(':') + 1, linetext.lastIndexOf(']')));
			} else if (linetext.indexOf(TagTi) != -1) {// whether tittle or not
				lineMap.put(LRCKEY_TYPE, TITTLE_ZONE);
				lineMap.put(LRCKEY_TEXT, linetext.substring(linetext.indexOf(':') + 1, linetext.lastIndexOf(']')));
			} else if (linetext.indexOf(TagBy) != -1) {// whether author or not
				lineMap.put(LRCKEY_TYPE, AOTHOR_ZONE);
				lineMap.put(LRCKEY_TEXT, linetext.substring(linetext.indexOf(':') + 1, linetext.lastIndexOf(']')));
			} else if (linetext.indexOf(TagOff) != -1) {// whether offset or not
				lineMap.put(LRCKEY_TYPE, OFFSET_ZONE);
				lineMap.put(LRCKEY_TEXT, linetext.substring(linetext.indexOf(':') + 1, linetext.lastIndexOf(']')));
			} else {// lrc content
				String[] cut = linetext.split("]");
				if (cut.length >= 1) {
					String time = cut[0].substring(linetext.indexOf('[') + 1);
					long minute = Integer.parseInt(time.substring(0, time.lastIndexOf(":")));
					long second = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.lastIndexOf(".")));
					long millisecond = Integer.parseInt(time.substring(time.indexOf(".") + 1)) * 10;
					long totalms = minute * 60 * 1000 + second * 1000 + millisecond;

					lineMap.put(LRCKEY_TYPE, LRC_ZONE);
					lineMap.put(LRCKEY_TIME, time);
					lineMap.put(LRCKEY_TIMEMS, totalms);

					if (cut.length >= 2) {
						String lrctext = cut[cut.length - 1];
						lineMap.put(LRCKEY_TEXT, lrctext);
					}
				}
			}

			lrclist.add(lineMap);
		}
		return lrclist;
	}

	public static void main (String[] args) throws IOException {
		List<Map<String, Object>> lrclist = LrcUtils.resolve(new File("C:/Users/Administrator/Desktop/lrc.lrc"));
		System.out.println(JsonUtils.format(lrclist));
	}
}