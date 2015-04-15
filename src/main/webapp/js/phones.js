angular.module('phones', ['ui.router', 'ngResource', 'ui.bootstrap'])

    .config(function config($stateProvider) {
        $stateProvider.state('phones', {
            url: '/phones',
            views: {
                'main': {
                    templateUrl: 'partials/phone-list.html',
                    controller: 'PhoneListCtrl'
                }
            },
            resolve: {
                phones: function (phoneService) {
                    return phoneService.getAllPhones();
                }
            },
            data: {pageTitle: 'Home'}
        })
            .state('phone', {
                url: '/phones/:phoneId',
                views: {
                    'main': {
                        templateUrl: 'partials/phone-detail.html',
                        controller: 'PhoneDetailCtrl'
                    }
                },
                resolve: {
                    phone: function (phoneService, $stateParams) {
                        return phoneService.getPhoneById($stateParams.phoneId);
                    }
                },
                data: {pageTitle: 'Phones'}
            });
    })

    .factory('phoneService', function ($resource) {
        var service = {};

        service.getAllPhones = function () {
            var Phones = $resource("/api/phones");
            return Phones.query();

        };

        service.getPhoneById = function (phoneId) {
            var Phone = $resource("/api/phones/:phoneId");
            return Phone.get({phoneId: phoneId});
        };

        return service;
    })
    .controller('PhoneListCtrl', function ($scope, $modal, phones, phoneService) {
        $scope.phones = phones;

        $scope.openNewBuyDlg = function (phone) {
            var modalInstance = $modal.open({
                templateUrl: '/partials/newBuy.html',
                controller: 'NewBuyPhoneCtrl',
                scope: $scope,
                resolve: {
                    phone: function () {
                        return phone;
                    }
                }
            });

            modalInstance.result.then(
                function (newPhone) {
                    console.log(phone)
                    phone.count = newPhone.count;
                },
                function () {
                }
            );
        };



        $scope.flagVisible = false;
        /* $scope.toCart = function() {
         $scope.flagVisible = !$scope.flagVisible;
         $scope.addProduct = "Товар добавлен в корзину!"
         };
         */

        $scope.toggleActive = function (s) {
            s.active = !s.active;
            $scope.flagVisible = true;

        };

        $scope.total = function () {
            var total = 0;

            angular.forEach($scope.phones, function (s) {
                if (s.active) {
                    total += s.cost;
                }
            });

            return total;
        };
    })
    .controller('PhoneDetailCtrl', function ($scope, $modal, phone) {
        console.log(phone);
        $scope.phone = phone;


      $scope.setImage = function (imageUrl) {
            $scope.mainImageUrl = imageUrl;
        }

        $scope.openNewBuyDlg = function () {
            var modalInstance = $modal.open({
                templateUrl: '/partials/newBuy.html',
                controller: 'NewBuyPhoneCtrl',
                scope: $scope,
                resolve: {
                    phone: function () {
                        return $scope.phone;
                    }
                }
            });

            modalInstance.result.then(
                function (newPhone) {
                    $scope.phone.count = newPhone.count;
                },
                function () {
                }
            );
        };
    })
    .controller('NewBuyPhoneCtrl', function ($scope, $modalInstance, $http, phone, $window) {

        console.log(phone);
        $scope.visa = {
            firstName: 'Sergei',
            lastName: 'En',
            cartName: 'visa',
            cartNumber: 123456789,
            expirationDate: '2015-05-20',
            cvv: 999,
            summ: phone.cost
        };

        $scope.boughtOnOptions = {
            'year-format': "'yyyy'",
            'starting-day': 1,
            open: false
        };

        $scope.openBoughtOn = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.boughtOnOptions.open = true;
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.submit = function () {
            $scope.submitting = true;
            $http({
                method: 'POST',
                url: '/api/buyPhone/' + phone.id + '',
                data: $scope.visa
            }).success(function (data) {
                $scope.submitting = false;
                $window.alert("Покупка произведена успешно!");
                $modalInstance.close(data);
            }).error(function (data, status) {
                $scope.submitting = false;
                if (status === 400)
                    $scope.badRequest = data;
                else if (status === 409)
                    $scope.badRequest = 'Карта не найдена!';
                else if (status === 422)
                    $scope.badRequest = 'Недостаточно средств на карте!';
                else if (status === 401)
                    $scope.badRequest = 'Необходимо авторизоваться!';
            });
        };
    })

;