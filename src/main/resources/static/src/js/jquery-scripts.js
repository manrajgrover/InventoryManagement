const jQuery = require('jquery');
window.jQuery = jQuery;
require('bootstrap');
require('select2');

jQuery(document).ready(function() {
  jQuery(".dropdown-toggle").dropdown();
  
  if (jQuery("#dropdown-product").length !== 0) {
    jQuery("#dropdown-product").select2();
  }

  if (jQuery("#dropdown-version").length !== 0) {
    jQuery("#dropdown-version").select2();
  }

});
