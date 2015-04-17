angular.module('cartForm', ['ui.router', 'ngResource', 'phones'])

    .config(function config($stateProvider) {
        $stateProvider.state('cartFrom', {
            url: '/cartFrom',
            views: {
                'main': {
                    templateUrl: 'partials/cart.html',
                    controller: 'CartCtrl'
                }
            },
            resolve: {
                phones: function (cartService) {
                    return cartService.getAllPhonesToCart();
                }
            },
            data: {pageTitle: 'Корзина'}
        })
    })

    .factory('cartService', function ($resource) {
        var service = {};
        service.items = [];

        service.getAllPhonesToCart = function () {
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


        return service;
    })


    .controller('CartCtrl', function ($scope, phoneService, cartService) {
        var phone = cartService.getAllPhonesToCart();
        if (phone) {
            phoneService.getAllPhonesToCart(phone)
                .success(function (data) {
                    $scope.items = data;
                })
        }
        $scope.setImage = function (imageUrl) {
            $scope.mainImageUrl = imageUrl;
        }

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

        // флаг, указывающий на процесс обновления корзины на сервере

        $scope.cartproc = false;
        // процесс обновления корзины на сервере

        $scope.save = function () {
            $scope.cartproc = true;
            var items = $scope.items_cart();
            $.post('/cart/cart.php', {'cart': items}, function (data, textStatus, jqXHR) {
                var ids = jQuery.map(data, function (el) {
                    return el.id;
                });
                $scope.items = jQuery.grep($scope.items, function (el) {
                    return jQuery.inArray(el.id, ids) >= 0;
                });
                $scope.cartproc = false;
                // помечаем форму как не тронутую

                $scope.cartform.$setPristine();
                $('#cart-size').html(data.length);
                // применяем изменения модели для обновления представления

                $scope.$apply();
            }, 'json');
        };
        // флаг, указывающий на процесс отправки заявки на сервере

        $scope.orderproc = false;
        // процесс обновления корзины на сервере

        $scope.send = function () {
            $scope.orderproc = true;
            var items = $scope.items_cart();
            var order = $scope.order;
            $.post('/cart/cart.php', {'cart': items, 'order': order}, function (data, textStatus, jqXHR) {
                var ids = jQuery.map(data, function (el) {
                    return el.id;
                });
                $scope.items = jQuery.grep($scope.items, function (el) {
                    return jQuery.inArray(el.id, ids) >= 0;
                });
                $scope.orderproc = false;
                // помечаем форму как не тронутую

                $scope.cartform.$setPristine();
                $('#cart-size').html(data.length);
                // плагин Twitter Bootstrap для модальных окон

                $('#order-alert').modal();
                // применяем изменения модели для обновления представления

                $scope.$apply();
            }, 'json');
        };
        // модель заявки

        $scope.order = {
            name: '',
            email: '',
            phone: '',
            address: '',
            comments: ''
        };


    });