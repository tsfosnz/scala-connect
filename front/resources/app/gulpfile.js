var gulp = require('gulp');
var concat = require('gulp-concat');

var taskBuildProject = require("./member/manage/build");

var env = {
    'dev': {
        EndpointBaseUrl: "'/team/teamapp/front/template';",
        TemplateBaseUrl: "'/team/teamapp/front/template';"
    }
};

// ./node_modules/.bin/gulp

gulp.task('member', function (cb) {

    return taskBuildProject(gulp, env);
});


var Server = require('karma').Server;

/**
 * Run test once and exit
 */
gulp.task('test', function (done) {
    new Server({
        configFile: __dirname + '/karma.conf.js',
        singleRun: true
    }, done).start();
});

gulp.task('default', ['member'], function () {


});