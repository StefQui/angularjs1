/**
 * INSPINIA - Responsive Admin Theme
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written stat for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/index/main");

    $stateProvider

        .state('index', {
            abstract: true,
            url: "/index",
            templateUrl: "views/common/content.html",
        })
        .state('index.main', {
            url: "/main",
            templateUrl: "views/main.html",
            data: { pageTitle: 'Example view' }
        })
        .state('index.minor', {
            url: "/minor",
            templateUrl: "views/minor.html",
            data: { pageTitle: 'Example view' }
        })
        .state('index.stef', {
            url: "/stef",
            templateUrl: "views/stef.html",
            data: { pageTitle: 'Stef view' }
        })
        .state('index.crud', { 
            url: "/crud",
            templateUrl: "views/crud.html",
            data: { pageTitle: 'Crud view' }
        })
        .state('index.login', { 
            url: "/login",
            templateUrl: "views/users/login.html",
            data: { pageTitle: 'Login view' }
        })
        .state('index.register', { 
            url: "/register",
            controller: 'RegisterCtrl',
            templateUrl: "views/users/register.html",
            data: { pageTitle: 'Register view' },
            controllerAs: 'vm'

        })
        
//        $httpProvider.defaults.useXDomain = true;
//    delete $httpProvider.defaults.headers.common['X-Requested-With'];

}

angular
    .module('inspinia')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });


// enable CORS

angular
.module('inspinia').config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}
]);