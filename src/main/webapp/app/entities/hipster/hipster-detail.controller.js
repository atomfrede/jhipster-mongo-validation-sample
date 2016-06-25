(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('HipsterDetailController', HipsterDetailController);

    HipsterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Hipster'];

    function HipsterDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Hipster) {
        var vm = this;

        vm.hipster = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterApp:hipsterUpdate', function(event, result) {
            vm.hipster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
