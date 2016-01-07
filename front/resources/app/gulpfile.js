var gulp = require('gulp');
var concat = require('gulp-concat');

var home = require("./home/manage/build");

var env = {
    'dev': {
        EndpointBaseUrl: "'conn/front/template';",
        TemplateBaseUrl: "'conn/front/template';"
    }
};

// ./node_modules/.bin/gulp

gulp.task('home', function (cb) {

    return home(gulp, env);
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

gulp.task('default', ['home'], function () {


});