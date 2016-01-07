angular.module('basketForm', ['ui.router', 'ngResource', 'account', 'orderForm'])

    .config(function config($stateProvider) {
        $stateProvider.state('manageBasket', {
            url: '/basket?name',
                views: {
                    'main': {
                        templateUrl: 'partials/basket.html',
                        controller: 'CartCtrl'
                    }
                },
            data: {pageTitle: "Корзина"}
            });
    })

    .factory('basketService', function ($resource, $http) {
        var service = {};
        service.items = [];

        service.getAllPhonesToLocal= function () {
            return localStorage.getItem("phone");
        };

        service.removeItem = function () {
            localStorage.removeItem("phone");
            this.items = []
        };

        service.toCart = function (data) {
            if (this.items.length === 0) {
                if (localStorage.getItem("phone") !== null) {
                    console.log("Lengs " + this.items.length)
                    this.items.push(localStorage.getItem("phone"));
                }
            }

            this.items.push(data);
            localStorage.setItem("phone", this.items);

        };

        service.getAllPhonesToBasket = function (name, data) {
            return $http({
                url: "/api/basket",
                method: "POST",
                params: {name: name},
                data: data
            })
                .success(function (success) {
            }).error(function (data, status) {
            });
        };
        return service;
    })


    .controller('CartCtrl', function ($scope, basketService, accountService, $state, $stateParams, orderService) {
        var phone = basketService.getAllPhonesToLocal();
        accountService.getAuthorizedAccount().success(function (account) {
            $scope.account = account;
        });
            if (phone) {
                basketService.getAllPhonesToBasket($stateParams.name, phone.split(','))
                    .success(function (data) {
                        angular.forEach(data, function (item) {
                            item.count = 1;
                        })
                        $scope.items = data;
                    });
            }
        ;




        $scope.setImage = function (imageUrl) {
            $scope.mainImageUrl = imageUrl;
        };

        $scope.minus = function (index) {
            if ($scope.items[index].count > 0) {
                $scope.cartform.$setDirty();
                $scope.items[index].count--;
            }
        };

        $scope.plus = function (index) {
            $scope.cartform.$setDirty();
            $scope.items[index].count++;
        };

        $scope.removeItem = function (item) {
            var index = $scope.items.indexOf(item);
            $scope.items.splice(index, 1);
            basketService.removeItem();
            angular.forEach($scope.items, function (item) {
                basketService.toCart(item.id);
            });

        };

        $scope.total = function () {
            var total = 0;
            angular.forEach($scope.items, function (item) {
                total += item.count * item.cost;
            });
            return total;
        };

        $scope.has_items = function () {
            return $scope.items != null;
        };

        $scope.send = function () {
            var order = $scope.order;
            order.summ = $scope.total();

            orderService.addOrder($stateParams.name, $scope.items, order).success(function (data) {
                alert("На ваш адресс отправлено письмо.");
                $state.go("manageOrder", {name: $scope.account.username});
            }).error(function (data, status) {

            });
        };

        $scope.order = {
            name:'',
            email: '',
            phone: '',
            address: '',
            comments: '',
            orderDate: new Date()
        };
    });