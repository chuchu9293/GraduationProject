angular.module('myApp', []);

angular.module('myApp').controller(
		"appController",
		function($scope, $http) {
			$scope.url = "http://www.people.com.cn/";
			$scope.collectObj = new Object();
			$scope.urlSubmit = function() {
				// angular的$http.post()方法默认数据传输格式是json的
				var urlJson = {
					"url" : $scope.url
				};
				$http.post("/GraduationProjectCollect/webService/urlService",
						urlJson).success(function(data) {
					alert(data.title);
					// $scope.collectObj.url=;
					$scope.collectObj.title = data.title;
					$scope.collectObj.content = data.content;
					// $scope.collectObj.isPositive=isPositive;
				}).error(function(data) {
					console.log(data);
				});
			}

			$scope.insertDB = function(url, title, content, isPositive) {
				var collectJson = {
					"url" : url,
					"title" : title,
					"content" : content,
					"isPositive" : isPositive
				};
				$http.post(
						"/GraduationProjectCollect/webService/collectService",
						collectJson).success(function(data) {
					alert(data.title);
				}).error(function(data) {
					console.log(data);
				});

			}
		}

);