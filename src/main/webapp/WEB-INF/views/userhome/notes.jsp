<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/base/base_meta.jsp" %>
    <%@ include file="/WEB-INF/views/base/base_static.jsp" %>
    <title>个人中心</title>
</head>
<body>

<div class="uh-header uh-header-fixed">
    <div class="uh-nav">
        <div class="uh-nav-content">
            <div class="uh-nav-arrow" onclick="javascript:history.go(-1)">
                <i class="fa fa-angle-left"></i>
            </div>
            <div class="uh-nav-title">我的笔记</div>
            <div class="uh-nav-operate">
            </div>
        </div>
    </div>
</div>
<div class="uh-body uh-header-fixed">

    <div id="notes">
        <div class="am-panel am-panel-default">
            <ul id="notes-dom" class="am-list am-list-static">
                <script id="notes-tpl" type="text/x-handlebars-template">
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
                </script>
            </ul>
        </div>
    </div>
</div>

</div>
</body>
<script>
    $().ready(function () {
        // 填充分页数据
        mc.postAjax({
                url: "${ctx}/userhome/getNotes",
                data: {
                    pageno: 1,
                    pagesize: 100
                }
            },
            function (resultmap) {
                mc.handleAjaxResult(resultmap, function () {
                    if (resultmap.result.pageresult.length >= 0) {
                        uhtpl.render($("#notes-dom"), $("#notes-tpl"), resultmap.result.pageresult, true);
                    }
                });
            }
        );
    });
</script>
</html>
