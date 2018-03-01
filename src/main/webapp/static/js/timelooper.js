/**
 * javascript定时循环工具
 * 
 * @author wangzf
 */
!function() {

	var timelooper = {};

	/**
	 * 循环
	 * 
	 * @param options
	 *            包含5个参数 <br>
	 *            totalTimes： 执行的总次数，如果为0，则一致循环，不会退出<br>
	 *            interval： 执行的间隔时间，默认为1s<br>
	 *            startEvent： 循环开始之前执行的事件<br>
	 *            looperEvent： 循环执行的事件，有一个参数index代表当前执行的次数，从1开始<br>
	 *            stopEvent：循环结束之后执行的事件
	 */
	function loop(options) {
		// 总次数
		var totalTimes = options.totalTimes || 0;

		// 间断的时长，默认为1s
		var interval = options.interval || 1000;

		// 开始的事件
		var startFun = options.startEvent || new Function();
		// 循环的事件
		var looperFun = options.looperEvent || new Function();
		// 结束的事件
		var stopFun = options.stopEvent || new Function();

		// 触发开始事件
		startFun();

		// 开始循环
		var myInterval = setInterval(intervalLooper, interval);

		var index = 1;

		// 循环触发机制
		function intervalLooper() {
			if (totalTimes <= 0) {
				looperFun(index++)
			} else {
				var remainTimes = totalTimes - index;
				if (remainTimes > 0) {
					looperFun(index++);
				} else {
					clearInterval(myInterval);
					stopFun();
				}
			}
		}
	}

	timelooper.loop = loop;

	window.timelooper = timelooper;
}();
