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

<div>
    <!-- 轮播 -->
    <div data-am-widget="slider" class="am-slider am-slider-c2" data-am-slider='{&quot;directionNav&quot;:false}'>
        <ul class="am-slides">
            <c:forEach items="${picList }" var="pic">
                <li><img class="lazy" style="width: 100%; height: 150px;" src="${ctx }${pic.picurl}"
                         alt="${pic.description }">
                    <div class="am-slider-desc">${pic.title }</div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <!-- 热门课程 -->
    <div>
        <div class="am-panel am-panel-primary">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">热门</h3>
            </div>
            <div id="hotcourse" class="am-g am-cf">
                <!-- template #hotcourse-tpl -->

            </div>
        </div>
    </div>
    <!-- 热门课程 -->
    <div>
        <div class="am-panel am-panel-primary">
            <div class="am-panel-hd">
                <h3 class="am-panel-title">教育技术专题</h3>
            </div>
            <div id="course-jyjs" class="am-g am-cf">
                <!-- template #hotcourse-tpl -->

            </div>
        </div>
    </div>
</div>
<script type="text/x-handlebars-template" id="hotcourse-tpl">
    {{#each this}}
    <div class="am-u-sm-6" onclick="showvideo('{{this.id}}','{{this.videoid}}');">
        <div class="am-thumbnail">
            <img class="lazyload" src="${ctx}/static/img/icon/icon_mcourse_76x76.png"
                 data-original="${ctx }{{this.posterurl}}"/>
            <h3 class="am-thumbnail-caption">{{this.name}}</h3>
        </div>
    </div>
    {{/each}}
</script>
<script type="text/x-handlebars-template" id="course-jyjs-tpl">
    {{#each this}}
    <div class="am-u-sm-6" onclick="showvideo('{{this.id}}','{{this.videoid}}');">
        <div class="am-thumbnail">
            <img class="lazyload" src="${ctx}/static/img/icon/icon_mcourse_76x76.png"
                 data-original="${ctx }{{this.posterurl}}"/>
            <h3 class="am-thumbnail-caption">{{this.name}}</h3>
        </div>
    </div>
    {{/each}}
</script>
</body>
<script>
    $().ready(function () {
        //添加主页到主屏幕
        //addToHomescreen();

        //初始化热门课程
        initHotCourse();

        //教育技术专题
        initJyjsCourse();
    })

    function initHotCourse() {

        mc.postAjax({
            url: "${ctx}/app/course/getHotCourse"
        }, function (data) {
            var courselist = data.result;
            //渲染模板
            mc.render($("#hotcourse"), $("#hotcourse-tpl"), courselist);
            $("img.lazyload").lazyload();
        });
    }

    //初始化教育技术课程
    function initJyjsCourse() {

        mc.postAjax({
            url: "${ctx}/app/course/getJyjsCourse",
        }, function (data) {
            var courselist = data.result;
            //渲染模板
            mc.render($("#course-jyjs"), $("#course-jyjs-tpl"), courselist);
            $("img.lazyload").lazyload();
        });
    }

    function showvideo(courseid, videoid) {
        window.location.href = "${ctx}/app/video/showvideo?courseid="
            + courseid + "&videoid=" + videoid;
    }
</script>
</html>
