/**
 * 封装layer-mobile
 * 
 * 现在支持的弹窗有 toast msg alert confirm loading
 * 
 * 关闭弹窗 close closeAll
 * 
 * @param $
 *            jquery
 * @param layer
 *            layer
 */
!function($, layer) {
	"use strict";

	var uhlayer = {};

	/**
	 * toast窗口
	 * 
	 * @param message
	 *            提示的信息
	 * @param time
	 *            时长，单位秒
	 */
	function toast(message, time) {
		if (!message) {
			message = "";
		}
		if (!time) {
			// 2秒后自动关闭
			time = 2;
		}
		// 提示
		return layer.open({
			content : message,
			skin : 'msg',
			anim : false,
			time : time,
		});
	}

	/**
	 * alert框（上面消息内容，下面一个确认按钮）
	 * 
	 * @param message
	 *            提示的信息
	 * @param btnOk
	 *            确认按钮文本
	 * @param btnOkEvent
	 *            确认按钮文本
	 */
	function alert(message, btnOk, btnOkEvent) {

		// 默认消息为"";
		if (!message) {
			message = "";
		}
		// 默认按钮文本为"确认"
		if (!btnOk) {
			btnOk = "确认";
		}

		// 信息框
		return layer.open({
			content : message,
			anim : false,
			btn : btnOk,
			yes : btnOkEvent
		});
	}

	/**
	 * msg框（上面标题，下面内容）
	 * 
	 * @param title
	 *            标题文本
	 * @param content
	 *            提示信息文本
	 */
	function msg(title, content) {
		if (!title) {
			title = "提示";
		}
		if (!content) {
			content = "";
		}
		// 自定义标题风格
		return layer.open({
			title : [ title, 'background-color: #4dafe0; color:#fff;' ],
			content : content
		});
	}

	/**
	 * confirm框
	 * 
	 * @param message
	 *            提示的信息
	 * @param btnOk
	 *            确认按钮文本
	 * @param btnOkEvent
	 *            确认按钮的事件，可以传递一个index的参数
	 * @param btnCancel
	 *            取消按钮文本
	 * @param btnCancelEvent
	 *            取消按钮的事件，没有参数（默认关闭当前弹窗）
	 */
	function confirm(message, btnOk, btnOkEvent, btnCancel, btnCancelEvent) {
		if (!btnOk) {
			btnOk = "确定";
		}
		if (!btnCancel) {
			btnCancel = "取消";
		}
		// 询问框
		return layer.open({
			content : message,
			btn : [ btnOk, btnCancel ],
			yes : btnOkEvent,
			no : btnCancelEvent
		});
	}

	/**
	 * 遮罩层
	 * 
	 * @param $shadeDom
	 *            遮罩层的dom元素
	 * @param opacity
	 *            透明度0-1
	 * @param remove
	 *            弹出遮罩层后是否移除遮罩层Dom
	 * 
	 */
	function shade($shadeDom, opacity, remove) {

		// 将shadeDom强制转化为Jquery元素
		if (!$shadeDom instanceof jQuery) {
			$shadeDom = $($shadeDom);
		}

		// 校验透明度
		if (!opacity) {
			opacity = 0.5;
		}

		if (opacity < 0) {
			opacity = 0;
		} else if (opacity > 1) {
			opacity = 1;
		}

		var content = $shadeDom.html() || "";

		// 页面层
		var shadeIndex = layer.open({
			type : 1,
			content : content,
			anim : 'up',
			style : 'background: rgba(0, 0, 0, 0); text-align: center;'
		});

		var $layershade = $("#layui-m-layer" + shadeIndex
				+ ">.layui-m-layershade");
		$layershade.css("background-color", "rgba(0, 0, 0, " + opacity + ")");

		if (remove === true) {
			$shadeDom.remove();// 不论原先的状态如何，这里将原先的要弹出的遮罩div元素移除掉
		}
		return shadeIndex;
	}

	/**
	 * 从页面底部弹出出一个新的页面
	 * 
	 * @param $poppageEleDom
	 *            弹出页面的dom元素
	 * @param remove
	 *            弹出后是否移除当前Dom元素
	 */
	function popdom($poppageEleDom, remove) {
		// 将$poppageEleDom强制转化为Jquery元素
		if (!$poppageEleDom instanceof jQuery) {
			$poppageEleDom = $($poppageEleDom);
		}

		var content = $poppageEleDom.html() || "";

		var popIndex = pophtml(content);

		// 是否当前Dom元素
		if (remove === true) {
			$poppageEleDom.remove();// 不论原先的状态如何，这里将原先的要弹出的遮罩div元素移除掉
		}

		return popIndex;
	}

	/**
	 * 从页面底部弹出出一个新的页面
	 * 
	 * @param url
	 *            要请求的url
	 * @param data
	 *            请求参数
	 * @param closeDomID
	 *            在新页面中关闭当前遮罩层的Dom元素id
	 */
	function poppage(url, data, closeDomID) {
		var index;
		$.ajax({
			url : url,
			data : data,
			async : false,
			success : function(htmlcontent) {
				// 弹出遮罩层
				var $wrapper = $("<div></div>");
				$wrapper.append(htmlcontent)
				index = pophtml($wrapper.html());

				// 禁止下层滚动
				$('body').bind("touchmove", function(e) {
					e.preventDefault();
				});

				// 去掉页面遮罩层的滚动条
				$(".layui-m-layerchild").css("overflow", "auto");

				// 关闭遮罩层的点击事件
				$(closeDomID).click(function() {
					uhlayer.close(index);
					// 禁止下层滚动
					$('body').bind("touchmove", function(e) {
						e.stopPropagation();
					});
				});
			},
		});
		return index;
	}
	/**
	 * 从页面底部弹出出一个新的页面
	 * 
	 * @param content
	 *            弹出页面的html文本
	 * @param full
	 *            是否全屏显示
	 */
	function pophtml(content) {
		// var style = "position:fixed; left:0; top:0; width:100%;
		// height:100%;border: none; -webkit-animation-duration: .5s;
		// animation-duration: .5s;";
		var style = "position:initial; left:0; top:0; width:100%; height:100%; overflow: scroll;border: none; -webkit-animation-duration: .5s; animation-duration: .5s;";

		// 页面层
		var pophtmlIndex = layer.open({
			type : 1,
			content : content,
			anim : 'up',
			style : style
		});

		return pophtmlIndex;
	}

	/**
	 * 加载框
	 */
	function loading(tipsTest) {
		var loadingOptions = {
			type : 2
		};
		if (tipsTest) {
			loadingOptions.content = tipsTest;
		}
		return layer.open(loadingOptions);
	}

	/**
	 * cube加载中
	 */
	function cubeLoading(time) {
		var content = "<div class='sk-rotating-plane'></div>";
		var index = layer.open({
			shadeClose : false,
			content : content,
			time : time,
		});

		var $layer = $(".layui-m-layerchild.layui-m-anim-scale");
		$layer.css("background", "transparent").css("box-shadow", "none");

		return index;
	}

	/**
	 * wave加载中(推荐)
	 */
	function waveLoading(time) {
		var content = '<div class="sk-wave">'
				+ '<div class="sk-rect sk-rect1"></div>'
				+ '<div class="sk-rect sk-rect2"></div>'
				+ '<div class="sk-rect sk-rect3"></div>'
				+ '<div class="sk-rect sk-rect4"></div>'
				+ '<div class="sk-rect sk-rect5"></div>' + '</div>';
		var index = layer.open({
			shadeClose : false,
			content : content,
			time : time,
		});

		var $layer = $(".layui-m-layerchild.layui-m-anim-scale");
		$layer.css("background", "transparent").css("box-shadow", "none");

		return index;
	}

	/**
	 * 关闭某个索引对应的窗口
	 */
	function close(index) {
		layer.close(index)
	}

	/**
	 * 关闭所有的弹出层
	 */
	function closeAll() {
		layer.closeAll()
	}

	// toast窗口
	uhlayer.toast = toast;
	// 没有按钮的消息窗口（标题、消息体）
	uhlayer.msg = msg;
	// 一个按钮的确认窗口
	uhlayer.alert = alert;
	// 两个按钮的确认窗口
	uhlayer.confirm = confirm;
	// 遮罩层
	uhlayer.shade = shade;
	// 从底部弹出一个新的页面
	uhlayer.poppage = poppage;
	// 正在加载中窗口
	uhlayer.loading = loading;
	// cube加载中
	uhlayer.cubeLoading = cubeLoading;
	// wave加载中
	uhlayer.waveLoading = waveLoading;
	// 关闭某个弹出层
	uhlayer.close = close;
	// 关闭所有弹出层
	uhlayer.closeAll = closeAll;

	window.uhlayer = uhlayer;
}($, layer);