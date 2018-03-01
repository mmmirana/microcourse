<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/base/base_meta.jsp" %>
    <%@ include file="/WEB-INF/views/base/base_static.jsp" %>
    <title>mcourse.cc</title>
</head>
<body>
<%-- header --%>
<%@ include file="/WEB-INF/views/app/common/header.jsp" %>
<div class="mcbody">
    <div class="hidden">
        <input id="videoid" value="${videoid }" hidden="hidden"> <input id="courseid" value="${courseid }"
                                                                        hidden="hidden">
    </div>
    <div id="videowrapper">
        <video id="coursevideo" class="video-js" controls preload="auto">
            <source class="mp4source" type='video/mp4'>
            <!-- 加载hls视频-->
            <source src="http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8" type="application/x-mpegURL">
            <!-- 加载rtmp视频-->
            <source src="rtmp://live.hkstv.hk.lxdns.com/live/hks" type="rtmp/flv"/>
            <track kind="subtitles" label="Chinese subtitles" srclang="zh" label="Chinese">
            <p class="vjs-no-js">
                To view this video please enable JavaScript, and consider upgrading to a web browser that <a
                    href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
            </p>
        </video>
    </div>
    <%-- uhtabs --%>
    <div id="mctabs">
        <div class="uh-tabs uh-row border-top-none">
            <div class="uh-tab uh-col-4 active" data-swiper-id="tab_subtitles">字幕</div>
            <div class="uh-tab uh-col-4" data-swiper-id="tab_notes">笔记</div>
            <div class="uh-tab uh-col-4" data-swiper-id="tab_comment">评论</div>
        </div>
        <div id="mctabsswiper">
            <div class="uh-swiper">
                <div class="swiper-wrapper">
                    <div id="tab_subtitles" class="swiper-slide">
                        <div id="subtitles">
                            <div class="am-panel am-panel-default">
                                <ul id="vtt-dom" class="am-list am-list-static">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div id="tab_notes" class="swiper-slide">
                        <div id="notes">
                            <div class="am-panel am-panel-default">
                                <ul id="notes-dom" class="am-list am-list-static">
                                    <li>
                                        <div class="margin-lg text-center">
                                            登陆后查看
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div id="tab_comment" class="swiper-slide">
                        <div id="comments">
                            <div class="am-panel am-panel-default">
                                <ul id="comment-dom" class="am-list am-list-static">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<footer class="uh-footer">
    <div style="width: 80%; margin: 0 auto; padding: 10px 0px;">
        <button id="footerbtn" class="am-btn am-btn-block am-btn-success am-round" onclick="addFavourate();">收藏</button>
    </div>
