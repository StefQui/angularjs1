/**
 * INSPINIA - Responsive Admin Theme
 *
 */

/**
 * MainCtrl - controller
 */

    console.log('start controllers');

function MainCtrl() {

	this.userName = 'Example user';
	this.helloText = 'Welcome in SeedProject';
	this.descriptionText = 'It is an application skeleton for a typical AngularJS web app. You can use it to quickly bootstrap your angular webapp projects and dev environment for these projects.';

};

function StefCtrl($scope,restoService,ENV,$http) {

	this.stefTitle = 'Stef from ctrl'+ENV.name+" "+ENV.restUrl;
	$scope.reponse = "hello1";
	$scope.reponse2 = "hello2";

	this.testServer = function() {
        toastr.options = {
      		  "closeButton": false,
      		  "debug": false,
      		  "newestOnTop": false,
      		  "progressBar": false,
      		  "positionClass": "toast-top-left",
      		  "preventDuplicates": false,
      		  "onclick": null,
      		  "showDuration": "300",
      		  "hideDuration": "1000",
      		  "timeOut": "5000",
      		  "extendedTimeOut": "1000",
      		  "showEasing": "swing",
      		  "hideEasing": "linear",
      		  "showMethod": "fadeIn",
      		  "hideMethod": "fadeOut"
      		}
		toastr["success"]("Demande envoyée");
//        $toaster.success({ body:"Hi, welcome to Inspinia. This is example of Toastr notification box."});
//        alert("toaster"+toatser);
		$http.get(ENV.restUrl+'rest/res/hello').then(function(data, status, headers, config) {
            result = data;
           toastr.options = {
            		  "closeButton": false,
            		  "debug": false,
            		  "newestOnTop": false,
            		  "progressBar": false,
            		  "positionClass": "toast-top-left",
            		  "preventDuplicates": false,
            		  "onclick": null,
            		  "showDuration": "300",
            		  "hideDuration": "1000",
            		  "timeOut": "5000",
            		  "extendedTimeOut": "1000",
            		  "showEasing": "swing",
            		  "hideEasing": "linear",
            		  "showMethod": "fadeIn",
            		  "hideMethod": "fadeOut"
            		}
            toastr["warning"]("Données bien reçue "+result.data.name);
           $scope.reponse = JSON.stringify(result);
           $scope.reponse2 = result.data.name;
//            alert("toaster");
//            this.reponse = JSON.stringify(data);
            return true;
        },function(data, status, headers, config) {
            alert("pb");
            return true;
        });
	} 

};

function RegisterCtrl($scope,authService,UserService, $location, $rootScope, FlashService) {

// this.stefTitle = 'Stef from ctrl';
	$scope.send = function() {
		authService.sendRegistration($scope.signUser);
	}
	$scope.signUser = null;
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user)
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                    }
                });
        }
    

	
	
};

// app.factory('personService', function ($resource) {
// return $resource('resources/persons/:id');
// });

function RestoListCtrl($scope, $rootScope, restoService) {
	// Initialize required information: sorting, the first page to show and the
	// grid options.
	$scope.sortInfo = {
		fields : [ 'restoId' ],
		directions : [ 'asc' ]
	};
	$scope.restos = {
		currentPage : 1
	};

	$scope.gridOptions = {
		data : 'restos.list',
		useExternalSorting : true,
		sortInfo : $scope.sortInfo,

		columnDefs : [
				{
					field : 'restoId',
					displayName : 'Id'
				},
				{
					field : 'name',
					displayName : 'Name'
				},
				{
					field : 'description',
					displayName : 'Description'
				},
				{
					field : '',
					width : 30,
					cellTemplate : '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>'
// cellTemplate : '<span class="glyphicon glyphicon-remove remove"
// ng-click="alert('coucou')"></span>'
				} ],

		multiSelect : false,
		selectedItems : [],
		// Broadcasts an event when a row is selected, to signal the form that
		// it needs to load the row data.
		afterSelectionChange : function(rowItem) {
			if (rowItem.selected) {
				$rootScope.$broadcast('restoSelected',
						$scope.gridOptions.selectedItems[0].restoId);
			}
		}
	};

	// Refresh the grid, calling the appropriate rest method.
	$scope.refreshGrid = function() {
		var listRestosArgs = {
			page : $scope.restos.currentPage,
			sortFields : $scope.sortInfo.fields[0],
			sortDirections : $scope.sortInfo.directions[0]
		};

		restoService.get(listRestosArgs, function(data) {
			$scope.restos = data;
		})
	};

	// Broadcast an event when an element in the grid is deleted. No real
	// deletion is perfomed at this point.
	$scope.deleteRow = function(row) {
		alert("coucou"+row.entity.restoId);
		$rootScope.$broadcast('deleteResto', row.entity.restoId);
	};

	// Watch the sortInfo variable. If changes are detected than we need to
	// refresh the grid.
	// This also works for the first page access, since we assign the initial
	// sorting in the initialize section.
	$scope.$watch('sortInfo.fields[0]', function() {
		$scope.refreshGrid();
	}, true);

	// Do something when the grid is sorted.
	// The grid throws the ngGridEventSorted that gets picked up here and
	// assigns the sortInfo to the scope.
	// This will allow to watch the sortInfo in the scope for changed and
	// refresh the grid.
	$scope.$on('ngGridEventSorted', function(event, sortInfo) {
		$scope.sortInfo = sortInfo;
	});

	// Picks the event broadcasted when a person is saved or deleted to refresh
	// the grid elements with the most
	// updated information.
	$scope.$on('refreshGrid', function() {
		$scope.refreshGrid();
	});

	// Picks the event broadcasted when the form is cleared to also clear the
	// grid selection.
	$scope.$on('clear', function() {
		$scope.gridOptions.selectAll(false);
	});
};


