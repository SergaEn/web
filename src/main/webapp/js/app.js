angular.module('RootApp', [
    'ui.router',
    'account',
    'phones',
    'phoneAnimations',
    'ui.bootstrap'


])
    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/phones');

    })

    .controller('AppCtrl', function AppCtrl($scope, $location) {
        $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle;
            }
        });
    })
    /*   .config(function ($routeProvider) {
     $routeProvider.
     when('/phones', {
     templateUrl: 'partials/phone-list.html',
     controller: 'PhoneListCtrl'
     }).
     when('/phones/:id', {
     templateUrl: 'partials/phone-detail.html',
     controller: 'PhoneDetailCtrl'
     }).
     otherwise({
     redirectTo: '/phones'
     });
     })*/

    /*  .controller( 'AppCtrl', function AppCtrl ( $scope, sessionService) {
    $scope.isLoggedIn = sessionService.isLoggedIn;
        $scope.logout = sessionService.logout;
     })*/;
