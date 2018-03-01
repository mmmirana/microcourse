<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/base/base_meta.jsp"%>
<%@ include file="/WEB-INF/views/base/base_static.jsp"%>
<title>注册</title>
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
		<form id="registerForm" class="am-form" action="${ctx }/userhome/register" method="post">
			<fieldset class="am-form-set">
				<div class="am-form-group am-form-icon">
					<i class="am-icon-user am-text-secondary"></i><input type="text" class="am-form-field" name="username" required="required" placeholder="用户名" pattern="^[0-9a-zA-Z]{6,}$"
						data-foolish-msg="6-12位字符，只能包含字母或数字">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-mobile am-text-lg am-text-secondary"></i><input type="text" class="am-form-field" name="phone" required="required" placeholder="手机号码" pattern="^1\d{10}$"
						data-foolish-msg="手机号码格式错误">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-envelope am-text-xs  am-text-secondary"></i><input type="text" class="am-form-field" name="email" required="required" placeholder="电子邮箱" data-foolish-msg="电子邮箱格式错误"
						pattern="^\w*@\w*\.\w*">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-unlock-alt am-text-secondary" aria-hidden="true"></i><input id="password" type="password" class="am-form-field" name="password" required="required" placeholder="密码"
						pattern="^[0-9a-zA-Z]{6,}$" data-foolish-msg="6-12位字符，只能包含字母或数字">
				</div>
				<div class="am-form-group am-form-icon">
					<i class="am-icon-unlock-alt am-text-secondary" aria-hidden="true"></i><input type="password" class="am-form-field" name="repassword" required="required" placeholder="确认密码"
						data-equal-to="#password" data-foolish-msg="两次密码不一致">
				</div>
				<div class="am-form-group">
					<button id="submit" type="submit" class="am-btn am-btn-secondary am-btn-block am-btn-lg">注册</button>
				</div>
				<div class="am-form-group">
					<button type="button" class="am-btn am-btn-default am-btn-block am-btn-md" onclick="gotoLogin()">我有账户，马上登陆</button>
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
		AmForm.validate($("#registerForm"), function($form) {
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
								mcutils.toast("注册成功，" + totalTimes + "秒后去登陆");
							},
							looperEvent : function(index) {
								mcutils.toast("注册成功，" + (totalTimes - index)
										+ "后去登陆");
							},
							stopEvent : function() {
								gotoLogin();
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

	//去登陆
	function gotoLogin() {
		window.location.href = "${ctx}/userhome/toLogin";
	}
</script>
</html>
