(function () {
    'use strict';

    angular
        .module('incomeBookTableModule')
        .controller('createUpdateRecordModal', Controller);

    Controller.$inject = ['$uibModalInstance', 'record'];

    function Controller($uibModalInstance, record) {
        const vm = this;
        vm.record = record; // get automaticaly from controller who call this modal window instance
        vm.save = save;
        vm.close = close;

        function save() {
            $uibModalInstance.close(vm.record);
        }
        function close() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();