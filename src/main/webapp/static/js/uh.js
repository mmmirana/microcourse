/**
 * 微课前端组件封装
 * 
 * @Author Assassin
 */
!function($, uhlayer) {
	var uh = {};

	/**
	 * 微课ajax get请求 API:
	 * 
	 * 1、uh.get(url,successCallback);
	 * 
	 * 2、uh.get(url,data,successCallback);
	 * 
	 * @param url
	 *            要请求的url
	 * @param data
	 *            要请求的data
	 * @param successCallback
	 *            请求成功的回调函数
	 */
	uh.get = function(url, data, successCallback) {

		if ($.isFunction(data)) {
			successCallback = data;
			data = undefined;
		}

		$.get(url, data, function(resultmap) {
			if (successCallback && $.isFunction(successCallback)) {
				successCallback(resultmap);
			}
		});
	}

	/**
	 * 微课异步ajax post(async) API:
	 * 
	 * uh.post(url,data,successCallback,errorCallback,isShowLoading);
	 * 
	 * @param url
	 *            要请求的url
	 * @param data
	 *            要请求的data
	 * @param successCallback
	 *            ajax请求成功的回调函数
	 * @param errorCallback
	 *            ajax请求发生异常的回调函数
	 * @param isShowLoading
	 *            是否显示loading框
	 */
	uh.post = function(url, data, successCallback, errorCallback, isShowLoading) {
		var ajaxOptions = {
			url : url,
			data : data,
			async : true,
			successCallback : successCallback,
			errorCallback : errorCallback,
			showLoading : isShowLoading
		};
		uh.ajax(ajaxOptions);
	};

	/**
	 * 微课同步ajax post(sync) API:
	 * 
	 * uh.syncPost(url,data,successCallback,errorCallback,isShowLoading);
	 * 
	 * @param url
	 *            要请求的url
	 * @param data
	 *            要请求的data
	 * @param successCallback
	 *            ajax请求成功的回调函数
	 * @param errorCallback
	 *            ajax请求发生异常的回调函数
	 * @param isShowLoading
	 *            是否显示loading框
	 */
	uh.syncPost = function(url, data, successCallback, errorCallback,
			isShowLoading) {
		var ajaxOptions = {
			url : url,
			data : data,
			async : false,
			successCallback : successCallback,
			errorCallback : errorCallback,
			showLoading : isShowLoading
		};
		uh.ajax(ajaxOptions);
	}

	/**
	 * 微课异步ajax post(async) API:
	 * 
	 * uh.ajax(options);
	 * 
	 * @param url
	 *            要请求的url
	 * @param data
	 *            要请求的data
	 * @param successCallback
	 *            ajax请求成功的回调函数，如果未定义，则默认在控制台打印json数据
	 * @param errorCallback
	 *            ajax请求发生异常的回调函数，如果为定义，将使用默认方法处理
	 */
	uh.ajax = function(ajaxOptions) {
		var successCallback = ajaxOptions.successCallback;
		var errorCallback = ajaxOptions.errorCallback;
		var showLoading = true;
		if (ajaxOptions.showLoading != undefined
				&& ajaxOptions.showLoading === false) {
			showLoading = false;
		}

		// 如果显示loading，则ajax请求之前弹出loading框
		var loadingIndex;
		if (showLoading === true) {
			loadingIndex = uhlayer.waveLoading();
		}

		var options = {
			url : ajaxOptions.url,
			type : ajaxOptions.type || "post",
			data : ajaxOptions.data,
			async : ajaxOptions.async,
			dataType : ajaxOptions.dataType || "json",
			success : function(resultmap) {
				// 如果显示loading，则ajax请求成功后关闭loading框
				if (showLoading === true) {
					uhlayer.close(loadingIndex);
				}
				if (successCallback && $.isFunction(successCallback)) {
					successCallback(resultmap);
				} else {
					// 默认不处理成功的ajax
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 如果显示loading，则ajax请求成功后关闭loading框
				if (showLoading === true) {
					uhlayer.close(loadingIndex);
				}
				if (errorCallback && $.isFunction(errorCallback)) {
					errorCallback(XMLHttpRequest, textStatus, errorThrown);
				} else {
					uhlayer.toast("抱歉，请求失败！");
				}
			}
		};

		$.ajax(options);
	};

	/**
	 * 处理ajax结果
	 * 
	 * @param resultmap
	 *            ajax 返回的json对象
	 * @param successCallback
	 *            resultmap.code==1的回调函数
	 * @param failedCallback
	 *            resultmap.code!=1的回调函数
	 */
	uh.handleAjaxresult = function(resultmap, successCallback, failedCallback) {
		if (resultmap && resultmap.code == 1) {
			successCallback(resultmap);
		} else {
			if (failedCallback && $.isFunction(failedCallback)) {
				failedCallback(resultmap);
			} else {
				var errormsg = (resultmap ? resultmap.msg : undefined) || "";
				uhlayer.toast(errormsg);
			}
		}
	}

	/** ************************* handlebar template相关 ************************* */

	/**
	 * handlebar渲染模板
	 * 
	 * @param $wrapper
	 *            渲染完毕后要放入对应Dom元素对应的jquery对象
	 * @param $tpl
	 *            要渲染的模板
	 * @param data
	 *            数据源
	 * @param isAppend
	 *            是否追加数据，默认false
	 * 
	 */
	uh.render = function($wrapper, $tpl, data, isAppend) {
		var source = $tpl.text();
		var template = Handlebars.compile(source);

//		内建工具
//
//		转义字符串
//
//		Handlebars.Utils.escapeExpression(string)  
//		判断空值
//
//		Handlebars.Utils.isEmpty(value)  
//		扩展对象
//
//		Handlebars.Utils.extend(foo, {bar: true})  
//		转字符串
//
//		Handlebars.Utils.toString(obj)  
//		判断数组
//
//		Handlebars.Utils.isArray(obj)  
//		判断函数
//
//		Handlebars.Utils.isFunction(obj)  

		// debug
		Handlebars.registerHelper("debug", function(optionalValue) {
			console.log("======== Current Context========");
			console.log(this);
			if (optionalValue) {
				console.log("======== Value ========");
				console.log(optionalValue);
			}
		});

		// notempty
		Handlebars.registerHelper('notempty', function(array, options) {
			if (array.length > 0) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// greater than
		Handlebars.registerHelper('gt', function(param1, param2, options) {
			if (param1 > param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// greater equal
		Handlebars.registerHelper('ge', function(param1, param2, options) {
			if (param1 >= param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// less than
		Handlebars.registerHelper('lt', function(param1, param2, options) {
			if (param1 < param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// less equal
		Handlebars.registerHelper('le', function(param1, param2, options) {
			if (param1 <= param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// equal
		Handlebars.registerHelper('eq', function(param1, param2, options) {
			if (param1 == param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// not equal
		Handlebars.registerHelper('ne', function(param1, param2, options) {
			if (param1 != param2) {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// not null
		Handlebars.registerHelper("isnotblank", function(param1, options) {
			if (param1 != undefined && param1 != null && param1 != ""
					&& param1 != "null") {
				return options.fn(this);
			} else {
				return options.inverse(this);
			}
		});

		// null
		Handlebars.registerHelper("isblank", function(param1, options) {
			if (param1 != undefined && param1 != null && param1 != ""
					&& param1 != "null") {
				return options.inverse(this);
			} else {
				return options.fn(this);
			}
		});

		var html = template(data);

		if (isAppend != undefined && isAppend === true) {
			$wrapper.append(html);
		} else {
			$wrapper.html(html);
		}
	}

	/**
	 * 微课展开或者收缩面板
	 */
	uh.togglePanel = function($panelDom) {
		if ($panelDom && $panelDom.length > 0) {
			// 是否可以折叠
			var isCollapse = $panelDom.attr("data-collapse") || false;
			if (isCollapse == "true") {
				var hasOpen = $panelDom.hasClass("open");
				if (hasOpen === true) {
					$panelDom.removeClass("open");
					// $panelDom.children(":not(.uh-panel-title)").hide();
					$panelDom.children(":not(.uh-panel-title)").slideUp();
				} else {
					$panelDom.addClass("open");
					// $panelDom.children(":not(.uh-panel-title)").show();
					$panelDom.children(":not(.uh-panel-title)").slideDown();
				}
			}
		} else {
			throw new Error("cann't find element: " + $panelDom.selector);
		}
	}

	/**
	 * 微课iscroll
	 * 
	 * .uh-header .uh-body .uh-footer
	 * 
	 * 默认uh-body作为滚动区域，.uh-header固定头部，.uh-footer固定尾部
	 * 
	 * @param $uhscrollDom
	 *            这里就是uh-body对应的id
	 * 
	 */
	uh.scroll = function($uhscrollDom) {

		// 使滚动区域的高度占满屏幕剩余区域
		var headerheight = $uhscrollDom.siblings(".uh-header").height() || 0;
		var footerheight = $uhscrollDom.siblings(".uh-footer").height() || 0;
		$uhscrollDom.height($("body").height() - headerheight - footerheight
				- 3);
		var uhscroll = new IScroll($uhscrollDom.get(0), {
			// 是否使用滚轮
			mouseWheel : false,
			// 是否允许点击
			click : true,
			// 是否允许轻触，这里使用Tap替代click事件
			tap : true,
			// 列出哪些元素不屏蔽默认事件；
			preventDefaultException : {
				tagName : /^(INPUT|TEXTAREA|BUTTON|SELECT)$/
			}
		});
		return uhscroll;
	}

	/**
	 * 初始化swiper
	 * 
	 * @param $uhswiperDom
	 *            要滑动的Dom组件
	 * @param options
	 *            swiper选项
	 */
	uh.swiper = function($uhswiperDom, options) {
		var uhswiper = new Swiper($uhswiperDom.get(0), options);
		return uhswiper;
	}

	/**
	 * 微课tabs组件
	 * 
	 * var options = { tabIndex : 0, activeClass : "active", ontabchange :
	 * function(tabIndex) { console.log("ontabchange: " + tabIndex); },
	 * onslideChange : function(activeIndex) { console.log("onslideChange: " +
	 * activeIndex); } };
	 * 
	 * var uhtabs = uh.tabs($("#doc_tabs"), options); var swiperActiveIndex =
	 * uhtabs.swiper.activeIndex; // 提供一个切换tab的事件 uhtabs.changeTab = changeTab; //
	 * 提供一个切换swiper的事件 uhtabs.changeSwiper = changeSwiper;
	 * 
	 */
	uh.tabs = function tabs($tabDom, options) {
		// 声明uhtabs组件
		var uhtabs = {};

		var $tabs = $tabDom.find(".uh-tabs");// 上半部分的tabs组件
		var $swiper = $tabDom.find(".uh-swiper");// 下半部分的swiper组件

		// 判断是否有uh-tabs组件和uh-swiper组件
		if ($tabs.length == 0) {
			throw new Error("couldn't find class '.uh-tabs' in '"
					+ $tabDom.selector);
		}
		if ($swiper.length == 0) {
			throw new Error("couldn't find class '.uh-swiper' in '"
					+ $tabDom.selector);
		}

		// 获取options参数
		var index = options.tabIndex || 0;
		var activeClass = options.activeClass || "active";
		var ontabchange = options.ontabchange || undefined;
		var onslideChange = options.onslideChange || undefined;

		// 初始化swiper组件
		var tabswiper = new Swiper($swiper.get(0), {
			autoHeight : true, // enable auto height
			spaceBetween : 20,
			on : {
				slideChange : function() {
					var swiperindex = this.activeIndex;
					changeSwiper(swiperindex);
				},
			},
		});

		var $tabitems = $tabs.find(".uh-tab");
		// tab点击事件
		$tabitems.click(function() {
			var index = $(this).index();
			changeTab(index);
		})

		// 组件初始化完成后，滑动到默认的索引
		var $defaultTab = $($tabitems.get(index));
		if ($defaultTab && $defaultTab.length > 0) {
			$defaultTab.click();
		}

		// tab切换的事件
		function changeTab(tabactiveindex) {
			var index = tabactiveindex;
			var $this = $($tabitems.get(tabactiveindex));
			$this.siblings().removeClass(activeClass);
			$this.addClass(activeClass);

			// 判断是否需要滑动swiper
			var swiperid = $this.attr("data-swiper-id");
			var swiperindex = $("#" + swiperid).index();
			if (swiperindex != tabswiper.activeIndex) {
				tabswiper.slideTo(swiperindex, 500, false);
			}

			// 回调点击事件
			if (ontabchange && $.isFunction(ontabchange)) {
				ontabchange(index);
			}
		}

		// swiper切换事件
		function changeSwiper(swiperindex) {

			// 判断是否需要让tab切换
			var $swiperslideItems = $swiper
					.find(".swiper-wrapper .swiper-slide");
			var $activeSwiper = $swiperslideItems.get(swiperindex);
			var swiperid = $activeSwiper.id;
			var $tabactive = $tabs.find(".uh-tab[data-swiper-id='" + swiperid
					+ "']")
			var tabactiveindex = $tabactive.index();
			if (!$tabactive.hasClass(activeClass)) {
				changeTab(tabactiveindex);
			}

			// 滑动的回调事件
			if (onslideChange && $.isFunction(onslideChange)) {
				onslideChange(swiperindex);
			}
		}

		// 返回tab下的 swiper对象
		uhtabs.swiper = tabswiper;
		// 提供一个切换tab的事件
		uhtabs.changeTab = changeTab;
		// 提供一个切换swiper的事件
		uhtabs.changeSwiper = changeSwiper;

		return uhtabs;
	}

	/**
	 * address组件API: https://topoadmin.github.io/address/
	 * 
	 * uh.address($("#address"), options);
	 * 
	 * @param $addressDom
	 *            要弹出选择地区窗口的Dom元素
	 * @param options
	 *            选项
	 * 
	 * @options.title String '请选择地址' 窗口 title
	 * @options.prov String '北京' 省级
	 * @options.city String '北京市' 市级
	 * @options.district String '东城区' 县区级
	 * @options.selectNumber Int 0 配置可选项(2只选省市，1只选省)
	 * @options.scrollToCenter Boolean false 打开选择窗口时已选项是否滚动到中央
	 * @options.autoOpen Boolean false 是否自动打开选择窗口
	 * @options.customOutput Boolean false 自定义选择完毕输出，不执行内部填充函数
	 * @options.selectEnd Function false 选择完毕回调事件 return
	 *                    {prov,city,district,zip},address
	 * 
	 * 
	 */
	uh.address = function($addressDom, options) {
		$addressDom.address({
			prov : options.prov,// 默认选中的省
			city : options.city,// 默认选中的市
			district : options.district,// 默认选中的区
			selectNumber : options.selectNumber,// 设置省市县可选择个数 1-2,默认为3个都可选
			scrollToCenter : options.scrollToCenter,// 是否将已选位置滚动到中央
			autoOpen : options.autoOpen,// 是否自动打开
			customOutput : options.customOutput,
			selectEnd : options.selectEnd
		});
	};

	/**
	 * actionsheet辅助操作类 // 初始化actionsheet组件 uh.actionsheet($("#relation"),
	 * function($this, index) { console.log("u click the " + index + " item"); },
	 * 0);
	 * 
	 * @param $target
	 *            要弹出actionsheet的Dom元素（请不要使用表单元素，如input，建议使用label）
	 * @param itemCallback
	 *            item点击事件的回调函数
	 * @param index
	 *            要初始化第index个item的值
	 */
	uh.actionsheet = function($target, itemCallback, index) {

		var hiddeninputid = $target.attr("data-am-hidden");
		// 要弹出的model的id
		var modalid = actionsheetOptions($target.attr('data-am-modal')).target;
		// modal item点击事件
		$(modalid + " .am-modal-item").click(function() {
			var currentIndex = $(this).index(".am-modal-item");
			$(hiddeninputid).val($(this).attr("data-val"));
			$target.text($(this).text());
			// item点击回调事件
			if (itemCallback && $.isFunction(itemCallback)) {
				itemCallback($(this), currentIndex);
			}
		});

		// 默认选中第index个item
		var defaultIndex = -1;
		if (index != undefined && !isNaN(index)) {
			defaultIndex = index;
		}
		if (defaultIndex >= 0) {
			var $defaultItem = $(modalid + " .am-modal-item:eq(" + defaultIndex
					+ ")");
			$(hiddeninputid).val($defaultItem.attr("data-val"));
			$target.text($defaultItem.text());
		}
	}

	/**
	 * actionsheetPlus辅助操作类（升级版） // 初始化actionsheet组件
	 * uh.actionsheet($("#relation"), function($this, index) { console.log("u
	 * click the " + index + " item"); }, 0);
	 * 
	 * @param $target
	 *            要弹出actionsheet的Dom元素（请不要使用表单元素，如input，建议使用label）
	 * @param itemCallback
	 *            item点击事件的回调函数
	 * @param index
	 *            要初始化第index个item的值
	 */
	uh.actionsheetPlus = function($target, itemCallback, index) {

		var hiddeninputid = $target.attr("data-am-hidden");
		// 要弹出的model的id
		var modalid = actionsheetOptions($target.attr('data-am-modal')).target;
		// modal item点击事件
		$(modalid + " .am-modal-item").click(function() {
			var currentIndex = $(this).index(".am-modal-item");
			$(hiddeninputid).val($(this).attr("data-val"));
			$target.text($(this).find(".am-modal-itemtext").text());
			// item点击回调事件
			if (itemCallback && $.isFunction(itemCallback)) {
				itemCallback($(this), currentIndex);
			}
		});

		// 默认选中第index个item
		var defaultIndex = -1;
		if (index != undefined && !isNaN(index)) {
			defaultIndex = index;
		}
		if (defaultIndex >= 0) {
			var $defaultItem = $(modalid + " .am-modal-item:eq(" + defaultIndex
					+ ")");
			$(hiddeninputid).val($defaultItem.attr("data-val"));
			$target.text($defaultItem.find(".am-modal-itemtext").text());
		}
	}

	/**
	 * 将data-am-modal的值转js对象
	 */
	function actionsheetOptions(string) {
		if ($.isPlainObject(string)) {
			return string;
		}

		var start = (string ? string.indexOf('{') : -1);
		var options = {};

		if (start != -1) {
			try {
				options = (new Function('', 'var json = '
						+ string.substr(start)
						+ '; return JSON.parse(JSON.stringify(json));'))();
			} catch (e) {
			}
		}

		return options;
	}

	/**
	 * uh全局调用的js
	 */
	function inituhjs() {
		$().ready(
				function() {
					// 初始化折叠面板
					$(".uh-panel>.uh-panel-title").click(function() {
						uh.togglePanel($(this).parent());
					});

					// 微课表单移除input border
					$(".uh-form .uh-input").focus(function() {
						$(this).css("border", "1px solid #ccc");
					});

					// 设置uhbody的高度（除去固定头部的高度和固定尾部的高度）
					var $body = $(".uh-body");
					if ($body.length > 0) {
						var fixedHeaderHeight = 0;
						var fixedFooterHeight = 0;

						if ($body.hasClass("uh-header-fixed")
								&& $(".uh-header.uh-header-fixed").length > 0) {
							fixedHeaderHeight = $(".uh-header.uh-header-fixed")
									.height();

							$body.css("padding-top", fixedHeaderHeight + "px");
						}
						if ($body.hasClass("uh-footer-fixed")
								&& $(".uh-footer.uh-footer-fixed").length > 0) {
							fixedFooterHeight = $(".uh-footer.uh-footer-fixed")
									.height();
							$body.css("padding-bottom", fixedFooterHeight
									+ "px");
						}
					}
				});
	}

	// 微课全局组件初始化
	inituhjs();

	window.uh = uh;

}($, uhlayer);