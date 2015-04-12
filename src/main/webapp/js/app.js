
angular.module('phonecatApp', [
    'ngRoute',
    'phonecatAnimations',
    'phonecatControllers',
    'phonecatFilters',
    'ui.bootstrap'


])
    .config(function ($routeProvider) {
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
    })

    .controller( 'AppCtrl', function AppCtrl ( $scope, sessionService) {
    $scope.isLoggedIn = sessionService.isLoggedIn;
        $scope.logout = sessionService.logout;
});
