<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/base/base_meta.jsp" %>
    <%@ include file="/WEB-INF/views/base/base_static.jsp" %>
    <title>个人中心</title>
</head>
<body>
<div class="uh-header">
    <div class="uh-nav">
        <div class="uh-nav-content">
            <div class="uh-nav-arrow" onclick="javascript:history.go(-1)">
                <i class="fa fa-angle-left"></i>
            </div>
            <div class="uh-nav-title">我的收藏</div>
            <div class="uh-nav-operate">
            </div>
        </div>
    </div>
</div>
<div class="uh-body">
    <div class="am-panel am-panel-primary">
        <div id="favourate-dom" class="am-g am-cf">
            <script type="text/x-handlebars-template" id="favourate-tpl">
                {{#ex '{{length}}>0'}}
                {{#each this}}
                <div class="am-u-sm-6" onclick="showvideo('{{this.id}}','{{this.videoid}}');">
                    <div class="am-thumbnail">
                        <img class="lazyload" src="${ctx}/static/img/icon/icon_mcourse_76x76.png"
                             data-original="${ctx }{{this.posterurl}}"/>
                        <h3 class="am-thumbnail-caption">{{this.name}}{{this.age}}</h3>
                    </div>
                </div>
                {{/each}}
                {{else}}
                <div class="margin-lg text-center">
                    暂无数据
                </div>
                {{/ex}}
            </script>
        </div>
    </div>
</div>
</body>
<script>
    $().ready(function () {
        // 填充分页数据
        mc.postAjax({
                url: "${ctx}/userhome/getFavourate",
                data: {
                    pageno: 1,
                    pagesize: 100
                }
            },
            function (resultmap) {
                mc.handleAjaxResult(resultmap, function () {
                    if (resultmap.result.pageresult.length >= 0) {
                        uhtpl.render($("#favourate-dom"), $("#favourate-tpl"), resultmap.result.pageresult, true);
                        $("img.lazyload").lazyload();
                    }
                });
            }
        );
    });

    function showvideo(courseid, videoid) {
        window.location.href = "${ctx}/app/video/showvideo?courseid="
            + courseid + "&videoid=" + videoid;
    }
</script>
</html>
