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
			alert('Cập nhật thành công');
		}).catch(function(err) {
			alert('Lỗi cập nhật sản phẩm');
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
			alert('Thêm mới thành công');
		}).catch(err => {
			alert('Lỗi thêm mới sản phẩm');
			console.log(item)
			console.log("Erorr", err.data.errors);

			$scope.error = err.data.errors;

		})

	}

	// Xóa sản phẩm 
	$scope.delete = function(item) {
		$http.delete(`/rest/products/${item.id}`).then(function(response) {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert('Xóa thành công');

		}).catch(function(err) {
			alert('Lỗi xóa sản phẩm');
			console.log("Erorr", err);
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