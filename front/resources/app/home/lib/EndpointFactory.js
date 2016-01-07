
var EndpointFactory = function(baseUrl, routes) {

    'use strict';

    return createInstance(routes);

    function createInstance(routes) {

        angular.forEach(routes, function(value, key) {

            routes[key] = baseUrl + value;

        });

        return {
            api : routes,
            build : build
        };

        //////////////////////////////////////

        function build (url, params) {

            if (!params) {
                return url;
            }

            if (typeof params != 'object') {
                return url;
            }

            if (params.length <= 0) {
                return url;
            }

            angular.forEach(params, function(value, key) {
                url = url.replace(':' + key, value);
            });

            return url;
        }
    }

};

module.exports = EndpointFactory;