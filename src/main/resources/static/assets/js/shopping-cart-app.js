

var app = angular.module('shopping-cart-app', []);
app.directive('passwordConfirm', ['$parse', function($parse) {
	return {
		restrict: 'A',
		scope: {
			matchTarget: '=',
		},
		require: 'ngModel',
		link: function link(scope, elem, attrs, ctrl) {
			var validator = function(value) {
				ctrl.$setValidity('match', value === scope.matchTarget);
				return value;
			}

			ctrl.$parsers.unshift(validator);
			ctrl.$formatters.push(validator);

			// This is to force validator when the original password gets changed
			scope.$watch('matchTarget', function(newval, oldval) {
				validator(ctrl.$viewValue);
			});

		}
	};
}]);
app.controller('ctrl', function($scope, $http) {
	$scope.cart = {
		items: [],
		add(id) {
			var item = this.items.find(item => item.id === id); // kiểm tra id nhập vào có tồn tại trong giỏ hàng hay chưa
			if (item) {
				item.qty++;
				this.saveToLocalStorage();
			} else // nếu chưa tồn tại trong giỏ hàng
				$http.get(`/rest/products/${id}`).then(response => {
					response.data.qty = 1; // set sl = 1
					this.items.push(response.data); // push vào mảng
					this.saveToLocalStorage();
				}).catch(err => {
					console.log(err)
				})
		},
		remove(id) {
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		clear() {
			this.items = [];
			this.saveToLocalStorage();

		},
		amt_of(item) {

		},
		get count() {
			return this.items.map(item => item.qty).reduce((total, qty) => total += qty, 0);
			// lấy ra số lượng trên mỗi sản phẩm
			// rồi cộng lại 

		},
		get amount() {

			let total_price = this.items.reduce((tolPrice, item) => {
				if (item.discount != null && item.discount.discount > 0) {
					tolPrice += (item.qty * (item.price - (item.price * item.discount.discount) / 100));
				} else {
					tolPrice += item.qty * item.price;
				}
				return tolPrice;
			}, 0)
			return total_price;
		},
		loadLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
			// nếu dữ liệu trong local của Items có thì gán vào danh sách Items, ngược lại gán []
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items)); // thực hiện chuyển mảng items sang json 
			localStorage.setItem("cart", json); // lưu vào localStorage
		}
	}
	$scope.cart.loadLocalStorage();
	$scope.payments = {};
	console.log($scope.payments.payments)
	$scope.order = {
		createDate: new Date(),
		address: "",
		account: {
			username: ""
		},
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					product: { id: item.id },
					price: item.price,
					quantity: item.qty,
					discount: item.discount != null ? item.discount.discount : null,
					payments: $scope.payments.payments,
					delivered: true
				}
			})
		},

		purchase() {
			console.log("vào đây")
			var order = angular.copy($scope.order);
			console.log(order)
			// Thực hiện đặt hàng
			console.log(order)
			console.log($scope.payments.payments)
			$http.post("/rest/orders", order).then(resp => {
				Swal.fire({
					title: 'Đặt hàng thành công',
					icon: 'success',
					confirmButtonText: `OK`,
				}).then((result) => {
					/* Read more about isConfirmed, isDenied below */
					if (result.isConfirmed) {
						$scope.cart.clear();
						location.href = "/order/detail/" + resp.data.id;
					}
				})
			}).catch(erro => {
				Swal.fire({
					title: 'Đặt hàng không thành công',
					icon: 'error',
					confirmButtonText: `OK`,
				}).then((result) => {
					/* Read more about isConfirmed, isDenied below */
					if (result.isConfirmed) {
					}
				})
			})
		}
	}

	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.itemsList.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsList.length
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
	$scope.topgiamgia = [];
	$http.get('/rest/products/topgiamgia').then(function(response) {
		$scope.topgiamgia = response.data;
		console.log($scope.topgiamgia)
	}).catch(err => {
		console.log($scope.topgiamgia)
		console.log(err)
	})
})
app.controller('profile', function($scope, $http) {
	$scope.formProfile = {};
	$scope.formPassword = {};
	$scope.itemsAll = [];
	$scope.initialize = function() {

		$http.get('/rest/accounts?admin=false').then(function(response) {
			$scope.itemsAll = response.data;
			console.log($scope.itemsAll)
		}).catch(err => {
			console.log("error", err)
		})

		$http.get('/rest/accounts/my-account').then(function(response) {
			$scope.formProfile = response.data;
			console.log($scope.formProfile)
		}).catch(err => {
			console.log("error", err)
		})
	}
	$scope.reset = function() {
		$scope.formProfile = {};
		$http.get('/rest/accounts/my-account').then(function(response) {
			$scope.formProfile = response.data;
			console.log($scope.formProfile)
		}).catch(err => {
			console.log("error", err)
		})
	}

	$scope.update = function() {
		var item = angular.copy($scope.formProfile);
		$http.put(`/rest/accounts/my-account/${item.username}`, item).then(function(response) {
			var index = $scope.itemsAll.findIndex(p => p.username == item.username);
			$scope.itemsAll[index] = item;
			alert('Cập nhật thành công');
		}).catch(function(err) {
			alert('Lỗi cập nhật tài khoản');
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
			$scope.formProfile.photo = response.data.name; // đây là đường dẫn của file
		}).catch(err => {
			alert("Lỗi hình ảnh");
			console.log("Erorr", err);
		})
	}
	$scope.savePass = function() {
		console.log('asdasd');
		var passOld = $("#passOld").val();
		var pass = $("#passNew").val();
		var valid = pass == $("#CfPassNew").val();
		if (!valid) {
			alert("Mật khẩu không khớp");
			return;
		}
		if (passOld == pass) {
			alert('Mật khẩu mới không được giống mật khẩu cũ');
			return;
		}
		$scope.update();
	}


	$scope.initialize();
});
app.controller('sign-up', function($scope, $http) {
	/*	$scope.form.cfpassword = null;
		$scope.form.password = null;*/
	$scope.form = {};
	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/accounts/my-account/save', item).then(function(response) {
			alert('Đăng ký thành công');
			location.href = "/security/login/form";
		}).catch(error => {
			alert('Lỗi đăng ký');
			console.log("Erorr", error);
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

})

