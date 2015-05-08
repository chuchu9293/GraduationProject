angular.module('myApp', []);

angular
		.module('myApp')
		.controller(
				"appController",
				function($scope, $http) {

					// 爬虫系统url默认值
					$scope.url = "http://www.csdn.net";
					$scope.collectObj = new Object();

					// 读取收集系统数据库的统计信息
					$scope.statisticsService = function() {
						$http
								.get(
										"/GraduationProjectCollect/webService/statisticsService")
								.success(
										function(data) {
											$scope.statistics = new Object();
											$scope.statistics.total = data.total;
											$scope.statistics.positiveTotal = data.positiveTotal;
											$scope.statistics.positiveUrl = data.positiveUrl;
											$scope.statistics.positiveLocal = data.positiveLocal;
											$scope.statistics.negativeTotal = data.negativeTotal;
											$scope.statistics.negativeUrl = data.negativeUrl;
											$scope.statistics.negativeLocal = data.negativeLocal;
										}).error(function(data) {
									alert("读取收集系统数据库的统计信息失败");
									console.log(data);
								});
					}

					// 设置初始爬虫系统url
					$scope.setInitialUrl = function(url) {
						$http.get(
								"/GraduationProjectCollect/webService/spiderService/setInitialUrl?initialUrl="
										+ url).success(function(data) {
							alert("设置爬虫的初始url" + url + "成功");
							$scope.nextDisplay();
						}).error(function(data) {
							alert("设置爬虫的初始url" + url + "失败");
							console.log(data);
						});
					}

					// 展示爬虫系统的下一个url并解析此url
					$scope.nextDisplay = function() {
						$scope.getNextUrl().then(function success(data) {
							$scope.urlService($scope.nextUrl)
						});
					}

					$scope.getNextUrl = function() {
						var promise = $http
								.get(
										"/GraduationProjectCollect/webService/spiderService/nextUrl")
								.success(function(data) {
									$scope.nextUrl = data;
								}).error(function(data) {
									alert("获取爬虫系统地nextUrl失败");
									console.log(data);
								});
						return promise;
					}

					// urlService——利用jsoup解析网页
					$scope.urlService = function(url) {
						// angular的$http.post()方法默认数据传输格式是json的
						var urlJson = {
							"url" : url
						};
						$http
								.post(
										"/GraduationProjectCollect/webService/urlService",
										urlJson).success(function(data) {
									$scope.collectObj.url = url;
									$scope.collectObj.title = data.title;
									$scope.collectObj.content = data.content;
								}).error(function(data) {
									alert('网络状况不佳或链接不是html，抓取网页失败');
									console.log(data);
								});
					}

					$scope.collectService = function(isPositive) {
						// 创建请求json
						var collectJson = {
							"url" : $scope.collectObj.url,
							"title" : $scope.collectObj.title,
							"content" : $scope.collectObj.content,
							"isPositive" : isPositive
						};

						// post请求调用collectService
						$http
								.post(
										"/GraduationProjectCollect/webService/collectService",
										collectJson)
								.success(
										function(data) {
											if (data.isOk == "true"
													&& data.docId != "-1") {
												alert('写入数据库成功，存入的docId为：'
														+ data.docId);
											} else {
												alert('可能由于数据库已有该 url记录，写入数据库失败');
											}
											// 展示爬虫系统的下一个url并解析此url
											$scope.nextDisplay();

										}).error(function(data) {
									alert('写入数据库失败');
									console.log(data);
									// 展示爬虫系统的下一个url并解析此url
									$scope.nextDisplay();
								});

					}
				}

		);