angular.module('myApp', []);

angular
		.module('myApp')
		.controller(
				"appController",
				function($scope, $http) {

					// 待判断网页url默认值
					$scope.url = "http://blog.csdn.net/chuchus/article/details/23205283";
					$scope.doc=new Object();

					// 设置待判断的url
					$scope.predictUrlService = function(predictUrl) {
						$http
								.get(
										"/GraduationProjectUse/webService/predictService/predictUrl?url="+predictUrl)
								.success(
										function(data) {
											if( data=="ok"){
												$scope.doc.url = data.url;
											}
											else{
												alert("提交url失败,请重试");
											}
										}).error(function(data) {
									alert("提交url失败，请重试");
									console.log(data);
								});
					}
					
					//读取判断结果
					$scope.getResultService = function() {
						$http
								.get(
										"/GraduationProjectUse/webService/predictService")
								.success(
										function(data) {
											if( data.status=="ok"){
												$scope.doc.url = data.url;//若是本地文件，服务端会对此字段设值“本地文件”
												$scope.doc.title = data.title;
												$scope.doc.content = data.content;
												$scope.doc.token = data.token;
												$scope.doc.vector = data.vector;
												$scope.doc.result=data.result;
												$scope.doc.degree = data.degree;
											}
											else{
												alert("获取判断结果失败,请重试");
											}
										}).error(function(data) {
											alert("获取判断结果失败,请重试");
									console.log(data);
								});
					}
					
					
					
					
					
					
				}
				

		);

//常规js

function  myFileSubmit(){
	document.getElementById("myFileForm").submit();
}
