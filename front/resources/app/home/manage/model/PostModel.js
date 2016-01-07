/**
 *
 * How to make a better component?
 * If the page is completly one thing, then component is
 * represent the data of the page, its a data store, and
 * some methods in it
 *
 * Route => Page => Partial Page => Component => View/Model
 *
 * The question is, we think of the app as page / ui, and
 * we can't get a component of ui in code..
 *
 * Lets think of it in another way, we have model and it had
 * data, then we will use some of them.., then it will show
 * in a view, the view will have event/interactive, the event
 * handler will go where, it if go one component, then it
 * will have many more..
 *
 * Now if this is model, and the question is, how about
 * relationship? project {member}, task {checklist, member..}
 *
 * project.member.add(), or project.addMember, or ProjectMember.add
 *
 * Is it possible to use require and then build it?
 *
 * We should use require actually..ES2015..
 *
 */

var ModelFactory = require("../../lib/ModelFactory");
var TestModel = require("./TestModel");

var instance;
var scope;

var PostModel =  {
    singleton: function (_scope, _models) {

        scope = _scope;

        if (!instance) {
            instance = createInstance(_models);
            console.log(instance);
        }

        console.log(instance);

        return instance;
    }
};

function createInstance(service) {

    var factory = ModelFactory();
    var model = service['Post'];

    var vm = {
        getBy: function (data) {

            if (data['id']) {

                if (this.list.length < 0) {
                    return null;
                }

                for (var i = 0; i < this.list.length; ++i) {

                    if (this.list[i].project_id == data['id']) {
                        return this.list[i];
                    }

                }

                return null;

            }
        },

        show: show,
    };

    vm = angular.extend(
        factory,
        vm
    );

    return vm;

    function show() {

        console.log("...show...");
        vm.list = TestModel.project.all();
        vm.item = vm.list[0];
    }

}

module.exports = ProjectModel;