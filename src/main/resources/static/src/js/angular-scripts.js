const angular = require('angular');

const app = angular.module("app", [require('angular-utils-pagination'), require('angular-route')]);

app.config(function($httpProvider) {
  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});

app.config(function($routeProvider) {
  $routeProvider.when('/', {
    controller: 'homeController',
    templateUrl: '/dist/views/home.html'
  }).when('/dashboard', {
    controller: 'dashboardController',
    templateUrl: '/dist/views/dashboard.html'
  }).when('/profile', {
    controller: 'profileController',
    templateUrl: '/dist/views/profile.html'
  }).when('/admin', {
    controller: 'adminController',
    templateUrl: '/dist/views/admin.html'
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


app.controller("homeController", function($scope, $location, sessionService) {

  $scope.login = () => {
    window.location.replace("/login");
  }
});

app.controller("navController", function($scope, $http, $location, sessionService) {

  angular.element(document).ready(function () {
    jQuery(".dropdown-toggle").dropdown();
  });

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
    self.authenticated = false;
  });

  $scope.logout = function() {
    $http.post('/logout', {}).success(function() {
      sessionService.resetSession();
      $scope.session = {};
      $location.path("/");
    }).error(function(data) {
      self.authenticated = false;
      $location.path("/");
    });
  };

});

const initializeSelect = () => {
  jQuery('.dropdown-product').selectize({
    create: true,
    sortField: 'text'
  });
}

const getProductInformation = ($http, $scope, id) => {
  $http.get(`/items/${id}/count`).success(function(data) {
    $scope.metaInfo.visible = true;
    $scope.metaInfo.count = data;
    $scope.message = "";

    if (data > 0) {
      $scope.requestButton = true;
      $scope.requestId = id;
      $scope.requestText = "Request Item";
    }
  }).error(function() {
    self.authenticated = false;
  });
}

app.controller("dashboardController", function($scope, $http, $location, $timeout, sessionService) {

  $http.get("/products").success(function(data) {
    console.log(data);
    $scope.products = data;
    $scope.metaInfo = {
      visible: false,
      count: 0
    };

    $timeout(initializeSelect, 30);

  }).error(function() {
    self.authenticated = false;
    $location.path("/");
  });

  jQuery("#product").on('change', function() {
    let id = jQuery("#product").val();
    getProductInformation($http, $scope, id);
  });

  $scope.requestItem = (productId) => {
    $scope.session = sessionService.getSession();

    let userId = $scope.session.id;

    $http.post('/requests', {
      userId: userId,
      productId: productId
    }).success(function() {
      $scope.message = "Item requested";
    }).error(function(data) {
      $scope.message = "Error requesting the item";
    });

  }

});

app.controller("adminController", function($http, $scope, $timeout, sessionService) {

  $http.get("/requests").success(function(data) {
    $scope.data = data;
    
  }).error(function() {
    console.log("Error");
  });

  $scope.populateModal = (id) => {
    $http.get(`/requests/${id}`).success(function(data) {
      $scope.modalData = data;
    }).error(function() {
      console.log("Error");
    });
  }

  jQuery('#reply').on('shown.bs.modal', function (e) {

    $scope.updateRequest = (id) => {
      let reply = jQuery(e.currentTarget).find('textarea[name="reply"]').val();

      $http.patch(`/requests/${id}`, {
        reply: reply
      }).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#reply').modal('hide');
        $scope.requestMessageShow = true;
        $scope.requestMessage = "Reply sent";
        $timeout(function(){
          $scope.requestMessageShow = false;
        }, 5000);
      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#reply').modal('hide');
        $scope.requestMessageShow = true;
        $scope.requestMessage = "An error occured";
        $timeout(function(){
          $scope.requestMessageShow = false;
        }, 5000);
        console.log("Error");
      });
    }

  });

});

app.controller("profileController", function($scope, $location, sessionService) {
  console.log("profile called");
  $scope.session = sessionService.getSession();
});
