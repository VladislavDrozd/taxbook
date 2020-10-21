(function () {
   'use strict';

   angular
       .module('myApp1212')
       .controller('testController', Controller);

   Controller.$inject = ['$http'];

    function Controller($http) {
        const vm = this;
        vm.data = 'som';
        vm.list = [];
        vm.clickMeAdd= clickMeAdd;
        vm.clickMeRemove= clickMeRemove;

        activate();

        function activate() {
            console.log("activate begin");
            loadRecords();
            console.log("activate end");
        }

        function loadRecords() {
            $http.get('http://localhost:8080/taxbook/app/incomeBook?action=getAllRecords')
                .then(r => {
                    vm.list = r.data;
                    console.log(r)
                });
        }

        function clickMeAdd() {
            vm.list.push({recordId:1});
        }
        function clickMeRemove() {
            vm.list.shift();
        }
    }
})();

