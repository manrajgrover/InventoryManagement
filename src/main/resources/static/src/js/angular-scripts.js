const angular = require('angular');

const app = angular.module("app", [require('angular-route')]);

app.config(function($httpProvider) {
  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});

app.config(function($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: '/dist/views/home.html'
  }).when('/dashboard', {
    controller: 'dashboardController',
    templateUrl: '/dist/views/dashboard.html'
  }).when('/profile', {
    controller: 'profileController',
    templateUrl: '/dist/views/profile.html'
  }).otherwise({
    redirectTo: '/'
  });
});

app.service('sessionService', function() {
  let session = {};

  return {
    resetSession: function() {
      session = {};
    },
    getSession : function() {
      return session;
    },
    setSession : function(_session) {
      session = _session;
    }
  }
});

app.run(function($rootScope, $location, sessionService){

  $rootScope.$on( "$routeChangeStart", function(event, next, current) {
    let session = sessionService.getSession();
    if (!session.hasOwnProperty('authenticated') || session.authenticated === false) {
      if ( next.templateUrl !== "/dist/views/home.html" ) {
        $location.path( "/" );
      }
    }
  });

});

app.controller("navController", function($scope, $http, $location, sessionService) {

  $http.get("/userLogin").success(function(data) {
    console.log(data);

    let session = {
      name: data.name,
      email: data.email,
      picture: data.picture,
      id: data.id,
      authenticated: true
    };

    sessionService.setSession(session);
    console.log(session);
    $scope.session = session;
    $location.path("/dashboard");

  }).error(function() {
    self.user = "N/A";
    self.authenticated = false;
  });

  $scope.logout = function() {
    $http.post('/logout', {}).success(function() {
      sessionService.resetSession();
      $scope.session = {};
      $location.path("/");
    }).error(function(data) {
      console.log("Logout failed")
      self.authenticated = false;
    });
  };

});


app.controller("dashboardController", function($http, $location) {
});

app.controller("profileController", function($scope, $location, sessionService) {
  console.log("profile called");
  $scope.session = sessionService.getSession();
});
