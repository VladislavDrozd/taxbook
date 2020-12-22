(function () {
   'use strict';

   angular
       .module('appModule')
       .controller('incomeBookTableController', Controller);

   Controller.$inject = ['$http', '$cookies', '$uibModal', 'APP_LINK'];

    function Controller($http, $cookies, $uibModal, APP_LINK) {
        const vm = this;

        vm.language; // [en, ua]
        vm.user = {};
        vm.clients = [];
        vm.recordList = [];
        vm.recordDayList = [];
        vm.recordMonthList = [];
        vm.filteredRecordList = [];

        // vm.printRecord = printRecord;
        vm.updateRecordModal = updateRecordModal;
        vm.deleteRecord = deleteRecord;
        vm.addRecordModal = addRecordModal;

        vm.invalidateSession = invalidateSession;

        activate();

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
            /*
                url: APP_LINK + 'login?action=logout',
                method: 'GET',
            }).success(function(data, status, headers, config) {
                $window.location.href = "index.jsp"; //This line redirects (redirect in Java does not work? because this method $http.get() асинхроннij).
            }).error(function(data, status, headers, config) {
                alert('Виникла помилка. Повідомте будь ласка розробників.');
            });*/
        }

        function activate() {
            loadRecords();
            loadUser();
            loadClients();
            loadCookies();
        }

        function addRecordModal() {
            const newRecord = {
                anotherProfitIncome: 0,
                anotherProfitType: '',
                clientId: null,
                dateTime: '',
                freeReceived: 0,
                income: 0,
                notes: '',
                refund: 0,
                revised: 0,
                totalIncome: 0
            };
            const modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../html/createUpdateRecordModal.html',
                controller: 'createUpdateRecordModal',
                controllerAs: 'vm',
                bindToController: true,
                size: 'lg',
                resolve: {
                    record: function () {
                        return newRecord;
                    },
                    action: function () {
                        return 'add';
                    },
                    clientArray: function () {
                        return vm.clients;
                    }
                }
            });
            modalInstance.result.then(function (returnRecord) {
                addRecord(returnRecord);
            }, function () {
                /*Do nothing when modal close*/
            });
        }

        function updateRecordModal(record) {
            const modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../html/createUpdateRecordModal.html',
                controller: 'createUpdateRecordModal',
                controllerAs: 'vm',
                bindToController: true,
                size: 'lg',
                resolve: {
                    record: function () {
                        if (record.clientId===0) {
                            record.clientId = null;
                        }
                        return record;
                    },
                    action: function () {
                        return 'update';
                    },
                    clientArray: function () {
                        return vm.clients;
                    }
                }
            });
            modalInstance.result.then(function (returnRecord) {
                    updateRecord(returnRecord);
            }, function () {
                /*Do nothing when modal close*/
            });
        }

        function addRecord(record) {
            $http.post(APP_LINK + 'app/incomeBook?action=addRecord', record)
                .then((response => {
                    console.log('RESPONSE:', response);
                    if (response.status === 200) {
                        alert('Запис успішно створено');
                        loadRecords();
                    } else {
                        alert('Запис не створено. Зв`яжіться з адміністратором.');
                    }
                }), (
                    () => {
                        alert('Запис не створено. Зв`яжіться з адміністратором.');
                    }));
        }

        function updateRecord(record) {
            $http.post(APP_LINK + 'app/incomeBook?action=updateRecord', record)
                .then((response => {
                    console.log(response);
                    if (response.data === '1') {
                        alert('Запис успішно відредаговано');
                        loadRecords();
                    } else {
                        alert('Запис не відредаговано. Зв`яжіться з адміністратором.');
                        loadRecords();
                    }
                }), (
                    () => {
                        alert('Запис не відредаговано. Зв`яжіться з адміністратором.');
                        loadRecords();
                }));
        }

        function deleteRecord(record) {
            $http.get(APP_LINK + 'app/incomeBook?action=deleteRecord&recordId=' + record.recordId)
                .then(response => {
                    if (response.data === '1') {
                        alert('Запис успішно видалено.');
                        loadRecords();
                    } else {
                        alert('Запис не видалено. Зв`яжіться з адміністратором.');
                    }
                },
                    () => {
                        alert('Запис не видалено. Зв`яжіться з адміністратором.');
                });
        }

        function loadRecords() {
            $http.get(APP_LINK + 'app/incomeBook?action=getAllRecords')
                .then(response => {
                    if (response.data.length === 0) return;
                    editResponseListAddDateType(response.data, 'dateTime');
                    vm.recordList = response.data;
                    vm.recordDayList = responseListToRecordDayList(vm.recordList);
                    vm.recordMonthList = responseDayListToRecordMonthList(vm.recordDayList);
                    console.log('LOADED', vm.recordList);
                    console.log('LOADED', vm.recordDayList);
                    console.log('LOADED', vm.recordMonthList);
                });
        }

        function loadUser() {
            $http.get(APP_LINK + 'app/user?action=getUserById')
                .then(response => {
                    response.data.createDate = new Date(response.data.createDate);
                    vm.user = response.data;
                    console.log('LOADED', vm.user);
                }, () => {
                    alert("Ви не зареєструвалися як користувач!");
                    window.location.href = "../index.jsp";
                });
        }

        function loadClients() {
            $http.get(APP_LINK + 'app/client?action=getAllClients')
                .then(response => {
                    editResponseListAddDateType(response.data, 'createDate');
                    vm.clients = response.data;
                    console.log('LOADED', vm.clients);
                });
        }

        function loadCookies() {
            vm.language = $cookies.get('language');
            console.log('LOADED', vm.language);
        }

        function editResponseListAddDateType(list, property) {
            list.map(r => r[property] = new Date(r[property]));
        }

        function responseListToRecordDayList(list) {
            let recordDayArr = [];
            let prevRecord = list.shift();
            recordDayArr.push(new RecordDay());
            last(recordDayArr).recordArray.push(prevRecord);
                const currentRecordDay = last(recordDayArr);
                currentRecordDay.day = prevRecord.dateTime;
                currentRecordDay.dayIncome = prevRecord.income;
                currentRecordDay.dayRefund = prevRecord.refund;
                currentRecordDay.dayRevised = prevRecord.revised;
                currentRecordDay.dayFreeReceived = prevRecord.freeReceived;
                currentRecordDay.dayTotalIncome = prevRecord.totalIncome;
                currentRecordDay.dayAnotherProfitIncome = prevRecord.anotherProfitIncome;
                currentRecordDay.dayTotalIncomePlusAnotherProfitIncome = (prevRecord.anotherProfitIncome + prevRecord.totalIncome);
            for (let record of list) {
                let recordDate = record.dateTime.getDate();
                let prevRecordDate = prevRecord.dateTime.getDate();
                if (recordDate !== prevRecordDate) {
                    recordDayArr.push(new RecordDay());
                }
                const currentRecordDay = last(recordDayArr);
                currentRecordDay.day = record.dateTime;
                currentRecordDay.dayIncome += record.income;
                currentRecordDay.dayRefund += record.refund;
                currentRecordDay.dayRevised += record.revised;
                currentRecordDay.dayFreeReceived += record.freeReceived;
                currentRecordDay.dayTotalIncome += record.totalIncome;
                currentRecordDay.dayAnotherProfitIncome += record.anotherProfitIncome;
                currentRecordDay.dayTotalIncomePlusAnotherProfitIncome += (record.anotherProfitIncome + record.totalIncome);
                currentRecordDay.recordArray.push(record);
                prevRecord = record;
            }
            return recordDayArr;
        }

        function responseDayListToRecordMonthList(dayList) {
            let recordMonthArr = [];
            let prevDayRecord = dayList.shift();
            recordMonthArr.push(new RecordMonth());
            last(recordMonthArr).recordDayArray.push(prevDayRecord);
                const currentRecordMonth = last(recordMonthArr);
                currentRecordMonth.month = getMonthName(prevDayRecord.day.getMonth());
                currentRecordMonth.monthIncome = prevDayRecord.dayIncome;
                currentRecordMonth.monthRefund = prevDayRecord.dayRefund;
                currentRecordMonth.monthRevised = prevDayRecord.dayRevised;
                currentRecordMonth.monthFreeReceived = prevDayRecord.dayFreeReceived;
                currentRecordMonth.monthTotalIncome = prevDayRecord.dayTotalIncome;
                currentRecordMonth.monthAnotherProfitIncome = prevDayRecord.dayAnotherProfitIncome;
                currentRecordMonth.monthTotalIncomePlusAnotherProfitType = (prevDayRecord.dayAnotherProfitIncome + prevDayRecord.dayTotalIncome);
            for (let record of dayList) {
                let recordMonth = record.day.getMonth();
                let prevRecordMonth = prevDayRecord.day.getMonth();
                if (recordMonth !== prevRecordMonth) {
                    recordMonthArr.push(new RecordMonth());
                }
                const currentRecordMonth = last(recordMonthArr);
                currentRecordMonth.month = getMonthName(record.day.getMonth());
                currentRecordMonth.monthIncome += record.dayIncome;
                currentRecordMonth.monthRefund += record.dayRefund;
                currentRecordMonth.monthRevised += record.dayRevised;
                currentRecordMonth.monthFreeReceived += record.dayFreeReceived;
                currentRecordMonth.monthTotalIncome += record.dayTotalIncome;
                currentRecordMonth.monthAnotherProfitIncome += record.dayAnotherProfitIncome;
                currentRecordMonth.monthTotalIncomePlusAnotherProfitType += (record.dayAnotherProfitIncome + record.dayTotalIncome);
                currentRecordMonth.recordDayArray.push(record);
                prevDayRecord = record;
            }
            return recordMonthArr;
        }

        function last(list) {
            return list[list.length - 1];
        }

        function getMonthName(num) {
            let month = '';
            if (vm.language === 'ua') {
                switch (num) {
                    case 0 : month = 'Січень'; break;
                    case 1 : month = 'Лютий'; break;
                    case 2 : month = 'Березень'; break;
                    case 3 : month = 'Квітень'; break;
                    case 4 : month = 'Травень'; break;
                    case 5 : month = 'Червень'; break;
                    case 6 : month = 'Липень'; break;
                    case 7 : month = 'Серпень'; break;
                    case 8 : month = 'Вересень'; break;
                    case 9 : month = 'Жовтень'; break;
                    case 10 : month = 'Листопад'; break;
                    case 11 : month = 'Грудень'; break;
                }
            }
            if (vm.language === 'en') {
                /*switch (num) {
                    case 0 : month = 'January'; break;
                }*/
            }
            return month;
        }
        
    }
})();

class RecordMonth {
    month;
    monthIncome = 0;
    monthRefund = 0;
    monthRevised = 0;
    monthFreeReceived = 0;
    monthTotalIncome = 0;
    monthAnotherProfitIncome = 0;

    monthTotalIncomePlusAnotherProfitType = 0;
    
    recordDayArray = [];
}

class RecordDay {
    day;
    dayIncome = 0;
    dayRefund = 0;
    dayRevised = 0;
    dayFreeReceived = 0;
    dayTotalIncome = 0;
    dayAnotherProfitIncome = 0;
    dayTotalIncomePlusAnotherProfitIncome = 0;
    recordArray = [];
}


