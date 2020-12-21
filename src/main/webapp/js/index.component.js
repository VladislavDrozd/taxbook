(function () {
    'use strict';

    angular
        .module('appModule')
        .controller('indexController', Controller);

    Controller.$inject = ['$http', '$cookies', '$uibModal', 'APP_LINK'];

    function Controller($http, $cookies, $uibModal, APP_LINK) {
        const vm = this;
        vm.email = {
          name: '',
          email: '',
          text: '',
        };

        vm.sendSimpleEmail = sendSimpleEmail;

        function sendSimpleEmail(form) {
            if (!form.$valid) return;
            $http.post(APP_LINK + 'login?action=sendSimpleEmail&ename='+vm.email.name+'&eemail='+vm.email.email+'&etext='+vm.email.text)
                .then((response => {
                    console.log('RESPONSE:', response);
                    if (response.status === 200) {
                        alert('Email надiслано');
                    } else {
                        alert('Email не надiслано. Зателефонуйте за номером +123456789');
                    }
                }), (
                    () => {
                        alert('Email не надiслано. Зателефонуйте за номером +123456789');
                    }));
        }

    }
})();


