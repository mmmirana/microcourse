!function($) {

	// 微课js工具类
	var uhutils = {}

	// 获取当前毫秒数时间戳
	uhutils.getTimestamp = function() {
		return $.now();
	}

	// 获取从x-y的随机数（包含x,不包含y）
	uhutils.getRandomNum = function(x, y) {
		var randomnum = Math.floor(Math.random() * (y - x) + x);
		return randomnum;
	}

	window.uhutils = uhutils;
}($);
