module.exports = function (gulp, env) {

    "use strict";

    var browserify = require('gulp-browserify');
    var replace = require('gulp-replace');
    var rename = require('gulp-rename');

    var base = "./home";
    var module = "home";
    var prefix = "manage";

    var app = base + "/" + prefix + "/App.js";

    var endpoint = '';
    var templateBaseUrl = '';



    if (env['production']) {

        // replace something not for production

        endpoint = "var EndpointBaseUrl = " + env['production']['EndpointBaseUrl'];
        templateBaseUrl = "var TemplateBaseUrl = " + env['production']['TemplateBaseUrl'];
        console.log(endpoint);
        console.log(templateBaseUrl);

    } else {

        endpoint = "var EndpointBaseUrl = " + env['dev']['EndpointBaseUrl'];
        templateBaseUrl = "var TemplateBaseUrl = " + env['dev']['TemplateBaseUrl'];
        console.log(endpoint);
    }

    return gulp.src(app)
        .pipe(browserify({
            insertGlobals: true
        }))
        .pipe(replace(/var EndpointBaseUrl.*?;/g, endpoint))
        .pipe(replace(/var TemplateBaseUrl.*?;/g, templateBaseUrl))
        .pipe(rename(module + "." + prefix + ".js"))
        .pipe(gulp.dest('./build'));

};
