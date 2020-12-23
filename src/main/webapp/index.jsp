<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="libs/bootstrap-4.5.2-dist/css/bootstrap.min.css">

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="libs/bootstrap-4.5.2-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/main-style.css">
    <link rel="stylesheet" href="css/article.css">
    <link rel="stylesheet" href="css/beforeLog.css">
    <link rel="stylesheet" href="css/afterLog.css">

    <!--   <link rel="stylesheet" href="style.css">-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <script src="libs/angular-1.8.0/angular.js"></script>
    <script src="libs/angular-1.8.0/angular-cookies.js"></script>
    <script src="libs/angular-1.8.0/angular-resource.js"></script>
    <script src="libs/angular-1.8.0/ui-bootstrap-tpls-0.14.3.js"></script>
    <script src="libs/angular-1.8.0/angular-touch.js"></script>
    <script src="libs/angular-1.8.0/angular-animate.js"></script>
    <script src="js/appModule.module.js"></script>
    <script src="js/appConstants.component.js"></script>
    <script src="js/index.component.js"></script>

</head>
<body ng-app="appModule" ng-controller="indexController as vm" ng-cloak>

<%--Preloader--%>

<%--https://itchief.ru/javascript/how-to-make-preloader-for-site--%>

<%--<div class="preloader">
    <div class="preloader__row">
        <div class="preloader__item"></div>
        <div class="preloader__item"></div>
    </div>
</div>--%>


<div class="container-fluid header" id="navbar">
    <nav class="navbar  navbar-expand-lg navbar-light bg-light ">
        <a class="navbar-brand" href="#">
            <img class="mr-2" src="img/logo.png" width="35px" >
            <span>TAXBOOK</span>
        </a>
        <button ng-if="vm.isLogin" class="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon" ></span>
        </button>


        <div class="collapse" id="navbarSupportedContent">
            <div class="overlay"></div>
        </div>


        <div ng-if="vm.isLogin" class="collapse navbar-collapse align-content-center" id="navbarSupportedContent">

            <button class="close" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" >X</button>

            <ul class="navbar-nav">

                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">Головна</a>
                </li>
                <li class="nav-item ml-3 EL">
                    <a class="nav-link" href="html/incomeBook.component.html">Електронна книга доходів</a>
                </li>
                <li class="nav-item ml-3  clients">
                    <a class="nav-link" href="html/client.component.html">Клієнти</a>
                </li>
                <button class="btn btn-toggler-out" style="padding-left: 10px; padding-right: 10px" ng-click="vm.invalidateSession()">Вийти з профілю</button>

            </ul>



        </div>
        <div ng-if="vm.isLogin" class="form-inline ml-auto inLog" >
        <button class="btn btn-log-out" style="padding-left: 10px; padding-right: 10px;" ng-click="vm.invalidateSession()">Вийти з профілю</button>
        </div>
        <div ng-if="!vm.isLogin" class="form-inline ml-auto">
            <button type="button" class="btn btn-reg" data-toggle="modal" data-target="#regModal">Реєстрація</button>
            <button  type="button" class="btn btn-log-in ml-3" data-toggle="modal" data-target="#logModal">Вхід</button>
        </div>

    </nav>
</div>



<div class="container-fluid p-0 page-header">
    <div class="container-bg text-center pr-4 pl-4 mb-4">
        <h1 >
            <span ng-if="!vm.isLogin" style="font-family:'Museo Sans Cyrl 900', sans-serif ">Taxbook</span>
            <span ng-if="vm.isLogin"  style="font-family:'Museo Sans Cyrl 900', sans-serif ">ФОП {{vm.currentUser.name}}</span>
            <p class="mt-3 mb-0"><small class="small-about">Швидкий та зручний сервіс для разрахунку оподаткування та ведення книги доходів ФОП України.
            </small></p>
            <p class="about"><small class="small-about">Електронна книга доходів. База контактів контрагентів.</small></p></h1>
    </div>

</div>

<article class="about">
    <div class="container-fluid form-1">
        <div class="row">

            <div class="col-xl-5 info text-left pl-4">
                <h4 class="mb-3" style = "font-family: 'Museo Sans Cyrl 700', sans-serif">I Група</h4>
                <list class="list">
                    <li>Обмеження: торгівля виключно на ринках.</li>
                    <li>ФОП, які не використовують працю найманих робітників.</li>
                    <li>ФОП, виручка котрих є не більшою за 300 тис. грн./один календарний рік.</li>
                    <li>Роздрібний продаж товарів тільки на ринках.</li>
                    <li>Надання побутових послуг населенню.</li>
                </list>
            </div>
            <div class="col-xl-7 img d-flex justify-content-end">
                <img src="img/Form1.jpg" alt="I Група">
            </div>
        </div>
    </div>
    <div class="container-fluid form-2">
        <div class="row">
            <div class="col-xl-5 info text-left pl-4 order-xl-2">
                <h4 class="mb-3" style = "font-family: 'Museo Sans Cyrl 700', sans-serif">II Група</h4>
                <list class="list">
                    <li>ФОП, виручка котрих є не більшою за 1,5 млн.</li>
                    <li>ФОП, які використовують працю не більше ніж 10 найманих працівників.</li>
                    <li>Виробництво і / або продаж товарів (кому завгодно).</li>
                    <li>Не мають права надавати послуги юридичним особам не платникам єдиного податку.</li>
                </list>
            </div>
            <div class="col-xl-7 img d-flex justify-content-start order-xl-1">
                <img src="img/Form2.jpg" alt="II Група">
            </div>



        </div>
    </div>
    <div class="container-fluid form-3">
        <div class="row">

            <div class="col-xl-5 info text-left pl-4">
                <h4 class="mb-3" style = "font-family: 'Museo Sans Cyrl 700', sans-serif">III Група</h4>
                <list class="list">

                    <li> ФОП, виручка котрих є не більшою за 5 млн. грн./один календарний рік.</li>
                    <li>ФОП, які використовують працю найманих робітників без кількісних обмежень.</li>
                    <li>Будь-які види діяльності, крім тих, які заборонені для ФОП, які сплачують єдиний податок.</li>

                </list>
            </div>
            <div class="col-xl-7 img d-flex justify-content-end">
                <img src="img/Form3.jpg" alt="III Група">
            </div>
        </div>
    </div>
    <div class="container-fluid annoying-reg d-flex justify-content-center">

        <button ng-if="!vm.isLogin" type="button" class="btn btn-annoying-reg mr-5" data-toggle="modal" data-target="#regModal">Реєстрація</button>
        <button type="button" class="btn btn-feedback" data-toggle="modal" data-target="#feedbackModal">Зворотній Зв'язок</button>

    </div>
