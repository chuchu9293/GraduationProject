angular.module('myApp', []);

angular
		.module('myApp')
		.controller(
				"appController",
				function($scope, $http) {

					// 待判断网页url默认值
					$scope.url = "http://blog.csdn.net/chuchus/article/details/23205283";
					$scope.doc=new Object();

					// 读取收集系统数据库的统计信息
					$scope.statisticsService = function() {
						$http
								.get(
										"/GraduationProjectUse/webService/predictUrl?url="+$scope.url)
								.success(
										function(data) {
											if( data.status=="ok"){
												$scope.doc.url = data.url;
												$scope.doc.title = data.title;
												$scope.doc.content = data.content;
												$scope.doc.token = data.token;
												$scope.doc.vector = data.vector;
												$scope.doc.result=data.result;
												$scope.doc.degree = data.degree;
											}
											else{
												$scope.errorDeal(data.status);
											}
										}).error(function(data) {
									alert("WebService通信失败，请重试");
									console.log(data);
								});
					}
					//WebService返回状态码为错误时的处理
					$scope.errorDeal(reason){
						alert(reason);
					}

				}

		);