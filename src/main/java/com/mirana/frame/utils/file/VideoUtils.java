package com.mirana.frame.utils.file;

import com.mirana.frame.utils.Assert;
import com.sun.xml.internal.ws.util.UtilException;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title video工具类
 * @Description
 * @CreatedBy Assassin
 * @DateTime 2017年11月10日下午8:52:27
 */
public class VideoUtils {
	/**
	 * 获取视频的基本属性
	 *
	 * @param videopath
	 * @return
	 */
	public static Map<String, Object> getVideoProp (String videopath) {
		return getVideoProp(new File(videopath));
	}

	/**
	 * 获取视频的基本属性
	 *
	 * @param videoFile
	 * @return
	 */
	public static Map<String, Object> getVideoProp (File videoFile) {
		Assert.fileExist(videoFile);
		Map<String, Object> videoInfo = new HashMap<String, Object>();
		try {
			Encoder encoder = new Encoder();
			MultimediaInfo mInfo = encoder.getInfo(videoFile);
			VideoInfo videoinfo = mInfo.getVideo();
			// 视频时长，单位毫秒
			long duration = mInfo.getDuration();
			// 比特率
			int bitrate = videoinfo.getBitRate();
			// 帧速率
			float framerate = videoinfo.getFrameRate();
			// 视频帧宽高
			int height = videoinfo.getSize().getHeight();
			int width = videoinfo.getSize().getWidth();
			videoInfo.put("duration", duration);
			videoInfo.put("durationSecond", duration / 1000);
			videoInfo.put("bitrate", bitrate);
			videoInfo.put("framerate", framerate);
			videoInfo.put("height", height);
			videoInfo.put("width", width);
		} catch (Exception e) {
			throw new UtilException("获取视频（" + videoFile.getAbsolutePath() + "）信息时发生异常", e);
		}
		return videoInfo;
	}
}
