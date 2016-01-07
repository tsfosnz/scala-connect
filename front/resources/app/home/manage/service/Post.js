
var EndpointFactory = require("../../lib/EndpointFactory");
var ServiceFactory = require("../../lib/ServiceFactory");
var Endpoint = require("./Endpoint");

var EndpointPost = EndpointFactory(Endpoint.endpointBaseUrl, {



});

// will have to know how to extend the model
// first get angularjs service

ServiceFactory(angular, "Post", EndpointPost);

