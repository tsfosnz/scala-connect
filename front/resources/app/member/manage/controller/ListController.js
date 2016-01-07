
var TestModel = require("../model/TestModel");
var Post = require("../service/Post");
var PostModel = require("../model/PostModel");

function ListController($scope, Post) {

    var vm = this;

    vm.post = PostModel.singleton($scope, {
        "Post": Post
    });

    TestModel.populate();
    return vm;
}

module.exports = ListController;
