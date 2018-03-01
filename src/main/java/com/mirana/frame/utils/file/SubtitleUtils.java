package com.mirana.frame.utils.file;

import com.mirana.frame.utils.Assert;
import com.mirana.frame.utils.SysPropUtils;
import com.mirana.frame.utils.log.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubtitleUtils {

	public static void srt2vtt (File srtfile, File vttfile) throws IOException {
		Assert.fileExist(srtfile);

		FileUtils.createNewFile(vttfile);

		List<String> srtlineList = org.apache.commons.io.FileUtils.readLines(srtfile, StandardCharsets.UTF_8);
		List<String> vttlineList = new ArrayList<String>();
		vttlineList.add("WEBVTT");
		// vttlineList.add("");
		for (int i = 0; i < srtlineList.size(); i++) {
			String srtline = srtlineList.get(i);
			// if (i % 4 != 0) {
			vttlineList.add(srtline.replaceAll(",", "."));
			// }
		}

		org.apache.commons.io.FileUtils.writeLines(vttfile, vttlineList);

		LogUtils.info("srt2vtt successfully! From [" + srtfile.getAbsolutePath() + "] to [" + vttfile.getAbsolutePath() + "]");
	}

	/**
	 * 解析字幕
	 *
	 * @param vttfile
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> resolveVtt (File vttfile) throws IOException {
		List<String> vttlineList = org.apache.commons.io.FileUtils.readLines(vttfile, StandardCharsets.UTF_8);
		vttlineList.set(0, "");
		int step = 3;
		for (int i = 1; i < vttlineList.size(); i++) {
			if (StringUtils.isBlank(vttlineList.get(i))) {
				step = i;
				break;
			}
		}

		List<Map<String, Object>> vttmaplist = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < vttlineList.size(); i += step) {
			if ((i + 3 + 1) <= vttlineList.size()) {
				Map<String, Object> vttmap = new HashMap<String, Object>();

				String index = vttlineList.get(i + 1);
				String timeline = vttlineList.get(i + 2);
				String time = timeline.replaceAll("\\..*-->.*", "").replaceAll("\\s", "");

				String[] timestr = time.split("[:\\.]");
				int hours = Integer.valueOf(timestr[0].replaceAll("^0?", ""));
				int minutes = Integer.valueOf(timestr[1].replaceAll("^0?", ""));
				int seconds = Integer.valueOf(timestr[2].replaceAll("^0?", ""));
				int totaltime = hours * 60 * 60 + minutes * 60 + seconds;

				String text = vttlineList.get(i + 3);
				vttmap.put("vtt_index", index);
				vttmap.put("vtt_text", text);
				vttmap.put("vtt_time", time);
				vttmap.put("vtt_totaltime", totaltime);

				vttmaplist.add(vttmap);
			}
		}

		return vttmaplist;
	}

	public static void main (String[] args) throws IOException {
		// //srt-->vtt
		File srcfile = new File("E:/workspace/MarsProjects/microcourse/src/main/webapp/static/video/xdjyjs_1.3.srt");
		String vttfilename = FileUtils.replaceSuffix(srcfile.getName(), ".vtt");
		File vttfile = new File(srcfile.getParent() + SysPropUtils.FILE_SEPARATOR + vttfilename);
		srt2vtt(srcfile, vttfile);
		// List<Map<String, Object>> vttmaplist = resolveVtt(
		// new File("E:/workspace/MarsProjects/microcourse/src/main/webapp/static/video/xdjyjs_20171215.vtt"));
		// System.out.println(JsonUtils.stringify(vttmaplist));
	}
}
