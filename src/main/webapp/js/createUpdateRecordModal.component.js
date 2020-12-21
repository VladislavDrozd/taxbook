(function () {
    'use strict';

    angular
        .module('appModule')
        .controller('createUpdateRecordModal', Controller);

    Controller.$inject = ['$uibModalInstance', 'record', 'action', 'clientArray'];

    function Controller($uibModalInstance, record, action, clientArray) {
        const vm = this;
        vm.record = record; // get automaticaly from controller who call this modal window instance
        vm.action = action; // get automaticaly from controller who call this modal window instance
        vm.clientArray = clientArray; // get automaticaly from controller who call this modal window instance
        vm.save = save;
        vm.close = close;

        function editRecordBeforeSending() {
            vm.record.revised = vm.record.income - vm.record.refund;
            vm.record.totalIncome = vm.record.revised + vm.record.freeReceived;
        }

        function save() {
            console.log('bef', vm.record);
            editRecordBeforeSending();
            console.log('aft', vm.record);
            $uibModalInstance.close(vm.record);
        }
        function close() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();