</footer>
<script id="vtt-tpl" type="text/x-handlebars-template">
    {{#ex '{{length}}>0'}}
    {{#each this}}
    <li onclick="videojump({{vtt_totaltime}})">
        {{#if vtt_time}}
        <font color="#0e90d2">[{{vtt_time }}]</font>&ensp;{{vtt_text}}
        {{else}}
        {{vtt_text}}
        {{/if}}
    </li>
    {{/each}}
    {{else}}
    <div class="margin-lg text-center">
        暂无数据
    </div>
    {{/ex}}
</script>
<script id="notes-tpl" type="text/x-handlebars-template">
    {{#ex '"${activeuser.id}" == ""'}}
    <li>
        <div class="margin-lg text-center">
            登陆后查看
        </div>
    </li>
    {{else}}
    {{#ex '{{length}}>0'}}
    {{#each this}}
    <li>
        <div>
            <div class="padding-sm color-primary">{{content}}</div>
            <div class="padding-sm text-right">{{createdTime}}</div>
        </div>
    </li>
    {{/each}}
    {{else}}
    <div class="margin-lg text-center">
        暂无数据
    </div>
    {{/ex}}
    {{/ex}}
</script>
<script id="comment-tpl" type="text/x-handlebars-template">
    {{#ex '{{length}}>0'}}
    {{#each this}}
    <li>
        <div class="uh-row" style="height: 50px;padding: 5px 0px;">
            <div class="uh-col-2">
                <img class="lazyload" style="width: 20px;height: 20px;"
                     src="${ctx}/static/img/userhome/user_default.png"
                     data-original="${ctx}${activeuser.imgurl}"/>
            </div>
            <div class="uh-col-10 uh-col-left">
                <div>
                    <div>
                        <span class="color-warn">{{commentatorname}}</span> 于 {{createdTime}} 评论：
                    </div>
                    <div class="padding-sm color-primary">{{content}}</div>
                </div>
            </div>
        </div>
    </li>
    {{/each}}
    {{else}}
    <div class="margin-lg text-center">
        暂无数据
    </div>
    {{/ex}}
</script>
</body>
<script>
    var videoPlayer;
    $().ready(function () {

        videoPlayer = initVideo();

        initVideoEvent(videoPlayer);

        initMctabsHeight();
    })

    function initMctabsHeight() {
        $("#mctabsswiper").height(
            $("html").height() - $("header").height()
            - $("footer").height() - $("#mctabs .uh-tab").height()
            - $("#videowrapper").height() - 3).css("overflow-y",
            "auto");
    }

    //初始化video
    function initVideo() {
        var player;
        //url
        var getbyidUrl = "${ctx}/app/video/getbyid?videoid="
            + $("#videoid").val() + "&courseid=" + $("#courseid").val();

        //ajax
        mc.postAjax({
                url: getbyidUrl,
                async: false
            },
            function (data) {
                if (data.code && data.code != 1) {
                    mc.alert("提示", "抱歉，找不到对应的资源！", function () {
                        window.history.go(-1);
                    });
                    return;
                }
                var video = data.result.video;
                var videoprop = data.result.videoprop;
                var vttlist = data.result.vttlist;
                var noteslist = data.result.noteslist;
                var commentlist = data.result.commentlist;
                if (!video) {
                    mc.alert("提示", "抱歉，找不到对应的资源！", function () {
                        window.history.go(-1);
                    });
                } else {
                    $("#coursevideo").attr("poster",
                        "${ctx}" + video.posterurl);
                    $("#coursevideo>source").attr("src",
                        "${ctx}" + video.videourl);
                    $("#coursevideo>track").attr("src",
                        "${ctx}" + video.subtitleurl);

                    //字幕
                    if (vttlist) {
                        uhtpl.render($("#vtt-dom"), $("#vtt-tpl"),
                            vttlist);
                    }
                    //笔记
                    if (noteslist) {
                        uhtpl.render($("#notes-dom"), $("#notes-tpl"),
                            noteslist);
                    }
                    // 	评论							if (commentlist) {
                    if (commentlist) {
                        uhtpl.render($("#comment-dom"), $("#comment-tpl"),
                            commentlist);
                    }
                    var options = {
                        tabIndex: 0,//初始化的tab索引
                        activeClass: "active",
                        ontabchange: function (tabIndex) {//切换tab的事件，tabIndex要切换的tabindex的索引
                            //console.log("ontabchange: " + tabIndex);
                            if (tabIndex == 0) {
                                $("#footerbtn").text("收藏");
                                $("#footerbtn").attr("onclick",
                                    "addFavourate();");
                            } else if (tabIndex == 1) {
                                $("#footerbtn").text("添加笔记");
                                $("#footerbtn").attr("onclick",
                                    "addNotes();");
                            } else if (tabIndex == 2) {
                                $("#footerbtn").text("发表评论");
                                $("#footerbtn").attr("onclick",
                                    "addComment();");
                            }
                        },
                        onslideChange: function (activeIndex) {//切换slide的事件，activeIndex滑动到目标位置的slide的索引
                            //console.log("onslideChange: "+ activeIndex);
                        }
                    };
                    var uhtabs = uh.tabs($("#mctabs"), options);

                    if (videoprop) {
                        console.log(mc.json.stringify(videoprop));
                    }

                    //延迟加载js
                    mc.loadjs($("body"), "${ctx}/static/plugins/videojs/video.js");
                    //延迟加载js
                    mc.loadjs($("body"), "https://cdnjs.cloudflare.com/ajax/libs/videojs-contrib-hls/5.12.2/videojs-contrib-hls.js");
                    //加载完毕后找到player对象
                    player = videojs("coursevideo", {
                        "playbackRates": [0.5, 1, 1.5],
                        "techOrder": ["html5"],
                        bigPlayButton: true,
                        textTrackDisplay: true,
                        posterImage: true,
                        errorDisplay: false,
                        control: {
                            captionsButton: false,
                            chaptersButton: false,
                            subtitlesButton: false,
                            liveDisplay: false,
                            playbackRateMenuButton: false
                        },
                        volumeMenuButton: {
                            inline: false,
                            vertical: true
                        }
                    });
                    player.addClass('vjs-matrix');
                }
            });
        return player;
    }

    //初始化播放器事件
    function initVideoEvent(player) {
        // 检测播放时间
        player.on("timeupdate", function () {
            var currentTimeInt = parseInt(player.currentTime());
            if (player.currentTime() < player.duration()) {

            } else if (player.currentTime() == player.duration()) {
                ////播放结束
                console.log("播放结束");
            }
        });
    }

    function jump() {
        var current = videoPlayer.currentTime();
        videoPlayer.currentTime(current + 100);
    }
    function back() {
        var current = videoPlayer.currentTime();
        videoPlayer.currentTime(current - 100);
    }

    function dispose() {
        videoPlayer.dispose();
    }

    function play() {
        videoPlayer.play();
    }
    function pause() {
        videoPlayer.pause();
    }

    function videojump(time) {
        videoPlayer.currentTime(time);
        videoPlayer.play();
    }

    // 添加收藏
    function addFavourate() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后再收藏");
            return;
        }

        mc.postAjax({
            url: "${ctx}/app/course/addFavourate",
            data: {
                favourateuserid: "${activeuser.id}",
                courseid: "${courseid}",
            }
        }, function (data) {
            if (data && data.code == 1) {
                mc.toast("收藏成功！");
            } else {
                var msg = "抱歉，收藏失败";
                if (data && data.msg) {
                    msg = data.msg;
                }
                mc.toast(msg);
            }
        });
    }

    //添加笔记
    function addNotes() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后再添加");
            return;
        }

        mc.promptText("填写笔记", function (text, index) {
            layer.close(index);
            mc.postAjax({
                url: "${ctx}/app/course/addNotes",
                data: {
                    courseid: "${courseid}",
                    content: text
                }
            }, function (data) {
                if (data && data.code == 1) {
                    mc.toast("添加笔记成功！");
                    mc.postAjax({
                        url: "${ctx}/app/course/getNotes",
                        data: {
                            activeuserid: "${activeuser.id}",
                            courseid: "${courseid}",
                        }
                    }, function (data) {
                        mc.handleAjaxResult(data, function () {
                            uhtpl.render($("#notes-dom"), $("#notes-tpl"),
                                data.result);
                        });
                    });
                } else {
                    var msg = "抱歉，添加笔记失败";
                    if (data && data.msg) {
                        msg = data.msg;
                    }
                    mc.toast(msg);
                }
            });
        })
    }

    //添加评论
    function addComment() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后进行评论");
            return;
        }

        mc.promptText("填写评论", function (text, index) {
            layer.close(index);
            mc.postAjax({
                url: "${ctx}/app/course/addComment",
                data: {
                    courseid: "${courseid}",
                    content: text
                }
            }, function (data) {
                if (data && data.code == 1) {
                    mc.toast("评论成功！");
                    mc.postAjax({
                        url: "${ctx}/app/course/getComment",
                        data: {
                            courseid: "${courseid}",
                        }
                    }, function (data) {
                        mc.handleAjaxResult(data, function () {
                            uhtpl.render($("#comment-dom"), $("#comment-tpl"),
                                data.result);
                        });
                    });
                } else {
                    var msg = "抱歉，评论失败";
                    if (data && data.msg) {
                        msg = data.msg;
                    }
                    mc.toast(msg);
                }
            });
        })
    }
</script>
</html>
