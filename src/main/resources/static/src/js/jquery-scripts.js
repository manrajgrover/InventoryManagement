const jQuery = require('jquery');
window.jQuery = jQuery;
require('bootstrap');


jQuery(document).ready(function() {
    jQuery(".dropdown-toggle").dropdown();
});
