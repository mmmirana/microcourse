package com.mirana.module.app.controller;

import com.mirana.frame.base.controller.BaseController;
import com.mirana.frame.constants.SysConstants;
import com.mirana.frame.db.base.query.OrderBy;
import com.mirana.frame.db.base.query.Where;
import com.mirana.frame.utils.RequestUtils;
import com.mirana.frame.utils.file.FileUtils;
import com.mirana.frame.utils.file.SubtitleUtils;
import com.mirana.frame.utils.result.Result;
import com.mirana.frame.utils.result.ResultUtils;
import com.mirana.module.common.model.McNotes;
import com.mirana.module.common.model.McUser;
import com.mirana.module.common.model.McUsercomment;
import com.mirana.module.common.model.McVideo;
import com.mirana.module.common.service.INotesService;
import com.mirana.module.common.service.IUsercommentService;
import com.mirana.module.common.service.IVideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/video")
public class VideoController extends BaseController {

	@Resource
	private IVideoService       videoService;
	@Resource
	private IUsercommentService usercommentService;
	@Resource
	private INotesService       notesService;

	@RequestMapping("/showvideo")
	public String toShowVideo (HttpServletRequest request, Model model) {
		String courseid = request.getParameter("courseid");
		String videoid = request.getParameter("videoid");
		model.addAttribute("courseid", courseid);
		model.addAttribute("videoid", videoid);
		return "/app/video";
	}

	@RequestMapping("/getbyid")
	@ResponseBody
	public Result getVideoById (HttpServletRequest request) {

		try {

			McUser activeuser = RequestUtils.getSessionAttr(request, SysConstants.SESSION_KEY_ACTIVEUSER);
			String ctxpath = RequestUtils.getRequestCtxRealPath(request);

			String courseid = request.getParameter("courseid");
			String videoid = request.getParameter("videoid");

			McVideo video = videoService.findByPk(Long.valueOf(videoid));

			// 获取视频基本信息
			File videoFile = new File(ctxpath + video.getVideourl());
			// Map<String, Object> videoprop = VideoUtils.getVideoProp(videoFile);

			// 放入返回值
			if (FileUtils.isExist(videoFile)) {

				Map<String, Object> resultmap = new HashMap<String, Object>();

				// 字幕
				File videoSubtitlesFile = new File(ctxpath + video.getSubtitleurl());
				if (FileUtils.isFile(videoSubtitlesFile)) {
					List<Map<String, Object>> vttlist = SubtitleUtils.resolveVtt(videoSubtitlesFile);
					resultmap.put("vttlist", vttlist);
				}

				// 用户评论
				Where commentwhere = new Where.Builder().EQ("courseid", Long.valueOf(courseid)).OrderBy("createdTime", OrderBy.DESC).build();
				List<McUsercomment> commentlist = usercommentService.findByWhere(commentwhere);

				if (activeuser != null) {
					// 笔记
					Where notesWhere = new Where.Builder().EQ("courseid", Long.valueOf(courseid)).EQ("noteuserid", activeuser.getId())
						.OrderBy("createdTime", OrderBy.DESC).build();
					List<McNotes> noteslist = notesService.findByWhere(notesWhere);
					// 笔记
					resultmap.put("noteslist", noteslist);

				}

				// 视频pojo
				resultmap.put("video", video);
				// // 视频属性
				// resultmap.put("videoprop", videoprop);

				// 评论列表
				resultmap.put("commentlist", commentlist);

				return ResultUtils.buildSuccess(resultmap);
			} else {
				return ResultUtils.buildFailed("抱歉，找不到对应的视频资源！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.buildError("获取视频数据发生异常！");
		}
	}

}
