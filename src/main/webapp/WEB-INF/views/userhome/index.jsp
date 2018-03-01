<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/base/base_meta.jsp" %>
    <%@ include file="/WEB-INF/views/base/base_static.jsp" %>
    <title>个人中心</title>
</head>
<body class="homepage">
<div class="uh-header">
    <div class="uh-nav">
        <div class="uh-nav-content">
            <div class="uh-nav-arrow" onclick="javascript:history.go(-1)">
                <i class="fa fa-angle-left"></i>
            </div>
            <div class="uh-nav-title">个人中心</div>
            <div class="uh-nav-operate">
            </div>
        </div>
    </div>
</div>
<div class="mc-divider-md"></div>
<div class="uh-body">

    <!-- 头像 -->
    <div class="avatar-wrapper uh-center-v"
         style="background: url(${ctx}/static/img/userhome/user_avator_bg.png); background-size: cover;"
    ">
    <div class="avatar-content uh-center-vh-row">
        <!-- 头像区域 -->
        <div class="margin-sm">
            <img class="avatar" src="${ctx}/static/img/userhome/user_default.png">
        </div>
        <!-- 个人信息|登陆注册 -->
        <div>
            <div class="fontsize-lg margin-lg">
                <c:if test="${activeuser==null}">
                    <a href="javascript:;" onclick="toLogin();">登陆</a> | <a href="javascript:;"
                                                                            onclick="toRegister();">注册</a>
                </c:if>
                <c:if test="${activeuser!=null}">
                    <c:choose>
                        <c:when test="${activeuser.username!=null||activeuser.username!=''}">
                            ${activeuser.username}
                        </c:when>
                        <c:when test="${activeuser.phone!=null||activeuser.phone!=''}">
                            ${activeuser.phone}
                        </c:when>
                        <c:when test="${activeuser.email==null||activeuser.email==''}">
                            ${activeuser.email}
                        </c:when>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </div>
    <c:if test="${activeuser!=null}">
        <label class="operate" onclick="logout()">注销></label>
    </c:if>
</div>
<div class="mc-divider-md"></div>
<div class="uh-list uh-bordered uh-effect mc-module">
    <div class="uh-list-item" style="color: #ff764b;" onclick="toFavourate();">
        <div class="uh-icon-left" style="color: red;">
            <i class="fa fa-heart" aria-hidden="true"></i>
        </div>
        <div class="margin-left-lg">
            我的收藏
        </div>
        <div class="uh-icon-right">
            <i class="fa fa-angle-right fontsize-xl"></i>
        </div>
    </div>
    <div class="uh-list-item" style="color: #546E7A;" onclick="toNotes();">
        <div class="uh-icon-left">
            <i class="fa fa-pencil-square" aria-hidden="true"></i>
        </div>
        <div class="margin-left-lg">
            我的笔记
        </div>
        <div class="uh-icon-right">
            <i class="fa fa-angle-right fontsize-xl"></i>
        </div>
    </div>
    <div class="uh-list-item" style="color: #9575CD;" onclick="toComments();">
        <div class="uh-icon-left">
            <i class="fa fa-commenting" aria-hidden="true"></i>
        </div>
        <div class="margin-left-lg">
            我的评价
        </div>
        <div class="uh-icon-right">
            <i class="fa fa-angle-right fontsize-xl"></i>
        </div>
    </div>
</div>

</div>
</body>
<script>
    $().ready(function () {

    });
    //到达登陆页面
    function toLogin() {
        window.location.href = "${ctx}/userhome/toLogin";
    }
    //到达注册页面
    function toRegister() {
        window.location.href = "${ctx}/userhome/toRegister";
    }
    //注销
    function logout() {
        mc.postAjax({
            url: "${ctx}/userhome/logout"
        }, function (resultmap) {
            mcutils.handleAjaxResult(resultmap, function () {
                var totalTimes = 1;
                timelooper.loop({
                    totalTimes: totalTimes,
                    interval: 1000,
                    startEvent: function () {
                        mcutils.toast("注销成功");
                    },
                    looperEvent: function (index) {
                    },
                    stopEvent: function () {
                        window.location.reload();
                    }
                });
            });
        })
    }

    // 我的收藏
    function toFavourate() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后查看");
            return;
        }
        window.location.href = "${ctx}/userhome/toFavourate";
    }

    // 我的笔记
    function toNotes() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后查看");
            return;
        }
        window.location.href = "${ctx}/userhome/toNotes";
    }

    // 到达评论
    function toComments() {
        if ("${activeuser.id}" == "") {
            mc.toast("请登录后查看");
            return;
        }
        window.location.href = "${ctx}/userhome/toComments";
    }
</script>
</html>
