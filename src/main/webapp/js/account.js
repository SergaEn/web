angular.module('account', ['ui.router', 'ngResource'])

    .factory('sessionService', function ($http, $state) {
        var session = {};
        session.login = function (data) {
            return $http.post("/login", "username=" + data.username +
            "&password=" + data.password, {
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (success) {
                localStorage.setItem("session", {});
                alert("Регистрация успешно завершина! \n Добро пожаловать - " + data.username);
            }).error(function (data, status) {

            });
        };
        session.logout = function () {
            localStorage.removeItem("session");
        };
        session.isLoggedIn = function () {
            return localStorage.getItem("session") !== null;
        };
        return session;
    })

    .factory('accountService', function ($resource, $http) {
        var service = {};

        service.getAccountById = function (accountId) {
            var Account = $resource("/api/accounts/:paramAccountId");
            return Account.get({paramAccountId: accountId});
        };
        service.getAllAccounts = function () {
            var Account = $resource("/api/accounts");
            return Account.query();
        };

        service.getAuthorizedAccount = function () {
            return $http.get("/api/authedaccounts").success(function (success) {
            }).error(function (data, status) {
            });
        };

        return service;
    })


    .controller('LoginCtrl', function ($scope, $http, $modal, sessionService, $state) {
        $scope.isLoggedIn = sessionService.isLoggedIn;
        $scope.logout = sessionService.logout;

        $scope.login = function () {
            var modalInstance = $modal.open({
                templateUrl: '/partials/login.html',
                controller: 'LoginModelCtrl',
                scope: $scope
            });

            modalInstance.result.then(
                function (account) {
                    $scope.username = account.username;
$state.go("phones");
                },
                function () {
                }
            );
        };

        $scope.addVisa = function () {
            var modalInstance = $modal.open({
                templateUrl: '/partials/addVisa.html',
                controller: 'AddNewVisaCtrl',
                scope: $scope
            });
        };
        $scope.register = function () {
                var modalInstance = $modal.open({
                templateUrl: '/partials/register.html',
                controller: 'RegisterCtrl',
                scope: $scope
            });
            modalInstance.result.then(
                function (account) {
                    sessionService.login(account).success(function (data) {
                        $scope.username = account.username;
                    });
                },
                function () {
                }
            );
        };
    })

    .controller('RegisterCtrl', function ($scope, $modalInstance, $http, sessionService, $state) {
        $scope.account = {
            username: null,
            password: null
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.submit = function () {
            $state.reload();
            $scope.submitting = true;
            $http({
                method: 'POST',
                url: '/api/register/',
                data: $scope.account
            }).success(function (data) {
                $scope.submitting = false;
                $modalInstance.close($scope.account);
            }).error(function (data, status) {
                $scope.submitting = false;
                if (status === 400)
                    $scope.badRequest = "Что-то пошло не так....";
                else if (status === 409)
                    $scope.badRequest = 'Имя: ' + $scope.account.username + ' уже занято!';
                else if (status === 422)
                    $scope.badRequest = 'Слишком мало символов....';
            });
        };
    })

    .controller('LoginModelCtrl', function ($scope, $modalInstance, sessionService, $state) {
        $scope.account = {
            username: null,
            password: null
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.submit = function () {
            $scope.submitting = true;
            sessionService.login($scope.account)
                .success(function (data) {
                    $scope.submitting = false;
                    $modalInstance.close($scope.account);
                }).error(function (data, status) {
                    $scope.submitting = false;
                    if (status === 400)
                        $scope.badRequest = "Что-то пошло не так....";
                    else if (status === 409)
                        $scope.badRequest = 'Пользователь не найден!';
                    else if (status === 422)
                        $scope.badRequest = 'Неверный пароль!';
                    else if (status === 401)
                        $scope.badRequest = 'Неверный логин и пароль!';
                });
        };
    })

    .controller('AddNewVisaCtrl', function ($scope, $modalInstance, $http, $window) {

        $scope.visa = {
            firstName: null,
            lastName: null,
            cartName: null,
            expirationDate: null,
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
                console.log("VISA data " + typeof data);
                $scope.submitting = false;
                $window.alert("Карта добавлена!");
                $modalInstance.close(data);
            }).error(function (data, status) {
                $scope.submitting = false;
                if (status === 400)
                    $scope.badRequest = "Что-то пошло не так....";
                else if (status === 409)
                    $scope.badRequest = 'Карта с таким номером: ' + $scope.visa.cartNumber + ' уже существует!';
                else if (status === 422)
                    $scope.badRequest = 'Не все поля заполенены!';
            });
        };
    })

;