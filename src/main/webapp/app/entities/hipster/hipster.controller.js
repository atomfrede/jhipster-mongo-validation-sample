(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('HipsterController', HipsterController);

    HipsterController.$inject = ['$scope', '$state', 'DataUtils', 'Hipster'];

    function HipsterController ($scope, $state, DataUtils, Hipster) {
        var vm = this;
        
        vm.hipsters = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Hipster.query(function(result) {
                vm.hipsters = result;
            });
        }
    }
})();
