'use strict';

angular
		.module('qingWebApp')
		.controller(
				'countDownController',
				function($scope, $interval) {
					//定义一个局部函数countDown1
					function countDown1() {
						var timeSpanBySeconds = (new Date(2015, 4 - 1, 29, 8,
								0, 0, 0).getTime() - new Date().getTime()) / 1000;
						var days = timeSpanBySeconds / (3600 * 24);
						days = Math.floor(days);
						var hours = (timeSpanBySeconds - days * 3600 * 24) / 3600;
						hours = Math.floor(hours);
						var minutes = (timeSpanBySeconds - days * 3600 * 24 - hours * 3600) / 60;
						minutes = Math.floor(minutes);
						var seconds = timeSpanBySeconds % 60;
						seconds = Math.floor(seconds);
						var str = days + " days " + hours + " hours " + minutes
								+ " minutes " + seconds + " seconds ";// 字符串之间可以用
						return str;
					}

					$scope.countDownString = countDown1();
					$interval(function() {
						$scope.countDownString = countDown1();
					}, 1000);
				});