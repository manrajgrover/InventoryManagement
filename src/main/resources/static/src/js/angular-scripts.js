"use strict";

const angular = require('angular');

const app = angular.module("app", [require('angular-utils-pagination'), require('angular-route')]);

app.config(function($httpProvider) {
  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});

app.config(function($routeProvider) {
  $routeProvider.when('/', {
    controller: 'homeController',
    templateUrl: 'dist/views/home.html'
  }).when('/dashboard', {
    controller: 'dashboardController',
    templateUrl: 'dist/views/dashboard.html'
  }).when('/profile', {
    controller: 'profileController',
    templateUrl: 'dist/views/profile.html'
  }).when('/admin', {
    controller: 'adminController',
    templateUrl: 'dist/views/admin.html'
  }).when('/selectRole', {
    controller: 'selectRoleController',
    templateUrl: 'dist/views/selectRole.html'
  }).when('/incoming/requests', {
    controller: 'incomingRequestsController',
    templateUrl: 'dist/views/incomingRequests.html'
  }).when('/products', {
    controller: 'productController',
    templateUrl: 'dist/views/product.html'
  }).when('/items', {
    controller: 'itemController',
    templateUrl: 'dist/views/item.html'
  }).when('/returns', {
    controller: 'returnsController',
    templateUrl: 'dist/views/returns.html'
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
      if ( next.templateUrl !== "dist/views/home.html" ) {
        $location.path( "/" );
      }
    } else if ((!session.hasOwnProperty('admin') || session.admin === false) && (next.templateUrl === "dist/views/admin.html" || next.templateUrl === "dist/views/selectRole.html")) {
      $location.path( "/dashboard" );
    }
  });

});


app.controller("homeController", function($scope) {

  $scope.login = () => {
    window.location.replace("login");
  }

});

app.controller("selectRoleController", function($scope, $location, sessionService) {
  
  $scope.roleSelected = (role) => {
    if(role === "admin") {
      $location.path("/admin");
    }
    else {
      let session = sessionService.getSession();
      session.admin = false;
      sessionService.setSession(session);
      $location.path("/dashboard");
    }
  }

});

