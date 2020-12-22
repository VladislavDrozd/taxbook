(function () {
    'use strict';

    angular
        .module('appModule')
        .controller('clientsController', Controller);

    Controller.$inject = ['$http', '$cookies', '$uibModal', 'APP_LINK'];

    function Controller($http, $cookies, $uibModal, APP_LINK) {
        const vm = this;

        vm.language; // [en, ua]
        vm.user = {};
        vm.currentUser = {};
        vm.clients = [];
        vm.filterClients = [];
        vm.filterOptions = {
            clientId:'0',
            contacts:'',
            createDateTo:'',
            createDateFrom:''
        };


        vm.invalidateSession = invalidateSession;
        vm.modalOpenAdd = modalOpenAdd;
        vm.modalOpenUpdate = modalOpenUpdate;
        vm.loadFilteredClients = loadFilteredClients;


        activate();




        function activate() {
            loadClients();
            loadCookies();
            onLoad();
            getLoginUser();
        }

        function onLoad() {
            $("option[value='?']")
                .attr("style", "display:none;")
                .siblings().removeAttr("disabled");
            $("option[value='?']")
                .attr("disabled", "disabled;")
                .siblings().removeAttr("disabled");


            vm.filterOptions.clientId = $("div.clients select").val();
            vm.loadFilteredClients();

        }

        function getLoginUser() {
            $http.post(APP_LINK + 'app/user?action=getUserById')
                .then((response => {
                    console.log('RESPONSE:', response);
                    vm.currentUser = response.data;
                }), (() => {
                    alert("Ви не зареєструвалися як користувач!");
                    window.location.href = "../index.jsp";
                }));
        }


        function invalidateSession() {
            $http.post(APP_LINK + 'login?action=logout')
                .then(
                    () => {
                        window.location.href = "../index.jsp"; //This line redirects (redirect in Java does not work? because this method $http.get() асинхроннij).
                    },
                    () => {
                        alert('Виникла помилка. Повідомте будь ласка розробників.');
                    }
                );
        }

        function modalOpenUpdate(client) {
            const modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../html/createClientAddModal.html',
                controller: 'createClientAddModal',
                controllerAs: 'vm',
                bindToController: true,
                size: 'lg',
                resolve: {
                    client: function () {
                        return client;
                    },
                    action: function () {
                        return 'update';
                    }
                }
            });
            modalInstance.result.then(function (returnClient) {
                updateClient(returnClient);
            }, function () {
                loadFilteredClients();
                /* Do nothing when modal close*/
            });
        }

        function updateClient(client){
            $http.post(APP_LINK + 'app/client?action=updateClient', client)
                .then((response => {
                    console.log('RESPONSE:', response);
                    if (response.data === '1') {
                        alert('Клієнта успішно відредаговано');
                        loadClients();
                        loadFilteredClients();
                    } else {
                        alert('Клієнт не відредаговано. Зв`яжіться з адміністратором.');
                    }
                }), (
                    () => {
                        alert('Клієнт не відредаговано. Зв`яжіться з адміністратором.');

                    }));

        }


        function modalOpenAdd() {
            const newClient = {
                name:'',
                contacts:'',
                edrpou:'',
                notes:''
            };
            const modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../html/createClientAddModal.html',
                controller: 'createClientAddModal',
                controllerAs: 'vm',
                bindToController: true,
                size: 'lg',
                resolve: {
                    client: function () {
                        return newClient;
                    },
                    action: function () {
                        return 'add';
                    }
                }
            });
            modalInstance.result.then(function (returnClient) {

                addClient(returnClient);
            }, function () {
               /* Do nothing when modal close*/
            });
        }


        function addClient(client){
            $http.post(APP_LINK + 'app/client?action=addClient', client)
                .then((response => {
                    console.log('RESPONSE:', response);
                    if (response.status === 200) {
                        alert('Клієнта успішно додано');
                        loadClients();
                        loadFilteredClients();
                    } else {
                        alert('Клієнт не додан. Зв`яжіться з адміністратором.');
                        loadClients();
                    }
                }), (
                    () => {
                        alert('Клієнт не додан. Зв`яжіться з адміністратором.');
                        loadClients();
                    }));

        }


        function loadFilteredClients(){
                console.log("Find - ", vm.filterOptions);
                $http.post(APP_LINK + 'app/client?action=getClientsByFilter', vm.filterOptions)
                    .then(response => {
                        /*editResponseListAddDateType(response.data, 'createDate');*/
                        vm.filterClients = response.data;
                        console.log("Filter", vm.filterClients);
                    })

        }

        function loadClients() {
            $http.get(APP_LINK + 'app/client?action=getAllClients')
                .then(response => {
                    editResponseListAddDateType(response.data, 'createDate');
                    vm.clients = response.data;
                    console.log('LOADED', vm.clients);
                });

        }



        function editResponseListAddDateType(list, property) {
            list.map(r => r[property] = new Date(r[property]));
        }

        function loadCookies() {
            vm.language = $cookies.get('language');
            console.log('LOADED', vm.language);
        }

    }
})();


