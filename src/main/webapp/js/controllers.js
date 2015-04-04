'use strict';

/* Controllers */

var phonecatControllers = angular.module('phonecatControllers', [])

phonecatControllers.controller('PhoneListCtrl', function ($scope, $http, $modal) {
    $http.get('/api/phones').success(function (data) {
        $scope.phones = data;
    });

    $scope.openNewBuyDlg = function (phone) {
        var modalInstance = $modal.open({
            templateUrl: '/partials/newBuy.html',
            controller: 'AddNewBuyCtrl',
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


    $scope.openNewDlg = function (total) {
        var modalInstance = $modal.open({
            templateUrl: '/partials/newBuy.html',
            controller: 'AddNewCtrl',
            scope: $scope,
            resolve: {
                phone: function () {
                    return $scope.phones[1];
                },
                total: function () {
                    return total;
                }
            }
        });

        /*    modalInstance.result.then(
         function(newPhone) {
         console.log(phone)
         phone.count = newPhone.count;
         },
         function() {
         }
         );*/

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
});

phonecatControllers.controller('PhoneDetailCtrl', function ($scope, $http, $routeParams, $modal) {
    $http.get('/api/phones/' + $routeParams.id).success(function (phone) {
        $scope.mainImageUrl = phone.images[0];
        $scope.phone = phone;
    });

    $scope.setImage = function (imageUrl) {
        $scope.mainImageUrl = imageUrl;
    }

    $scope.openNewBuyDlg = function () {
        var modalInstance = $modal.open({
            templateUrl: '/partials/newBuy.html',
            controller: 'AddNewBuyCtrl',
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


});


phonecatControllers.controller('AddNewBuyCtrl', function ($scope, $modalInstance, $http, phone, $window) {

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
            url: '/api/payToVisa/' + phone.id + '',
            data: $scope.visa
        }).success(function (data) {
            $scope.submitting = false;
            $window.alert("Покупка произведена успешно!");
            /* */
            /*   console.log(data.count);
             console.log( phone.count);
             console.log($scope.phone.count);*/
            /*
             phone.count = data.count;*/
            $modalInstance.close(data);
        }).error(function (data, status) {
            $scope.submitting = false;
            if (status === 400)
                $scope.badRequest = data;
            else if (status === 409)
                $scope.badRequest = 'Карта не найдена!';
            else if (status === 422)
                $scope.badRequest = 'Недостаточно средств на карте!';


        });
    };
});


phonecatControllers.controller('PhoneCountCtrl', function ($scope) {
    $scope.flagVisible = false;

    $scope.toCart = function () {
        $scope.flagVisible = !$scope.flagVisible;
        $scope.addProduct = "Покупка произведена успешно!"
    };


});


phonecatControllers.controller('AddNewCtrl', function ($scope, $modalInstance, $http, phone, $window, total) {

    console.log(phone);
    $scope.visa = {
        firstName: 'Sergei',
        lastName: 'En',
        cartName: 'visa',
        cartNumber: 123456789,
        expirationDate: '2015-05-20',
        cvv: 999,
        summ: total
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
            url: '/api/payToVisa/' + phone.id + '',
            data: $scope.visa
        }).success(function (data) {
            $scope.submitting = false;
            $window.alert("Покупка произведена успешно!");
            /* */
            /*   console.log(data.count);
             console.log( phone.count);
             console.log($scope.phone.count);*/
            /*
             phone.count = data.count;*/
            $modalInstance.close(data);
        }).error(function (data, status) {
            $scope.submitting = false;
            if (status === 400)
                $scope.badRequest = data;
            else if (status === 409)
                $scope.badRequest = 'Карта не найдена!';
            else if (status === 422)
                $scope.badRequest = 'Недостаточно средств на карте!';


        });
    };
});


phonecatControllers.controller('authorization', function ($scope, $http, $modal) {
    $scope.authorization = true;
    $scope.username = 'Супер магазин по продаже телефонов!!';

    $scope.login = function () {
        var modalInstance = $modal.open({
            templateUrl: '/partials/login.html',
            controller: 'loginCtrl',
            scope: $scope

        });

        modalInstance.result.then(
            function (account) {
                $scope.username = 'Добро пожаловать в наш магазин: ' + account.username;
                console.log($scope.username)
                $scope.authorization = !$scope.authorization;

            },
            function () {
            }
        );

    };

    $scope.addVisa = function () {
        var modalInstance = $modal.open({
            templateUrl: '/partials/addVisa.html',
            controller: 'addVisaCtrl',
            scope: $scope

        });

        modalInstance.result.then(
            function (account) {
                $scope.username = 'Добро пожаловать в наш магазин: ' + account.username;
                console.log($scope.username)
                /* $scope.authorization = !$scope.authorization;*/

            },
            function () {
            }
        );

    };


});

phonecatControllers.controller('addVisaCtrl', function ($scope, $http, $modal) {
    /* $scope.authorization = true;*/
    $scope.username = 'Супер магазин по продаже телефонов!!';

    $scope.addVisa = function () {
        var modalInstance = $modal.open({
            templateUrl: '/partials/addVisa.html',
            controller: 'addNewVisaCtrl',
            scope: $scope

        });

        modalInstance.result.then(
            function (account) {
                $scope.username = 'Добро пожаловать в наш магазин: ' + account.username;
                console.log($scope.username)
                /* $scope.authorization = !$scope.authorization;*/

            },
            function () {
            }
        );

    };


});


phonecatControllers.controller('loginCtrl', function ($scope, $modalInstance, $http, $window) {

    $scope.account = {
        username: null,
        password: null
    };


    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.submit = function () {
        $scope.submitting = true;
        $http({
            method: 'POST',
            url: '/api/accout/',
            data: $scope.account
        }).success(function (data) {
            $scope.submitting = false;
            $window.alert("Авторизация успешно завершина!");
            $modalInstance.close(data);
        }).error(function (data, status) {
            $scope.submitting = false;
            if (status === 400)
                $scope.badRequest = data;
            else if (status === 409)
                $scope.badRequest = 'Пользователь не найден!';
            else if (status === 422)
                $scope.badRequest = 'Неверный пароль!';


        });
    };
});


phonecatControllers.controller('addNewVisaCtrl', function ($scope, $modalInstance, $http, $window) {

    $scope.account = {
        username: null,
        password: null
    };

    $scope.visa = {
        firstName: null,
        lastName: null,
        cartName: null,
        expirationDate: new Date(),
        cvv: null,
        cartNumber: null,
        summ: null

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
            url: '/api/addVisa/',
            data: $scope.visa
        }).success(function (data) {
            $scope.submitting = false;
            $window.alert("Карта добавлена!");
            $modalInstance.close(data);
        }).error(function (data, status) {
            $scope.submitting = false;
            if (status === 400)
                $scope.badRequest = data;
            else if (status === 409)
                $scope.badRequest = 'Карта с таким номером уже существует!';
            else if (status === 422)
                $scope.badRequest = 'Не все поля заполенены!';


        });
    };
});


