/*
 * About component design, this is weak part of AngularJS, to design re-used component
 * wasn't easy, and ReactJS + Flux is much more better at this point, I just want to save
 * time when starting to build a new part of the app, and new application..
 *
 * Basically the component will do have show a list, edit, add, delete feature,
 * and that's same action for most of them, but not all same..
 *
 * For example, if add clinic, we only added it then, but if we add contact,
 * we will add it by clinic, so we need access clinic id at this point, then two add func
 * are different
 *
 * Even though still good to build some abstract class to share values among them
 *
 * We still have to re-build components for each page, and change model & endpoint
 *
 */
var ModelFactory = function () {

    var vm = {
        item: null,
        list: [],

        index: 0,

        keyword: '',
        type: '',
        page: '',
        count: 10,

        error: false,
        message: '',
        loading: ''

    };

    return vm;

};

module.exports = ModelFactory;

