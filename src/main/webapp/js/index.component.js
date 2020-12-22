(function () {
    'use strict';

    angular
        .module('appModule')
        .controller('indexController', Controller);

    Controller.$inject = ['$http', '$cookies', '$uibModal', 'APP_LINK'];

    function Controller($http, $cookies, $uibModal, APP_LINK) {
        const vm = this;

        vm.isLogin = false;
        vm.currentUser = {};

        vm.email = {
          name: '',
          email: '',
          text: '',
        };

        vm.loginUser = {
          login: null,
          password: null
        };

        vm.sendSimpleEmail = sendSimpleEmail;
        vm.loginUser = loginUser;

        activate();

        function activate() {
            getLoginUser();
        }

        function getLoginUser() {
            $http.post(APP_LINK + 'app/user?action=getUserById')
                .then((response => {
                    console.log('RESPONSE:', response);
                    vm.isLogin = true;
                    vm.currentUser = response.data;
                }), (() => {}));
        }

        function loginUser(form) {
            if (!form.$valid) return;
            $http.post(APP_LINK + 'login?action=login&login='+vm.loginUser.login+'&password='+vm.loginUser.password)
                .then((response => {
                    console.log('RESPONSE:', response);
                    window.location.href = "html/incomeBook.component.html";
                }), (
                    (response) => {
                        if (response.status === 401) {
                            alert('Невiрний логiн або пароль');
                        } else {
                            alert('Щось пiшло не так!');
                        }
                    }));
        }

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


