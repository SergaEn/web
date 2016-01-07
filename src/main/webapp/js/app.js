angular.module('RootApp', [
    'ui.router',
    'account',
    'phones',
    'basketForm',
    'phoneAnimations',
    'ui.bootstrap',
    'orderForm'


])
    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/phones');

    })

    .controller('AppCtrl', function AppCtrl($scope, accountService) {
        $scope.$on('$stateChangeSuccess', function (event, toState) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle;
            }
        });

        accountService.getAuthorizedAccount()
            .success(function (data) {
                $scope.username = data.username;
                $scope.account = data;
            });

    })
;
