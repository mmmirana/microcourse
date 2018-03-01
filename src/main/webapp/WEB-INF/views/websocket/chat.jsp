<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/base/base_taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/base/base_meta.jsp"%>
<%@ include file="/WEB-INF/views/base/base_static.jsp"%>
<title>mcourse.cc</title>
</head>
<body>
	<div>
		<input type="button" id="btnConn" value="连接" />
		<hr>
		<input type="button" id="btnClose" value="关闭" />
		<hr>
		<input type="button" id="btnSend" value="发送" />
	</div>
</body>
<script>
	$().ready(function() {
		initEvent();
	})

	function initEvent() {
		//			0 CONNECTING 连接尚未建立
		//			1 OPEN WebSocket的链接已经建立
		//			2 CLOSING 连接正在关闭
		//			3 CLOSED 连接已经关闭或不可用

		var websocket;
		$("#btnConn").click(function() {
			websocket = websocketConn();
		});

		$("#btnSend").click(
				function() {
					if (websocket) {
						if (websocket.readyState == 0) {
							console.log("0 CONNECTING 连接尚未建立");
						} else if (websocket.readyState == 1) {
							console.log("1 OPEN WebSocket的链接已经建立");
							websocket.send("From client，datetime: "
									+ dateutils.format(new Date()));
						} else if (websocket.readyState == 2) {
							console.log("2 CLOSING 连接正在关闭");
						} else if (websocket.readyState == 3) {
							console.log("3 连接已经关闭或不可用");
						}
					} else {
						console.warn("请先连接socket再发送消息");
					}
				});

		$("#btnClose").click(function() {
			if (websocket && websocket.readyState == 1) {
				websocket.close();
			} else {
				console.warn("尚未建立socket连接，无须关闭当前websocket");
			}
		});

		window.onunload = function() {
			$("#btnClose").click();
		}
	}

	function websocketConn() {
		//var host = 'ws://192.168.1.211:9999/';
		var host = 'ws://192.168.0.107:9999/';
		var websocket;
		if ('WebSocket' in window) {
			websocket = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket(host);
		} else {
			alert('提示', '当前浏览器不支持websocket，请更换浏览器');
		}

		websocket.onopen = function(event) {
			console.log('open');
		};

		websocket.onmessage = function(event) {
			var data = event.data;
			console.log(JSON.stringify(data));
		};

		websocket.onclose = function(event) {
			console.log('close');
		}
		websocket.onerror = function(event) {
			console.log('error');
		};

		//sendToAll();

		return websocket;
	}

	function sendToAll() {
		$.ajax({
			url : "http://192.168.0.107:9101/microcourse/websocket/sendToAll",
			type : "post",
			dataType : "json",
			success : function(data) {
				console.log(JSON.stringify(data));
			},
			error : function() {
				alert("error");
			}
		})
	}
</script>
</html>
