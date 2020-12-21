(function () {
    'use strict';

    angular
        .module('appModule')
        .controller('createClientAddModal', Controller);

    Controller.$inject = ['$uibModalInstance', 'client','action'];

    function Controller($uibModalInstance, client, action) {
        const vm = this;
        vm.client= client;
        vm.action = action;
        vm.add = add;
        vm.close = close;



        function add() {
            console.log('bef', vm.client);
            console.log('aft', vm.client);
            $uibModalInstance.close(vm.client);
        }
        function close() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();