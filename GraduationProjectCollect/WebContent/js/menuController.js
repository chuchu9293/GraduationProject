'use strict';
angular.module('qingWebApp').controller('menuController',
		function($scope, $location) {
	//导航栏的内容
	 $scope.sections = [
	                    //{title: 'gallery', link: '/gallery'},
	                    {title: 'countDown', link: '/countDown'}
	                  //  {title: 'menstruation', link: '/menstruation'},
	                  //  {title: 'songs', link: '/songs'}
	                  ];
	 
	    //处理导航栏标签的选中状态
	    $scope.isActive = function (viewLocation) {
	        return viewLocation === $location.path();
	      };
		});
