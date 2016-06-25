(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('HipsterDeleteController',HipsterDeleteController);

    HipsterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Hipster'];

    function HipsterDeleteController($uibModalInstance, entity, Hipster) {
        var vm = this;

        vm.hipster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Hipster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
