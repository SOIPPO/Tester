module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        concat: {
            userjs: {
                src: [
                    'src/main/webapp/plugins/bower/jquery/dist/jquery.js',
                    'src/main/webapp/plugins/bower/angular/angular.js',
                    'src/main/webapp/plugins/bower/bootstrap/dist/js/bootstrap.js',
                    'src/main/webapp/plugins/bower/angular-messages/angular-messages.js',
                    'src/main/webapp/plugins/bower/alertify-js/build/alertify.js',
                    'src/main/webapp/plugins/bower/blockUI/jquery.blockUI.js',
                    'src/main/webapp/plugins/bower/angular-validation-match/dist/angular-validation-match.js',
                    'src/main/webapp/plugins/jquery.formstyler/jquery.formstyler.min.js',
                    'src/main/webapp/plugins/bower/select2/dist/js/select2.full.js',
                    'src/main/webapp/static/utility.js'
                ],
                dest: "src/main/webapp/plugins/user-scripts.js"
            },

            adminjs: {
                src: [
                    'src/main/webapp/plugins/bower/jquery/dist/jquery.js',
                    'src/main/webapp/plugins/bower/angular/angular.js',
                    'src/main/webapp/plugins/bower/bootstrap/dist/js/bootstrap.js',
                    'src/main/webapp/plugins/bower/angular-messages/angular-messages.js',
                    'src/main/webapp/plugins/bower/jquery-ui/jquery-ui.min.js',
                    'src/main/webapp/plugins/bower/alertify-js/build/alertify.js',
                    'src/main/webapp/plugins/bower/blockUI/jquery.blockUI.js',
                    'src/main/webapp/plugins/bower/angular-validation-match/dist/angular-validation-match.js',
                    'src/main/webapp/plugins/bower/datatables.net/js/jquery.dataTables.js',
                    'src/main/webapp/plugins/bower/datatables.net-buttons/js/dataTables.buttons.js',
                    'src/main/webapp/plugins/bower/angular-xeditable/dist/js/xeditable.js',
                    'src/main/webapp/plugins/bower/select2/dist/js/select2.full.js',
                    'src/main/webapp/plugins/jquery.formstyler/jquery.formstyler.min.js',
                    'src/main/webapp/plugins/bower/jquery.livequery/dist/jquery.livequery.js',
                    'src/main/webapp/plugins/bower/angular-sanitize/angular-sanitize.js',
                    'src/main/webapp/plugins/bower/angular-ui-select/dist/select.js',
                    'src/main/webapp/static/utility.js'
                ],
                dest: "src/main/webapp/plugins/admin-scripts.js"
            },


            usercss: {
                src: [
                    'src/main/webapp/plugins/bower/bootstrap/dist/css/bootstrap.css',
                    'src/main/webapp/plugins/bower/alertify-js/build/css/alertify.css',
                    'src/main/webapp/plugins/bower/alertify-js/build/css/themes/bootstrap.css',
                    'src/main/webapp/static/css/register-form.css',
                    'src/main/webapp/plugins/bower/select2/dist/css/select2.css',
                    'src/main/webapp/plugins/jquery.formstyler/jquery.formstyler.css'
                ],
                dest: "src/main/webapp/static/user-styles.css"
            },
            admincss: {
                src: [
                    'src/main/webapp/plugins/bower/bootstrap/dist/css/bootstrap.css',
                    'src/main/webapp/plugins/bower/alertify-js/build/css/alertify.css',
                    'src/main/webapp/plugins/bower/alertify-js/build/css/themes/bootstrap.css',
                    'src/main/webapp/static/css/register-form.css',
                    'src/main/webapp/plugins/jquery.formstyler/jquery.formstyler.css',
                    'src/main/webapp/plugins/bower/jquery-ui/themes/base/jquery-ui.css',
                    'src/main/webapp/plugins/bower/datatables.net-dt/css/jquery.dataTables.css',
                    'src/main/webapp/plugins/bower/datatables.net-buttons-dt/css/buttons.dataTables.css',
                    'src/main/webapp/plugins/bower/angular-xeditable/dist/css/xeditable.css',
                    'src/main/webapp/plugins/bower/select2/dist/css/select2.css',
                    'src/main/webapp/plugins/bower/angular-ui-select/dist/select.css',
                    'src/main/webapp/plugins/angular-ui/selectize.default.min.css'

                ],
                dest: "src/main/webapp/static/admin-styles.css"
            }
        },
        cssmin: {
            admin: {
                src: 'src/main/webapp/static/admin-styles.css',
                dest: 'src/main/webapp/static/admin-styles.min.css'
            },
            user: {
                src: 'src/main/webapp/static/user-styles.css',
                dest: 'src/main/webapp/static/user-styles.min.css'
            }
        },
        uglify: {
            user: {
                src: 'src/main/webapp/plugins/user-scripts.js',
                dest: 'src/main/webapp/static/user-scripts.min.js'
            },
            admin: {
                src: 'src/main/webapp/plugins/admin-scripts.js',
                dest: 'src/main/webapp/static/admin-scripts.min.js'
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');

    grunt.registerTask('default', ['concat', 'uglify', 'cssmin']);

};