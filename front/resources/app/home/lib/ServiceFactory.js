
var ServiceFactory = function (angular, name, Endpoint) {

    'use strict';

    angular.module('app.models')
        .factory(name, _model);

    _model.$injector = ['$http'];

    function _model($http) {

        var service = {
            all: all,
            one: one,
            add: add,
            update: update,
            remove: remove
        };

        return service;

        ///////////////////////////////////////

        function all(param) {

            var url = Endpoint.build(Endpoint.api['all'], param.query);

            if (url === null) {
                return null;
            }

            console.log(url);

            return $http.get(url).then(function (result) {
                return result;
            });
        }

        function one(param) {

            var url = Endpoint.build(Endpoint.api['one'], param.query);

            if (url === null) {
                return null;
            }

            console.log(url);

            return $http.get(url).then(function (result) {
                return result;
            });
        }

        function update(param) {

            var url = Endpoint.build(Endpoint.api['update'], param.query);

            if (url === null) {
                return null;
            }

            console.log(url);

            return $http({

                url: url,
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                data: $.param(param.data)

            }).then(function (result) {
                return result;
            });
        }

        function add(param) {

            var url = Endpoint.build(Endpoint.api['new'], param.query);

            console.log(url);
            console.log(param);

            if (url === null) {
                return null;
            }

            return $http({

                url: url,
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                data: $.param(param.data)

            }).then(function (result) {
                return result;
            });

        }

        function remove(param) {

            var url = Endpoint.build(Endpoint.api['remove'], param.query);

            if (url === null) {
                return null;
            }

            console.log(url);

            return $http({

                url: url,
                method: "POST"

            }).then(function (result) {
                return result;
            });
        }
    }

};

module.exports = ServiceFactory;


