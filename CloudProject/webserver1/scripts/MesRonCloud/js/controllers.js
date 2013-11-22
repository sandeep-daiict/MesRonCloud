'use strict';
var scopes ;
/* Controllers */

function PhoneListCtrl($scope, request) 
{
  request.query();
scopes = $scope;
	
}

//PhoneListCtrl.$inject = ['$scope', 'Phone'];
 function JSON_CALLBACK( returned_data )
{
	scopes.vms = returned_data.types;
}


function PhoneDetailCtrl($scope, $routeParams, Phone) {
  $scope.phone = Phone.get({phoneId: $routeParams.phoneId}, function(phone) {
    $scope.mainImageUrl = phone.images[0];
  });

  $scope.setImage = function(imageUrl) {
    $scope.mainImageUrl = imageUrl;
  }
}

//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
