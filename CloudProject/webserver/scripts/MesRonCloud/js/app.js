'use strict';

/* App Module */

angular.module('cloud', ['phonecatServices','phonecatServicesImages','vmcreated','vmdestroyed','vminstance','volumecreate','volumes','volumesdestroy','attach','detach']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
	when('/volume', {templateUrl: 'partials/storage.html',   controller: Storage}).
	when('/createstorage', {templateUrl: 'partials/createstorage.html',   controller: CreateStorage}).
	when('/destroystorage', {templateUrl: 'partials/destroystorage.html',   controller: DestroyStorage}).
	when('/managestorage', {templateUrl: 'partials/managestorage.html',   controller: ManageStorage}).
	when('/attach', {templateUrl: 'partials/attach.html',   controller: Attach}).
	when('/detach', {templateUrl: 'partials/detach.html',   controller: Detach}).
	when('/format', {templateUrl: 'partials/format.html',   controller: Format}).
	when('/vms', {templateUrl: 'partials/compute-engine.html',   controller: ComputeEngineCtrl}).
	when('/create', {templateUrl: 'partials/createvm.html',   controller: Create}).
when('/destroy', {templateUrl: 'partials/destroyvm.html',   controller: Destroy}).
when('/manage', {templateUrl: 'partials/managevm.html',   controller: Manage}).
	when('/homepage', {templateUrl: 'partials/homepage.html',   controller: HomePageCtrl}).
    //when('/created', {templateUrl: 'partials/vmcreated.html',   controller: Created}).  
      
      otherwise({redirectTo: '/homepage'});
}]);
