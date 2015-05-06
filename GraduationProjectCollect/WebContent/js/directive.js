'usestrict';

var module1 = angular.module('qingWebApp.directive', []);

module1.directive('datepicker', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs, ngModel) {
			element.datepicker({dateFormat:"yy-mm-dd"});
		}
	};
});

module1.directive('highcharts', function() {
	return {
		// Enforce the angularJS default of restricting the directive to
		// attributes only
		restrict : 'A',
		link : function(scope, element, attrs, ngModel) {
			element.highcharts({

				chart : {
					type : 'columnrange',
					inverted : true
				},
				title : {
					text : 'qing’s menstruation'
				},
				subtitle : {
					text : 'statistics by month'
				},
				xAxis : {
					categories : [ 'Jan', 'Feb', 'Mar', 'Mar(2)', 'Apr' ]
				},
				yAxis : {
					title : {
						text : 'day of month'
					}
				},
				tooltip : {
					valueSuffix : 'th Day'
				},
				plotOptions : {
					columnrange : {
						dataLabels : {
							enabled : true,
							formatter : function() {
								return this.y + 'th Day';
							}
						}
					}
				},
				legend : {
					enabled : false
				},
				series : [ {
					name : 'day of month',
					data : [ [ 16, 23 ], [ 14, 18 ], [ 9, 14 ], [ 30, 30 ],
							[ 1, 3 ] ]
				} ]
			});
		}
	}
})
