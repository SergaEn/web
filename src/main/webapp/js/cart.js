angular.module('cartForm', ['ui.router', 'ngResource', 'phones', 'account', 'orderForm'])

    .config(function config($stateProvider) {
        $stateProvider.state('manageCart', {
                url: '/cart?name',
                views: {
                    'main': {
                        templateUrl: 'partials/cart.html',
                        controller: 'CartCtrl'
                    }
                },
            data: {pageTitle: "Корзина"}
            });
    })

    .factory('cartService', function ($resource, $http) {
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

        service.getPhonesToCart = function(accountId , data) {
            return $http({
                url: "/api/cart",
                method: "GET",
                params: {accountId: accountId},
                data: data
            }).success(function (success) {

            }).error(function (data, status) {
                alert("bad " + data);
            });
        };

        service.addOrder = function (accName, phones, order) {
           return $http({
               url: "/api/order",
               method: "POST",
               params: {name: accName},
               data: JSON.stringify({phones: phones, order: order})
           }).success(function (success) {

           }).error(function (data, status) {
               alert("bad " + data);
           });
        };


        return service;
    })


    .controller('CartCtrl', function ($scope, phoneService, cartService, accountService, $state, $stateParams) {
        var phone = cartService.getAllPhonesToLocal();
        accountService.getAuthorizedAccount().success(function (account) {
            $scope.account = account;
            console.log("Cart " + $scope.account.username);
        });


            if (phone) {
                phoneService.getAllPhonesToCart(phone)
                    .success(function (data) {
                        $scope.items = data;
                    });
            }
        ;




        $scope.setImage = function (imageUrl) {
            $scope.mainImageUrl = imageUrl;
        };

        // -1 товар
        $scope.minus = function (index) {
            if ($scope.items[index].count > 0) {
                $scope.cartform.$setDirty();
                $scope.items[index].count--;
            }
        };
        // +1 товар

        $scope.plus = function (index) {
            $scope.cartform.$setDirty();
            $scope.items[index].count++;
        };

        $scope.removeItem = function (item) {
            var index = $scope.items.indexOf(item);
            $scope.items.splice(index, 1);
            cartService.removeItem();
            angular.forEach($scope.items, function (item) {
                cartService.toCart(item.id);
            });

        };
        // подсчет итоговой суммы

        $scope.total = function () {
            var total = 0;
            angular.forEach($scope.items, function (item) {
                total += item.count * item.cost;
            });
            return total;
        };
        // проверка корзины на пустоту

        $scope.has_items = function () {
            return $scope.items != null;
        };

        $scope.send = function () {
            var order = $scope.order;
            order.summ = $scope.total();

            cartService.addOrder($stateParams.name, $scope.items, order).success(function (data) {
                alert("Ваш заказ успешно обработан, в ближайшее время с вами свяжутся...")
                $state.go("phones");
            }).error(function (data, status) {
                console.log("error " + status + " " + data);

            });


        };
        // модель заявки

        $scope.order = {
            name:'',
            email: '',
            phone: '',
            address: '',
            comments: '',
            orderDate: new Date()
        };


    });