function RestoFormCtrl($scope, $rootScope, restoService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or
	// when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.resto = null;
        // For some reason, I was unable to clear field values with type 'url'
		// if the value is invalid.
        // This is a workaroud. Needs proper investigation.
// document.getElementById('imageUrl').value = null;
        // Resets the form validation state.
        $scope.restoForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a person.
    $scope.updateResto = function () {
    	restoService.save($scope.resto).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('restoSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Picks up the event broadcasted when the person is selected from the grid
	// and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('restoSelected', function (event, restoId) {
        $scope.resto = restoService.get({id: restoId});
    });

    // Picks us the event broadcasted when the person is deleted from the grid
	// and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteResto', function (event, restoId) {
// console.log("delete")
    	restoService.delete({id: restoId}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('restoDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
};

function AlertMessagesCtrl($scope) {
	$scope.$on('restoSaved', function() {
		$scope.alerts = [ {
			type : 'success',
			msg : 'Record saved successfully!'
		} ];
	});

	this.testAlert='coucoucou';
	// Picks up the event to display a deleted message.
	$scope.$on('restoDeleted', function() {
		$scope.alerts = [ {
			type : 'success',
			msg : 'Record deleted successfully!'
		} ];
	});

	// Picks up the event to display a server error message.
	$scope.$on('error', function() {
		$scope.alerts = [ {
			type : 'danger',
			msg : 'There was a problem in the server!'
		} ];
	});

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	};
}

function CrudCtrl($scope) {

	this.crudTitle = 'Crud from ctrl';

};

// angular.module('inspinia',['ngResource']) //
angular.module('inspinia') //
.controller('RegisterCtrl', RegisterCtrl) //
.controller('MainCtrl', MainCtrl) //
.controller('StefCtrl', StefCtrl) //
.controller('CrudCtrl', CrudCtrl) //
.controller('AlertMessagesCtrl', AlertMessagesCtrl) //
.controller('RestoListCtrl', RestoListCtrl) //
.controller('RestoFormCtrl', RestoFormCtrl) //
.controller('chartJsCtrl', chartJsCtrl) //
.factory('restoService',['$resource','ENV', function($resource,ENV) {
// return $resource('resources/restos/:id');
	return $resource(ENV.restUrl+'resources/restos/:id');
}])
.factory('userService', function ($http) {
    var securities = {};
    $http.get('../service/UserService.js').
                                  success(function (data, status, headers, config) {
                                      securities = data;
                                  }).
                                  error(function (data, status, headers, config) {
                                      // log error
                                  });

    var factory = {};
    factory.getSecurities = function () {
        return securities;
    }
    return factory;
})
.factory('authService',['$http', function($http) {
	var factory = {};

    factory.sendRegistration = function(user) {
    	return $http.post('rest/auth/sendRegistration', user).then(function(data, status, headers, config) {
            result = data;
            alert("ok");
            return true;
        },function(data, status, headers, config) {
            alert("pb");
            return true;
        });
// $http({
// url: 'auth/sendRegistration',
// method: 'GET'
// }).success(function(data, status, headers, config) {
// result = data;
// return true;
// });
    };
    return factory;

}]);
console.log('start controllers2');


