	'use strict';

/* Services */

angular.module('phonecatServices', ['ngResource']).
    factory('request', function($resource){
  return $resource('http://localhost\\:8083/vm/types/callback=JSON_CALLBACK', {}, {
    query: {method:'JSONP' ,isArray:true}
  });
});
