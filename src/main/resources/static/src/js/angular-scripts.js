const angular = require('angular');

angular.module("app", [])
  .config( function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
  })
  .controller("home", function($http, $location) {

  const self = this;

  $http.get("/user").success(function(data) {
    console.log('Logging in');
    self.user = data.userAuthentication.details.name;
    self.authenticated = true;
  }).error(function() {
    self.user = "N/A";
    self.authenticated = false;
  });

  self.logout = function() {
    $http.post('/logout', {}).success(function() {
      self.authenticated = false;
      $location.path("/");
    }).error(function(data) {
      console.log("Logout failed")
      self.authenticated = false;
    });
  };

});
