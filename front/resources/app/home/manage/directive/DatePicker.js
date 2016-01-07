
angular
    .module('app')
    .directive('datePicker', function() {

        return {
            restrict: 'A',
            controller: controller,
            link: link
        };

        //////////////////////////////////////////

        function link(scope, element, attrs) {

            console.log('date picker init');
            var provider = attrs['provider'];

            element.datepicker().on('changeDate', function(e) {
                // `e` here contains the extra attributes

                //console.log(e);

                //console.log(scope.vm);

                var date = e.format();

                if (date == '') {
                    return;
                }

                provider = date;

                //scope.vm.schedule.action.item.finished_at = date;
                scope.$apply();

                //vm.schedule.action.item.due = e.date;
            });
        }

        function controller($scope) {


        }

    });