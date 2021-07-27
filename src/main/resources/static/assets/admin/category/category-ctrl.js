app.controller('category-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.cates = [];
	$scope.error = ['err'];
	$scope.sort = function(keyname) {
		$scope.sortKey = keyname;
		$scope.reverse = !$scope.reverse;
	}
	$scope.initialize = function() {
		// load products
		$http.get('/rest/categories').then(function(response) {
			$scope.items = response.data;
		});
	}
	$scope.initialize();

	$scope.reset = function() {
		$scope.error = ['err'];
		$('#id').attr('readonly', false);
		$('#btn-create').removeAttr('disabled');
		$('#btn-update').attr('disabled', 'disabled');
		$('#btn-delete').attr('disabled', 'disabled');
		$scope.form = {
			status: true
		};
	}
	// Hiển thị sản phẩm lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$('#btn-create').attr('disabled', 'disabled');
		$('#btn-delete').removeAttr('disabled');
		$('#btn-update').removeAttr('disabled');
		$('html,body').animate({
			scrollTop: $(".info").offset().top
		},
			'slow');
		$('#id').attr('readonly', true);
	}
	// Cập nhật sản phẩm 

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/categories/${item.id}`, item).then(function(response) {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			$scope.reset();
			alert('Cập nhật thành công');
		}).catch(function(err) {
			alert('Lỗi cập nhật sản phẩm');
			$scope.error = err.data.errors;
		})


	}
	// Thêm sản phẩm 
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/categories', item).then(function(response) {
			$scope.items.push(response.data);
			$scope.reset();
			alert('Thêm mới thành công');
		}).catch(error => {
			alert('Lỗi thêm mới sản phẩm');
			console.log("Erorr", error);
			$scope.error = error.data.errors;
		})
	}

	// Xóa sản phẩm 
	$scope.delete = function(data) {
		console.log(data)
		$http.delete(`/rest/categories/delete/${data.id}`).then(function(response) {
			console.log(response)
			var index = $scope.items.findIndex(p => p.id == data.id);
			console.log(index)
			console.log(data)
			$scope.items[index] = data;
			$scope.reset();
			alert('Xóa thành công');
		}).catch(function(err) {
			alert('Xóa thất bại');
			console.log("Erorr", err);
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

});