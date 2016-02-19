/**
 * INSPINIA - Responsive Admin Theme
 * 
 * Inspinia theme use AngularUI Router to manage routing and views Each view are
 * defined as state. Initial there are written stat for all view in theme.
 * 
 */
function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
	$urlRouterProvider.otherwise("/index/main");

	$ocLazyLoadProvider.config({
		// Set to true if you want to see what and when is dynamically loaded
		debug : true
	});

	$stateProvider

			.state('index', {
				abstract : true,
				url : "/index",
				templateUrl : "views/common/content.html",
			})
			.state('index.main', {
				url : "/main",
				templateUrl : "views/main.html",
				data : {
					pageTitle : 'Example view'
				}
			})
			.state('index.minor', {
				url : "/minor",
				templateUrl : "views/minor.html",
				data : {
					pageTitle : 'Example view'
				}
			})
			.state(
					'index.stef',
					{
						url : "/stef",
						templateUrl : "views/stef.html",
						data : {
							pageTitle : 'Stef view'
						},
						resolve : {
							loadMyService : [
									'$ocLazyLoad',
									'$injector',
									function($ocLazyLoad, $injector) {
										// alert("$ocLazyLoad=="+$ocLazyLoad+"
										// "+$injector);
										return $ocLazyLoad
												.load([ {
													insertBefore : '#loadBefore',
													name : 'toaster',
													files : [
															'js/plugins/toastr/toastr.min.js',
															'css/plugins/toastr/toastr.min.css' ]

												} ])

										;

									} ]
						}
					})
			.state('index.crud', {
				url : "/crud",
				templateUrl : "views/crud.html",
				data : {
					pageTitle : 'Crud view'
				}
			})
			.state('index.login', {
				url : "/login",
				templateUrl : "views/users/login.html",
				data : {
					pageTitle : 'Login view'
				}
			})
			.state(
					'index.register',
					{
						url : "/register",
						// controller: 'RegisterCtrl',
						templateUrl : "views/users/register.html",
						data : {
							pageTitle : 'Register view'
						},
						// controllerAs: 'vm',
						resolve : {
							loadMyService : [
									'$ocLazyLoad',
									function($ocLazyLoad, $injector) {
										return $ocLazyLoad
												.load([ {
													insertBefore : '#loadBefore',
													name : 'toaster',
													files : [
															'js/plugins/toastr/toastr.min.js',
															'css/plugins/toastr/toastr.min.css' ]
												} ]);

									} ]
						}

					})
			.state(
					'index.chartjs',
					{
						url : "/chartjs",
						// controller: 'RegisterCtrl',
						templateUrl : "views/chart.html",
						data : {
							pageTitle : 'Chart view'
						},
						// controllerAs: 'vm',
						resolve : {
							loadMyService : [
									'$ocLazyLoad',
									function($ocLazyLoad, $injector) {
										return $ocLazyLoad.load([
										                         {
										                             files: ['js/plugins/chartJs/Chart.min.js']
										                         },
										                         {
										                             name: 'angles',
										                             files: ['js/plugins/chartJs/angles.js']
										                         }
										                     ]);

									} ]
						}

					})

	// $httpProvider.defaults.useXDomain = true;
	// delete $httpProvider.defaults.headers.common['X-Requested-With'];

}

angular.module('inspinia').config(config).run(function($rootScope, $state) {
	$rootScope.$state = $state;
});

// enable CORS

angular.module('inspinia').config([ '$httpProvider', function($httpProvider) {
	$httpProvider.defaults.useXDomain = true;
	delete $httpProvider.defaults.headers.common['X-Requested-With'];
} ]);