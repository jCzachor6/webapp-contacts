'use strict';

var ContactsController = function($scope, $http, $window) {
    $scope.contacts = {};
    $scope.single = {};

    $scope.fetchContacts = function (){
        $http.get('contact').then(function(response) {
            $scope.contacts = response.data;
        });
    };

    $scope.deleteContact = function(contact){
        $http({
            method: 'DELETE',
            url: 'contact',
            data: {
                rid: contact.rid
            },
            headers: {
                'Content-type': 'application/json;charset=utf-8'
            }
        })
            .then(function() {
                $scope.fetchContacts();
            }, function(rejection) {
            });
    };

    $scope.addContact = function (single){
        $http.post('contact', single).then(function(response) {
            $scope.single = {};
            $scope.fetchContacts();
        });
    };

    $scope.fetchContacts();
};