</article>


<footer class="page-footer font-small">

    <div class="container-fluid text-center">

        <!-- Grid row -->
        <div class="row">

            <!-- Grid column -->
            <div class="col-md-6 mx-auto developers">

                <!-- Links -->
                <h6>Команда Розробників:</h6>

                <ul class="list-unstyled">
                    <li>
                        Владислав Дрозд -
                        <a href="https://github.com/VladislavDrozd">https://github.com/VladislavDrozd</a>
                    </li>
                    <li>
                        Рилов Володимир -
                        <a href="https://github.com/VladimirRylov">https://github.com/VladimirRylov</a>
                    </li>
                    <li>
                        Анастасія Мустратова -
                        <a href="https://www.instagram.com/anastasia.mustratova">https://www.instagram.com/anastasia.mustratova/</a>
                    </li>
                </ul>

            </div>

        </div>


    </div>
    <div class="footer-copyright text-center py-3">© 2020 Миколаїв
    </div>
</footer>




<!-- Modal reg -->
<div id="regModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Реєстрація</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form class="form" action="login?action=register" method="post">
                <div class="modal-body">

                    <div class="form-group">
                        <label for="usr">ПІБ:</label>
                        <input name="name" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="pwd">Ваш пароль:</label>
                        <input name="password" type="password" class="form-control" required >
                    </div>
                    <div class="form-group">
                        <label for="eml">Ваш email:</label>
                        <input name="email" type="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Ваш телефон:</label>
                        <input name="phone" type="phone" maxlength='13' class="form-control" placeholder="+" required>
                    </div>
                    <div class="form-group">
                        <label for="sel1">Ваша група:</label>
                        <select name="taxGroup" class="form-control" required>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                        </select>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="submit" name="submit" class="btn btn-submit" value="Register">Реєстрація</button>
                </div>
            </form>
        </div>

    </div>
</div>

<!-- Modal log -->
<div id="logModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Вхід</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form class="form" name="loginForm">
                <div class="modal-body">
                    <div class="form-group">
                        <input name="name" ng-model="vm.loginUser.login" type="email" class="form-control"  placeholder="email" required>
                    </div>
                    <div class="form-group">
                        <input name="password" ng-model="vm.loginUser.password" type="password" class="form-control"  placeholder="пароль" required>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="submit" ng-click="vm.loginUser(loginForm)" name="submit" class="btn btn-submit" value="submit">Увійти</button>
                </div>

            </form>
        </div>

    </div>
</div>

<!-- Modal feedback -->
<div id="feedbackModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Зворотній зв'язок</h4>
                    <small style="font-size:16px; font-family: 'Museo Sans Cyrl 500', sans-serif">Залиште своє повідомлення і незабаром ми зв'яжемося з вами</small>
                </div>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <form class="form" name="feedBackForm">
            <div class="modal-body">

                <div class="form">
                    <div class="form-group">
                        <label>Ваше Ім'я:</label>
                        <input type="text" class="form-control" ng-model="vm.email.name" required>
                    </div>

                    <div class="form-group">
                        <label>Ваш email:</label>
                        <input type="email" class="form-control" ng-model="vm.email.email" required>
                    </div>
                    <div class="form-group">
                        <label>Повідомлення</label>
                        <textarea class="form-control" ng-model="vm.email.text" rows="3" required></textarea>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button type="submit" ng-click="vm.sendSimpleEmail(feedBackForm)"
                        class="btn btn-submit">Відправити</button>
            </div>

            </form>
        </div>

    </div>
</div>

<%--<body>--%>

<%--<h2>Hello World</h2>--%>

<%--<h2>LOGIN</h2>--%>
<%--<form action="login?action=login" method="post">--%>
<%--    Login name (email): <input name="name" type="text" /><br>--%>
<%--    Password: <input name="password" type="text"/><br>--%>
<%--    <input type="submit" value="Login" />--%>
<%--</form>--%>

<%--<h2>REGISTER</h2>--%>
<%--<form action="login?action=register" method="post">--%>
<%--    Name: <input name="name" type="text" /><br>--%>
<%--    Email: <input name="email" type="text"/><br>--%>
<%--    Phone: <input name="phone" type="text"/><br>--%>
<%--    TaxGroup: <input name="taxGroup" type="text" placeholder="1, 2, 3"/><br>--%>
<%--    Password: <input name="password" type="text"/><br>--%>
<%--    <input type="submit" value="Register" />--%>
<%--</form>--%>

<%--<h2>LOGOUT</h2>--%>
<%--<form action="login?action=logout" method="post">--%>
<%--    <input type="submit" value="Logout" />--%>
<%--</form>--%>

<%--</body>--%>
</html>
