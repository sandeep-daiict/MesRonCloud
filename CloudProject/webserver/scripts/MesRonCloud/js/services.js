'use strict';
var ip="10.3.3.116";
/* Services */

angular.module('phonecatServices', ['ngResource']).
    factory('request', function($resource){
  return $resource('http://'+ip+'\\:8083/vm/types/callback=JSON_CALLBACK', {}, {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('phonecatServicesImages', ['ngResource']).
    factory('requestimage', function($resource){
  return $resource('http://'+ip+'\\:8083/image/list/callback=JSON_CALLBACK', {}, {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('vmcreated', ['ngResource']).
    factory('createnewvm', function($resource){
  return $resource('http://'+ip+'\\:8083/vm/create/?name=:name&instance_type=:instance_type&image_id=:image_id&desc=:desc&callback=JSON_CALLBACK_CREATE', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('vmdestroyed', ['ngResource']).
    factory('destroyvm', function($resource){
  return $resource('http://'+ip+'\\:8083/vm/destroy/?vmid=:vmid&callback=JSON_CALLBACK_DESTROY', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('vminstance', ['ngResource']).
    factory('instancevm', function($resource){
  return $resource('http://'+ip+'\\:8083/vm/instances/?callback=JSONP_CALLBACK_INSTANCES', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('volumecreate', ['ngResource']).
    factory('volumecreate', function($resource){
  return $resource('http://'+ip+'\\:8083/volume/create/?name=:name&size=:size&callback=JSONP_CALLBACK_CREATE_VOLUME', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('volumes', ['ngResource']).
    factory('volumesinfo', function($resource){
  return $resource('http://'+ip+'\\:8083/volume/info/?callback=JSONP_CALLBACK_VOLUMES', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('volumesdestroy', ['ngResource']).
    factory('volumesdestroy', function($resource){
  return $resource('http://'+ip+'\\:8083/volume/destroy/?volumeid=:volumeid&callback=JSONP_CALLBACK_VOLUMES', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});


angular.module('attach', ['ngResource']).
    factory('attach', function($resource){
  return $resource('http://'+ip+'\\:8083/volume/attach/?vmid=:vmid&volumeid=:volumeid&callback=JSONP_CALLBACK_ATTACH_VOLUME', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

angular.module('detach', ['ngResource']).
    factory('detach', function($resource){
  return $resource('http://'+ip+'\\:8083/volume/detach/?volumeid=:volumeid&callback=JSONP_CALLBACK_DETACH_VOLUME', {}, 
  {
    query: {method:'JSONP' ,isArray:true}
  });
});

