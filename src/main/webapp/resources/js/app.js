'use strict';
var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives', 'ngRoute']);

App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/contacts', {
            templateUrl: './allcontacts',
            controller: ContactsController
        })

        .otherwise({redirectTo: '/contacts'});
}]);