phonecatControllers.controller('registration', function ($scope, $http, $modal) {
    $scope.authorization = true;
    $scope.username = 'Супер магазин по продаже телефонов!!';
    $scope.register = function () {
        var modalInstance = $modal.open({
            templateUrl: '/partials/register.html',
            controller: 'registerCtrl',
            scope: $scope

        });

        modalInstance.result.then(
            function (account) {
                $scope.username = 'Добро пожаловать: ' + account.username;
                console.log($scope.username)
                $scope.authorization = !$scope.authorization;
            },
            function () {
            }
        );

    };


});

phonecatControllers.controller('registerCtrl', function ($scope, $modalInstance, $http, $window) {

    $scope.account = {
        username: null,
        password: null
    };


    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.submit = function () {
        $scope.submitting = true;
        $http({
            method: 'POST',
            url: '/api/register/',
            data: $scope.account
        }).success(function (data) {
            $scope.submitting = false;
            $window.alert("Регистрация успешно завершина!");
            /* */
            /*   console.log(data.count);
             console.log( phone.count);
             console.log($scope.phone.count);*/
            /*
             phone.count = data.count;*/
            $modalInstance.close(data);
        }).error(function (data, status) {
            $scope.submitting = false;
            if (status === 400)
                $scope.badRequest = data;
            else if (status === 409)
                $scope.badRequest = 'Такое имя уже занято!';
            else if (status === 422)
                $scope.badRequest = 'Слишком мало символов....';


        });
    };
});

