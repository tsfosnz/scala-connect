
'use strict';

angular.module('app.models', []);
angular.module('app', [
    'ui.router',
    'app.models'
]).config(config);

var ListController = require("./controller/ListController");
var MainController = require("./controller/MainController");

function config($stateProvider) {

    //$urlRouterProvider.otherwise('/');

    $stateProvider.state('default', {

        url: '',
        templateUrl: 'manage/post.list.html',
        controller: ListController,
        controllerAs: 'vm'

    }).state('list', {

        url: '/',
        templateUrl: 'manage/post.list.html',
        controller: ListController,
        controllerAs: 'vm'

    });

}

angular.module('app').controller('MainController', MainController);
