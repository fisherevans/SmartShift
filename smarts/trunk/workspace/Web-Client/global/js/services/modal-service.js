'use strict'

angular.module('smartsServices').factory('modalService', ['$modal', '$rootScope', '$location', 'httpService', 'utilService', 'accountsService',
    function( $modal, $rootScope, $location, httpService, utilService, accountsService){
        return {
            loginModal: function(initialErrorMessage){
                return $modal.open({
                    templateUrl: '../app/templates/modals/login-modal.html',
                    controller: 'LoginModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        initialErrorMessage: function() {
                            return initialErrorMessage == null ? '' : initialErrorMessage;
                        }
                    }
                }).result;
            },
            businessModal: function( business ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/business-modal.html',
                    controller: 'BusinessModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        businesses: function(){
                            return business;
                        }
                    }
                }).result;
            },
            addEmployeeModal: function( defaultModel ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/add-employee-modal.html',
                    controller: 'AddEmployeeModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        "defaultModel": function() { return defaultModel; }
                    }
                }).result;
            },
            editEmployeeModal: function( employee ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/edit-employee-modal.html',
                    controller: 'EditEmployeeModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        "employee": function() { return employee; }
                    }
                }).result;
            },
            deleteEmployeeModal: function( employee ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/delete-employee-modal.html',
                    controller: 'DeleteEmployeeModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        "employee": function() { return employee; }
                    }
                }).result;
            },
            filterEmployeesModal: function( filter ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/filter-employees-modal.html',
                    controller: 'FilterEmployeesModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        "filter": function() { return filter; }
                    }
                }).result;
            },
            addShiftModal: function(initial) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/add-shift-modal.html',
                    controller: 'AddShiftModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        "initial": function() { return initial; }
                    }
                }).result;
            }
        }
    }
]);