app.controller('accounts-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.idError = null;
	$scope.emailError = null;
	$scope.error = ['err'];
	$scope.sort = function(keyname) {
		$scope.sortKey = keyname;
		$scope.reverse = !$scope.reverse;
	}
	$scope.initialize = function() {
		$scope.reset()
		$http.get('/rest/accounts/list').then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log(err);
		})
	}

	$scope.reset = function() {
		$scope.idError = null
		$('.username').attr('readonly', false);
		$('#btn-create').removeAttr('disabled');
		$('#btn-update').attr('disabled', 'disabled');
		$('#btn-delete').attr('disabled', 'disabled');
		$scope.error = ['err'];
		$scope.form = {
			activated: true
		};
	}
	// Hiển thị sản phẩm lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		console.log($scope.form)

		$('#btn-create').attr('disabled', 'disabled');
		$('#btn-delete').removeAttr('disabled');
		$('#btn-update').removeAttr('disabled');
		$('.username').attr('readonly', true);
		$('html,body').animate({
			scrollTop: $(".info").offset().top
		},
			'slow');

	}
	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/accounts/my-account/${item.username}`, item).then(function(response) {
			var index = $scope.items.findIndex(p => p.username == item.username);
			$scope.items[index] = item;
			$scope.reset();
			Swal.fire('Cập nhật thành công !', '', 'success')
		}).catch(function(err) {
			Swal.fire('Cập nhật thất bại !', '', 'error')
			$scope.error = err.data.errors;
		})

	}
	// Thêm sản phẩm 
	$scope.create = function() {
		var item = angular.copy($scope.form);
		console.log(item.activated)
		console.log(item.username);
		item.status = true;
		$http.post('/rest/accounts/my-account/save2', item).then(function(response) {
			response.data.createDate = new Date(response.data.createDate);
			$scope.items.push(response.data);
			$scope.reset();
			Swal.fire('Thêm mới thành công', '', 'success')
			$scope.emailError = null;
			$scope.idError = null;
		}).catch(error => {
			Swal.fire('Lỗi thêm tài khoản !', '', 'error')
			console.log("Erorr1", error);
			$scope.error = error.data.errors;
			console.log("Erorr", $scope.error);
		})
	}

	// Xóa sản phẩm 
	$scope.delete = function(item) {
		Swal.fire({
			title: 'Tài khoản sẽ không được xóa mà chỉ ẩn, bạn chắc chứ?',
			showDenyButton: true,
			showCancelButton: true,
			confirmButtonText: `Xóa`,
			denyButtonText: `Không xóa`,
		}).then((result) => {
			/* Read more about isConfirmed, isDenied below */
			if (result.isConfirmed) {
				$http.delete(`/rest/accounts/delete/${item.username}`).then(function(response) {
					var index = $scope.items.findIndex(p => p.username == item.username);
					$scope.items[index] = response.data;
					$scope.reset();
					Swal.fire('Đã xóa !', '', 'success')
				}).catch(function(err) {
					Swal.fire('Xóa không thành công !', '', 'error')
					console.log("Erorr", err);
				})
			} else if (result.isDenied) {
			}
		})

	}

	$scope.imageChange = function(files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.entity,
			headers: {
				'Content-Type': undefined
			}
		}).then(response => {
			$scope.form.photo = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
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
	$scope.imageChange = function(files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.entity,
			headers: {
				'Content-Type': undefined
			}
		}).then(response => {
			$scope.form.photo = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
			console.log("Erorr", err);
		})
	}
	$scope.initialize();
})