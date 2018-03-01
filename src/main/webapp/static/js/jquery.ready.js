/**
 * App页面加载完毕后加载的方法
 */
!function() {
	$().ready(function() {
		// 初始化iscroll
		initIscroll();

		// 初始化图片轮播图
		initSlider();

		// 初始化图片懒加载
		initImgLazyload();

		// 初始化腾讯H5统计
		initTencentMTA();
	});

	// 初始化iscroll
	function initIscroll() {
		var $iscrollDom = $(".iscrollwrapper");
		var length = $iscrollDom.get("length");
		for (var i = 0; i < length; i++) {
			var iscroll = new IScroll($iscrollDom[i]);
		}
	}
	
	// 初始化图片轮播图
	function initSlider() {
		$('.am-slider').flexslider();
	}

	// 初始化图片懒加载
	function initImgLazyload() {
		$("img.lazy").lazyload();
	}

	// 初始化腾讯H5统计
	function initTencentMTA(){
		var _mtac = {};
		(function() {
			var mta = document.createElement("script");
			mta.src = "http://pingjs.qq.com/h5/stats.js?v2.0.4";
			mta.setAttribute("name", "MTAH5");
			mta.setAttribute("sid", "500591194");

			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(mta, s);
		})();
	}

}();