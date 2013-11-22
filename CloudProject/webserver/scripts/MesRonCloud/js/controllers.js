'use strict';
var scopes ;
var createnew;
var destroyvmnew;
var volumecreateservice;
var volumesdestroynew;
var attachnew;
var detachnew;
/* Controllers */



function Create($scope, request,requestimage,createnewvm) 
{	
		scopes = $scope;
		request.query();
		
		requestimage.query();
		createnew=createnewvm;
}




//PhoneListCtrl.$inject = ['$scope', 'Phone'];
 function JSON_CALLBACK( returned_data )
{
	scopes.vms = returned_data.types;
	
}

function JSON_CALLBACK_IMAGES( returned_data )
{
	scopes.images = returned_data.images;
	
}

function JSON_CALLBACK_CREATE(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.vmid!=null)
	{
		document.getElementById("temp").innerHTML="<h4>Instance sucessfully launched with id:"+scopes.result.vmid+"</h4>";
	}
	else
	{
		document.getElementById("temp").innerHTML="<h4>Instance launching failed</h4>";
	}
	//document.getElementById("page1").style.display = "";
	
}

function CreatedCtrl()
{
		//alert(document.getElementById("image").value);
	createnew.query({name:document.getElementById("name").value,instance_type:document.getElementById("instance").value,image_id:document.getElementById("image").value,desc:document.getElementById("desc").value});
	
}
function DestroyedCtrl()
{
	var id =document.getElementById("instance_destroy").value;
	//alert(document.getElementById("instance_destroy").value);
	destroyvmnew.query({vmid:id});
}

function JSONP_CALLBACK_DESTROY(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.status!=0)
	{
		document.getElementById("page1").innerHTML="<h4>Instance sucessfully deleted</h4>";
	}
	else
	{
		document.getElementById("page1").innerHTML="<h4>Instance Destroying failed</h4>";
	}
}
function Destroy($scope,destroyvm,instancevm) 
{
	scopes=$scope;
	destroyvmnew=destroyvm;
	instancevm.query();
}
function JSONP_CALLBACK_INSTANCES(data) 
{
	scopes.vms=data.instances;
}
function Storage($scope, request) 
{

	
}


function Manage($scope, instancevm) 
{
	scopes=$scope;
	instancevm.query();
}

/*
 * 
 * 
 * volume controllers
 * 
 * */


function CreateStorage($scope, volumecreate) 
{
	scopes=$scope;
	volumecreateservice=volumecreate;
}
function volumecreated()
{
	volumecreateservice.query({name:document.getElementById("name").value,size:document.getElementById("size").value});
}
function JSONP_CALLBACK_CREATE_VOLUME(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.volumeid!=null)
	{
		document.getElementById("page1").innerHTML="<h4>Volume sucessfully Created with id:"+scopes.result.volumeid+"</h4>";
	}
	else
	{
		document.getElementById("page1").innerHTML="<h4>Volume Creation failed</h4>";
	}
	//document.getElementById("page1").style.display = "";
	
}
function ManageStorage($scope, volumesinfo) 
{
	scopes=$scope;
	volumesinfo.query();
}
function JSONP_CALLBACK_VOLUMES(data)
{
	scopes.volumes=data.volumes;
}
function DestroyStorage($scope, volumesinfo,volumesdestroy) 
{
		scopes=$scope;	
		volumesdestroynew=volumesdestroy;
		volumesinfo.query();
}

function volumeDestroyedCtrl()
{
		var volume=document.getElementById("volume_destroy");
		
		//var vmid=volume.className;
		//alert(vmid+" value: "+volume.value);
		//if(vmid!=-1)
			volumesdestroynew.query({volumeid:volume.value});
		/*else
		{
			alert("Can not destroy volume :"+volume.value + " already attached to:"+vmid);
		}*/
}

function JSONP_CALLBACK_DESTROY_VOLUME(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.status!=0)
	{
		document.getElementById("page1").innerHTML="<h4>Volume sucessfully destroyed</h4>";
	}
	else
	{
		document.getElementById("page1").innerHTML="<h4>Volume Destroy failed</h4>";
	}
	//document.getElementById("page1").style.display = "";
	
}

function Attach($scope, instancevm,volumesinfo,attach) 
{
	scopes=$scope;
	instancevm.query();
	volumesinfo.query();
	attachnew=attach;
}
function AttachedCtrl()
{
	var volume=document.getElementById("volume_destroy");
	var id =document.getElementById("instance_destroy").value;
	attachnew.query({vmid:id,volumeid:volume.value});
}

function JSONP_CALLBACK_ATTACH_VOLUME(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.status!=0)
	{
		document.getElementById("page1").innerHTML="<h4>Volume sucessfully attached</h4>";
	}
	else
	{
		document.getElementById("page1").innerHTML="<h4>Volume attach failed</h4>";
	}
}
function Detach($scope,volumesinfo, detach) 
{
	scopes=$scope;
	volumesinfo.query();
	detachnew=detach;
}
function volumeDetachCtrl()
{
	var volume=document.getElementById("volume_destroy");
	detachnew.query({volumeid:volume.value});
}

function JSONP_CALLBACK_DETACH_VOLUME(returned_data)
{
	scopes.result = returned_data;
	if(scopes.result.status!=0)
	{
		document.getElementById("page1").innerHTML="<h4>Volume sucessfully detached</h4>";
	}
	else
	{
		document.getElementById("page1").innerHTML="<h4>Volume Detach failed</h4>";
	}
}
function Format($scope, request) 
{}





function ComputeEngineCtrl($scope, request)
{}





function HomePageCtrl($scope, request) 
{
 /* request.query();
scopes = $scope;
*/	
}
/*
function CreatedCtrl($scope,create)
{
	//scopes=$scope;
	$scope.name=document.getElementById('name').value;
	$scope.desc=document.getElementById('desc').value;
	$scope.instance_type=document.getElementById('instance').value;
	$scope.image_id=document.getElementById('image').value;
	createnewvm.query;
}*/
//PhoneDetailCtrl.$inject = ['$scope', '$routeParams', 'Phone'];
