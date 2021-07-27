var app = angular.module('admin-app', ['ngRoute', 'angularUtils.directives.dirPagination'],);
app.config(function($routeProvider) {
	$routeProvider.when('/product', {
		templateUrl: "/assets/admin/product/index.html",
		controller: 'product-ctrl'
	}).when('/category', {
		templateUrl: "/assets/admin/category/index.html",
		controller: 'category-ctrl'
	}).when('/authorize', {
		templateUrl: "/assets/admin/authority/index.html",
		controller: 'authority-ctrl'
	}).when('/thongke_loai', {
		templateUrl: "/assets/admin/thongke/_thongke_loai.html",
		controller: 'thongke-loai-ctrl'
	}).when('/thongke_sp', {
		templateUrl: "/assets/admin/thongke/_thongke_sp.html",
		controller: 'thongke-sp-ctrl'
	}).when('/thongke_order', {
		templateUrl: "/assets/admin/thongke/_thongke_order.html",
		controller: 'thongke-order-ctrl'
	}).when('/accounts', {
		templateUrl: "/assets/admin/accounts/accounts.html",
		controller: 'accounts-ctrl'
	}).when('/unauthorized', {
		templateUrl: "/assets/admin/authority/unauthorized.html",
		controller: 'authority-ctrl'
	}).when('/order', {
		templateUrl: "/assets/admin/order/index.html",
		controller: 'order-ctrl'
	}).when('/order/thongke', {
		templateUrl: "/assets/admin/order/thongke.html",
		controller: 'thongke-order-ctrl'
	}).otherwise({
		templateUrl: "/assets/admin/homeDashboard/home.html"
	})
})