app.controller('product-ctrl', function($scope, $http) {



	$scope.items = [];
	$scope.form = {};
	$scope.cates = [];
	$scope.field = [];
	$scope.error = ['err'];
	$scope.sort = function(keyname) {
		$scope.sortKey = keyname;
		$scope.reverse = !$scope.reverse;
	}
	$scope.reset = function() {
		$scope.error = ['err'];
		$scope.form = {
			createDate: new Date(),
			image: 'cloud-upload.jpg',
			available: true,
			status: true
		};
		$('#id').attr('readonly', false);
		$('#btn-create').removeAttr('disabled');
		$('#btn-update').attr('disabled', 'disabled');
		$('#btn-delete').attr('disabled', 'disabled');
	}
	$scope.initialize = function() {
		$scope.reset()
		// load products
		$http.get('/rest/products').then(function(response) {
			$scope.items = response.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate);
			})
		});
		// load categories
		$http.get('/rest/categories').then(function(response) {
			$scope.cates = response.data;
			console.log($scope.cates)
		});
	}
	$scope.initialize();

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

	}
	// Cập nhật sản phẩm 

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/products/${item.id}`, item).then(function(response) {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			$scope.reset()
			Swal.fire('Cập nhật thành công !', '', 'success')
		}).catch(function(err) {
			Swal.fire('Cập nhật thất bại !', '', 'error')
			$scope.error = err.data.errors;
		})


	}
	// Thêm sản phẩm 
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/products', item).then(function(response) {
			response.data.createDate = new Date(response.data.createDate);
			console.log(response.data)
			$scope.items.push(response.data);
			$scope.reset();
			Swal.fire('Thêm mới thành công', '', 'success')
		}).catch(err => {
			Swal.fire('Lỗi thêm sản phẩm !', '', 'error')
			console.log(item)
			console.log("Erorr", err.data.errors);

			$scope.error = err.data.errors;

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
			if (result.isConfirmed) {
				if (item.status == false) {
					Swal.fire('Tài khoản đã bị xóa, không thể xóa nữa !', '', 'error');
				} else {
					$http.delete(`/rest/products/${item.id}`).then(function(response) {
						$scope.reset();
						Swal.fire('Đã xóa !', '', 'success')
					}).catch(function(err) {
						Swal.fire('Xóa không thành công !', '', 'error')
						console.log("Erorr", err);
					})
				}
				/*			$http.delete(`/rest/products/${item.id}`).then(function(response) {
								var index = $scope.items.findIndex(p => p.id == item.id);
								$scope.items.splice(index, 1);
								$scope.reset();
								Swal.fire('Đã xóa !', '', 'success')
			
							})*/

			} else if (result.isDenied) {
			}
		})
	}

	$scope.imageChange = function(files) {
		var data = new FormData();
		data.append("file", files[0]);
		$http.post('/rest/upload/images/product', data, {
			transformRequest: angular.entity,
			headers: {
				'Content-Type': undefined
			}
		}).then(response => {
			$scope.form.image = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
			console.log("Erorr", err);
		})

	}
	$scope.$watch('searchText', function(term) {
		$scope.filtered = filterFilter($scope.items, term);
		$scope.size = $scope.filtered.length;
		$scope.noOfPages = Math.ceil($scope.filtered.length / $scope.entryLimit);
	}, true);
	$scope.pager = {
		page: 0,
		size: 6,
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