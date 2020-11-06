(function () {
   'use strict';

   angular
       .module('incomeBookTableModule')
       .controller('incomeBookTableController', Controller);

   Controller.$inject = ['$http', '$cookies'];

    function Controller($http, $cookies) {
        const vm = this;
        const APP_LINK = 'http://localhost:8080/taxbook/';

        vm.language; // [en, ua]
        vm.user = {};
        vm.clients = [];
        vm.recordList = [];
        vm.recordDayList = [];
        vm.recordMonthList = [];

        vm.printRecord = printRecord;

        activate();

        function activate() {
            loadRecords();
            loadUser();
            loadClients();
            loadCookies();
        }

        function printRecord(record) {
            console.log(record);
        }

        function loadRecords() {
            $http.get(APP_LINK + 'app/incomeBook?action=getAllRecords')
                .then(response => {
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

