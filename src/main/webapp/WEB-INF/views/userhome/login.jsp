<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/base/base_meta.jsp"%>
<%@ include file="/WEB-INF/views/base/base_static.jsp"%>
<title>登陆</title>
</head>
<body>
	<%-- header --%>
	<%@ include file="/WEB-INF/views/app/common/header.jsp"%>
	<div>
		<div class="header am-padding-lg mc-portrait-bg">
			<div class="am-g am-text-center">
				<img class="am-padding-xs radius-50 mc-protrait-img" src="${ctx }/static/img/icon/icon_mcourse_120x120.png">
			</div>
		</div>
		<form id="loginForm" class="am-form" action="${ctx }/userhome/login" method="post">
			<fieldset class="am-form-set">
				<div class="am-form-group am-form-icon">
					<i class="am-icon-user am-text-secondary"></i><input type="text" class="am-form-field" name="username" required="required" placeholder="用户名/手机号码/Email"
						pattern="(^[a-zA-Z]\w{5,19}$)|(^1\d{10}$)|(^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$)" data-foolish-msg="请填写正确的账户">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-unlock-alt am-text-secondary" aria-hidden="true"></i><input type="password" class="am-form-field" name="password" required="required" placeholder="密码">
				</div>
				<div class="am-form-group am-text-right font-sm" style="padding: 0px 5px;">
					<%--<a href="javascript:void(0);" onclick="forgetPwd()">忘记密码？</a>--%>
				</div>
				<div class="am-form-group">
					<button id="submit" type="submit" class="am-btn am-btn-secondary am-btn-block am-btn-lg">登陆</button>
				</div>
				<div class="am-form-group">
					<button type="button" class="am-btn am-btn-default am-btn-block am-btn-md" onclick="gotoRegister()">没有账户？现在注册</button>
				</div>
			</fieldset>
		</form>
	</div>
</body>
<script>
	$().ready(function() {
		//初始化表单验证
		initFormValidate();
	});

	function initFormValidate() {
		//表单校验
		AmForm.validate($("#loginForm"), function($form) {
			//提交表单  
			$form.ajaxSubmit({
				type : "post",
				dataType : "json",
				success : function(result) {
					mcutils.handleAjaxResult(result, function() {
						var totalTimes = 3;
						timelooper.loop({
							totalTimes : totalTimes,
							interval : 1000,
							startEvent : function() {
								mcutils.toast("登陆成功，" + totalTimes + "秒后跳转页面");
							},
							looperEvent : function(index) {
								mcutils.toast("登陆成功，" + (totalTimes - index)
										+ "秒后跳转页面");
							},
							stopEvent : function() {
								toUsehome();
							}
						});
					});
				},
				error : function() {
					mcutils.toast("抱歉，通讯异常！");
				}
			});
			return false;
		});
	}

	// 到达个人中心
	function toUsehome() {
		window.location.href = "${ctx}/userhome/index";
	}

	//忘记密码
	function forgetPwd() {
		window.location.href = "${ctx}/forgetPwd";
	}

	//去注册
	function gotoRegister() {
		window.location.href = "${ctx}/userhome/toRegister";
	}
</script>
</html>
