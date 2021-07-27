app.controller('thongke-order-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.itemsThongKeOrder = [];
	$scope.initialize = function() {
		$http.get('/rest/orderDetails/thongke/').then(response => {
			$scope.itemsThongKeOrder = response.data;
			console.log($scope.itemsThongKeOrder);
		}).catch(err => {
			console.log(err);
		})
	}
	$scope.pager = {
		page: 0,
		size: 10,
		get itemsThongKeOrder() {
			var start = this.page * this.size;
			return $scope.itemsThongKeOrder.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsThongKeOrder.length
				/ this.size);
		},
		first() {
			this.page = 0;

		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		},
	}
	$scope.initialize();

})