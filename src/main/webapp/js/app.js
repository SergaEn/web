angular.module('RootApp', [
    'ui.router',
    'account',
    'phones',
    'cartForm',
    'phoneAnimations',
    'ui.bootstrap'


])
    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $stateProvider.state('home', {
            url: '/phones',
            views: {
                'main': {
                    templateUrl: 'index.html',
                    controller: 'AppCtrl'
                }
            },
            data: {pageTitle: 'Home'}
        })

        $urlRouterProvider.otherwise('/phones');

    })

    .controller('AppCtrl', function AppCtrl($scope, accountService) {
        $scope.$on('$stateChangeSuccess', function (event, toState) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle;

}
        });
/*
        $scope.$on('emitFromChild', function(event, fromChild) {
            $scope.account = fromChild;
            console.log(fromChild.username);
        });*/
        accountService.getAuthorizedAccount()
            .success(function (data) {
                $scope.username = data.username;
                $scope.account = data;
                console.log("Авторизированый пользователь !"+data.username);
            });

    })
;
