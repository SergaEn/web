angular.module('orderForm', ['ui.router', 'ngResource'])

    .config(function config($stateProvider) {
        $stateProvider.state('manageOrder', {
            url: '/order?name',
            views: {
                'main': {
                    templateUrl: 'partials/orders.html',
                    controller: 'OrderCtrl'
                }
            },
            resolve: {
                orders: function (orderService, $stateParams) {
                    return orderService.getAllOrdersByAccount($stateParams.name);
                }
            },
            data: {pageTitle: "Заказы"}

        }).state('order', {
            url: '/order/:uuid',
            views: {
                'main': {
                    templateUrl: 'partials/order-detail.html',
                    controller: 'OrderDetailCtrl'
                }
            },
            resolve: {
                phones: function (orderService, $stateParams) {
                    return orderService.getAllPhonesByOrder($stateParams.uuid);
                }
            },
            data: {pageTitle: 'Заказы'}
        });
    })

    .factory('orderService', function ($resource, $http) {
        var service = {};

        service.getAllOrdersByAccount = function (name) {
            var Order = $resource("/api/order");
            return Order.query({name: name});
        };

        service.getAllPhonesByOrder = function (uuid) {
            var OrderPhone = $resource("/api/order/:uuid");
            return OrderPhone.query({uuid: uuid});
        };

        service.addOrder = function (accName, phones, order) {
            return $http({
                url: "/api/order",
                method: "POST",
                params: {name: accName},
                data: JSON.stringify({phones: phones, order: order})
            }).success(function (success) {

            }).error(function (data, status) {
                if (status===401){
                    alert("Необходимо авторизоваться!")
                }
            });
        };

        return service;
    })


    .controller('OrderCtrl', function ($scope, orders) {
        $scope.orders = orders;
    })

    .controller('OrderDetailCtrl', function ($scope, phones) {
        $scope.phones = phones;
        $scope.total = function () {
            var total = 0;
            angular.forEach($scope.phones, function (item) {
                total += item.count * item.cost;
            });
            return total;
        };


    });