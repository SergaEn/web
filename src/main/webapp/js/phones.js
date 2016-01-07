angular.module('phones', ['ui.router', 'ngResource', 'ui.bootstrap', 'basketForm'])

    .config(function config($stateProvider) {
        $stateProvider
            .state('phones', {
                url: '/phones',
                views: {
                    'main': {
                        templateUrl: 'partials/phone-list.html',
                        controller: 'PhoneListCtrl'
                    }
                }/*,
                 resolve: {
                 content: function (phoneService) {
                 return phoneService.getPhones();
                 }
                 }*/,
                data: {pageTitle: 'Home'}
            })
            .state('phone', {
                url: '/phone/:phoneId',
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

    .factory('phoneService', function ($resource, $http) {
        var service = {};

        service.getAllPhones = function (pageNumber) {
            var Phones = $resource("/api/phones/:pageNumber");
            return Phones.get({pageNumber: pageNumber}).success(function (data) {
            });

        };
        service.getPhones = function () {
            return $http.get("/api").success(function (success) {
            }).error(function (data, status) {
            });

        };

        service.getPhoneById = function (phoneId) {
            var Phone = $resource("/api/phone/:phoneId");
            return Phone.get({phoneId: phoneId});
        };

        return service;
    })
    .controller('PhoneListCtrl', function ($scope, $modal, phoneService, basketService, $http) {

        phoneService.getPhones().success(function (data) {
            $scope.phones = data.content;
            $scope.phones = data.content;
            $scope.totalPages = data.totalPages;
            $scope.currentPage = data.number + 1;
            $scope.totalRecords = data.totalElements;

            var pages = [];
            for (var i = 1; i <= data.totalPages; i++) {
                pages.push(i);
            }
            $scope.range = pages;

        });

        $scope.getAllRecords = function (pageNumber) {
            $http.get("/api/phones/" + pageNumber).success(function (data) {
                $scope.phones = data.content;
                $scope.totalPages = data.totalPages;
                $scope.currentPage = data.number + 1;
                $scope.totalRecords = data.totalElements;

                var pages = [];
                for (var i = 1; i <= data.totalPages; i++) {
                    pages.push(i);
                }
                $scope.range = pages;
            });
        };


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
        $scope.toCart = function (data) {
            basketService.toCart(data);
        };


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