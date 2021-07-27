app.controller('order-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.sort = function(keyname) {
		$scope.sortKey = keyname;
		$scope.reverse = !$scope.reverse;
	}
	$scope.initialize = function() {
		$http.get('/rest/orderDetails/list').then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log(err);
		})
	}
	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length
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

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		console.log($scope.form)
		$('#btn-update').removeAttr('disabled');
		$('html,body').animate({
			scrollTop: $("#infoOrdder").offset().top
		},
			'slow');
	}
	$scope.reset = function() {
		$('#btn-update').attr('disabled', 'disabled');
		$scope.form = {
			payments: "Cash"
		};
	}
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/orderDetails/update/${item.id}`, item).then(function(response) {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			$scope.reset()
			alert('Cập nhật thành công');
		}).catch(function(err) {
			alert('Lỗi cập nhật sản phẩm');
			console.log("Erorr", err);
		})


	}
	$scope.initialize();
})