(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('HipsterDialogController', HipsterDialogController);

    HipsterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Hipster'];

    function HipsterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Hipster) {
        var vm = this;

        vm.hipster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hipster.id !== null) {
                Hipster.update(vm.hipster, onSaveSuccess, onSaveError);
            } else {
                Hipster.save(vm.hipster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:hipsterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setAvatar = function ($file, hipster) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        hipster.avatar = base64Data;
                        hipster.avatarContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.birthday = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
