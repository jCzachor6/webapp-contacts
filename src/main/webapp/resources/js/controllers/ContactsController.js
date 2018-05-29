'use strict';

var ContactsController = function($scope, $http, $window) {
    $scope.contacts = {};

    $scope.fetchContacts = function (){
        $http.get('contact').then(function(response) {
            $scope.contacts = response.data;
        });
    };
    $scope.fetchContacts();
};