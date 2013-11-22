'use strict';

/* App Module */

angular.module('cloud', ['phonecatServices']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/vms', {templateUrl: 'partials/compute-engine.html',   controller: PhoneListCtrl}).
      when('/vms/:phoneId', {templateUrl: 'partials/phone-detail.html', controller: PhoneDetailCtrl}).
      otherwise({redirectTo: '/'});
}]);