app.controller("navController", function($scope, $http, $location, sessionService) {

  angular.element(document).ready(function () {
    jQuery(".dropdown-toggle").dropdown();
  });

  $http.get("userLogin").success(function(data) {
    console.log(data);

    let session = {
      name: data.name,
      email: data.email,
      picture: data.picture,
      id: data.id,
      admin: data.admin,
      authenticated: true
    };

    sessionService.setSession(session);
    console.log(session);
    $scope.session = session;
    if (data.admin) {
      $location.path("/selectRole");
    } else {
      $location.path("/dashboard");
    }
    

  }).error(function() {
    self.authenticated = false;
  });

  $scope.logout = function() {
    $http.post('logout', {}).success(function() {
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
  if(id === "") {
    return;
  }

  $http.get(`items/${id}/count`).success(function(data) {
    $scope.metaInfo.visible = true;
    $scope.metaInfo.count = data;
    $scope.message = "";

    if (data > 0) {
      $scope.requestButton = true;
      $scope.requestId = id;
    }
  }).error(function() {
    self.authenticated = false;
  });
}

app.controller("dashboardController", function($scope, $http, $location, $timeout, sessionService) {

  $http.get("products").success(function(data) {
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

    $http.post('requests', {
      userId: userId,
      productId: productId
    }).success(function() {
      $scope.message = "Item requested";
    }).error(function(data) {
      $scope.message = "Error requesting the item";
    });

  }

});

app.controller("adminController", function($scope, $http, $location, $timeout, sessionService) {

  $http.get("products").success(function(data) {
    console.log(data);
    $scope.products = data;
    $scope.metaInfo = {
      visible: false,
      count: 0
    };

    $timeout(() => {
      jQuery('.dropdown-product').selectize({
        create: true,
        sortField: 'text'
      });
    }, 30);

  }).error(function() {
    self.authenticated = false;
    $location.path("/");
  });

  $http.get("users").success(function(data) {
    console.log(data);
    $scope.users = data;

    $timeout(() => {
      jQuery('.dropdown-user').selectize({
        create: true,
        sortField: 'text'
      });
    }, 30);

  }).error(function() {
    self.authenticated = false;
    $location.path("/");
  });

  $scope.issueItem = () => {
    let productId = jQuery("#product").val();
    let userId = jQuery("#user").val();
    let productTag = jQuery("#productTag").val();

    $http.post('history', {
      userId: userId,
      productId: productId,
      productTag: productTag
    }).success(function(data) {
      if(data.availability) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
      } else {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
      }
      console.log(data);
      $scope.requestMessageShow = true;
      $scope.id = data.id;
      $scope.requestMessage = `${data.message}!`;
      if (data.availability) {
        $scope.requestMessage = `${data.message}! Your issue ticket number is ${data.id}`
      }
      $timeout(function(){
        $scope.requestMessageShow = false;
      }, 15000);
    }).error(function(data) {
      console.log("Error");
      $scope.message = "Error requesting the item";
    });

  }

});

app.controller("returnsController", function($scope, $http, $location, $timeout, sessionService) {

  $scope.returnItem = () => {
    let issueNumber = jQuery("#issueNumber").val();
    let productTag = jQuery("#productTag").val();

    if(issueNumber === "") {
      return;
    }

    $http.patch(`history/${issueNumber}`, {
      productTag: productTag
    }).success(function(data) {
      console.log(data);
      if(data.availability) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
      } else {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
      }
      $scope.requestMessageShow = true;
      $scope.requestMessage = data.message;
      $timeout(function(){
        $scope.requestMessageShow = false;
      }, 5000);
    }).error(function(data) {
      console.log("Error");
      $scope.message = "Error requesting the item";
    });

  }

});

app.controller("incomingRequestsController", function($http, $scope, $timeout, sessionService) {

  $http.get("requests").success(function(data) {
    $scope.data = data;
    
  }).error(function() {
    console.log("Error");
  });

  $scope.populateModal = (id) => {
    $http.get(`requests/${id}`).success(function(data) {
      $scope.modalData = data;
    }).error(function() {
      console.log("Error");
    });
  }

  jQuery('#reply').on('shown.bs.modal', function (e) {

    $scope.updateRequest = (id) => {
      let reply = jQuery(e.currentTarget).find('textarea[name="reply"]').val();

      $http.patch(`requests/${id}`, {
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

app.controller("productController", function($http, $scope, $timeout, $location, $route, sessionService) {

  $http.get("products").success(function(data) {
    console.log(data);
    $scope.data = data;
  }).error(function() {
    console.log("Error");
  });

  $scope.populateEdit = (id) => {
    $http.get(`products/${id}`).success(function(data) {
      jQuery("#editProductCompany").val(data.company);
      jQuery("#editProductVersion").val(data.version);
      jQuery("#editProductName").val(data.name);      
      $scope.edit = data;
    }).error(function() {
      console.log("Error");
    });
  }

  $scope.populateDelete = (id) => {
    console.log(id);
    $http.get(`products/${id}`).success(function(data) { 
      console.log(data); 
      $scope.delete = data;
    }).error(function() {
      console.log("Error");
    });
  }

  jQuery('#add').on('shown.bs.modal', function (e) {

    $scope.addProduct = () => {
      let company = jQuery(e.currentTarget).find('input[name="addProductCompany"]').val();
      let name = jQuery(e.currentTarget).find('input[name="addProductName"]').val();
      let version = jQuery(e.currentTarget).find('input[name="addProductVersion"]').val();

      console.log(company + " "+ name + " " + version);

      $http.post(`products`, {
        name: name,
        company: company,
        version: version
      }).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#add').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "Product added! Refreshing..";
        $timeout(function(){
          $scope.productMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#add').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "An error occured";
        $timeout(function(){
          $scope.productMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }

  });

  jQuery('#edit').on('shown.bs.modal', function (e) {

    $scope.updateProduct = (id) => {
      console.log(id);
      let company = jQuery(e.currentTarget).find('input[name="editProductCompany"]').val();
      let name = jQuery(e.currentTarget).find('input[name="editProductName"]').val();
      let version = jQuery(e.currentTarget).find('input[name="editProductVersion"]').val();

      console.log(company + " "+ name + " " + version);

      $http.patch(`products/${id}`, {
        name: name,
        company: company,
        version: version
      }).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#edit').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "Product updated! Refreshing..";
        $timeout(function(){
          $scope.productMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#edit').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "An error occured";
        $timeout(function(){
          $scope.productMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }

  });

  jQuery('#delete').on('shown.bs.modal', function (e) {

    $scope.deleteProduct = (id) => {
      console.log(id);

      $http.delete(`products/${id}`).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#delete').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "Product Deleted";
        $timeout(function(){
          $scope.productMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#delete').modal('hide');
        $scope.productMessageShow = true;
        $scope.productMessage = "An error occured";
        $timeout(function(){
          $scope.productMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }

  });

});

app.controller("itemController", function($http, $scope, $timeout, $location, $route, sessionService) {

  $http.get("items").success(function(data) {
    console.log(data);
    $scope.data = data;
  }).error(function() {
    console.log("Error");
  });

  $scope.populateAdd = () => {
    $http.get(`products`).success(function(data) {
      console.log(data);
      $scope.products = data;
    }).error(function() {
      console.log("Error");
    });
  }

  $scope.populateEdit = (id, productId) => {
    $http.get(`items/${id}`).success(function(data) {  
      $scope.edit = data;
      jQuery('#editItemTag').val(data.tag);
    }).error(function() {
      console.log("Error");
    });

    $http.get(`products`).success(function(data) {  
      $scope.products = data;
      $timeout(function(){
        console.log("Selecting");
        jQuery("#product option[value="+productId+"]").attr("selected","selected");
      }, 30);
    }).error(function() {
      console.log("Error");
    });
  }

  $scope.populateDelete = (id) => {
    console.log(id);
    $http.get(`items/${id}`).success(function(data) { 
      console.log(data); 
      $scope.delete = data;
    }).error(function() {
      console.log("Error");
    });
  }

  jQuery('#add').on('shown.bs.modal', function (e) {

    $scope.addItem = () => {
      let company = jQuery(e.currentTarget).find('select[name="addProductSelect"]').val();
      let tag = jQuery(e.currentTarget).find('input[name="addItemTag"]').val();

      console.log(company + " " + tag);

      $http.post(`items`, {
        productId: company,
        productTag: tag
      }).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#add').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "Item added! Refreshing..";
        $timeout(function(){
          $scope.itemMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#add').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "An error occured";
        $timeout(function(){
          $scope.itemMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }
  });

  jQuery('#edit').on('shown.bs.modal', function (e) {

    $scope.updateItem = (id) => {
      console.log(id);
      let company = jQuery(e.currentTarget).find('select[name="editProductSelect"]').val();
      let tag = jQuery(e.currentTarget).find('input[name="editItemTag"]').val();
      console.log(company + " " + tag);
      $http.patch(`items/${id}`, {
        productId: company,
        productTag: tag
      }).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#edit').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "Item updated! Refreshing..";
        $timeout(function(){
          $scope.itemMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#edit').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "An error occured";
        $timeout(function(){
          $scope.itemMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }

  });

  jQuery('#delete').on('shown.bs.modal', function (e) {

    $scope.deleteProduct = (id) => {
      console.log(id);

      $http.delete(`items/${id}`).success(function(data) {
        jQuery('#httpMessage').removeClass('alert-danger');
        jQuery('#httpMessage').addClass('alert-success');
        jQuery('#delete').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "Item Deleted";
        $timeout(function(){
          $scope.itemMessageShow = false;
          $route.reload();
        }, 3000);

      }).error(function() {
        jQuery('#httpMessage').removeClass('alert-success');
        jQuery('#httpMessage').addClass('alert-danger');
        jQuery('#delete').modal('hide');
        $scope.itemMessageShow = true;
        $scope.itemMessage = "An error occured";
        $timeout(function(){
          $scope.itemMessageShow = false;
        }, 3000);
        console.log("Error");
      });
    }

  });

});

app.controller("profileController", function($scope, $location, sessionService) {
  console.log("profile called");
  $scope.session = sessionService.getSession();
});
