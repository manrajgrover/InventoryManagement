"use strict";

const gulp = require('gulp');
const browserify = require('gulp-browserify');
const minifyCSS = require('gulp-minify-css');
const uglify = require('gulp-uglify');
const concat = require('gulp-concat');

/**
 * Browserifies JavaScript for distribution
 */
gulp.task('scripts', () => {
  gulp.src('src/js/script.js')
    .pipe(browserify({
      debug : true
    }))
    .pipe(gulp.dest('./dist/js'))
});

/**
 * Copies CSS from source to distribution
 */
gulp.task('css', () => {
  let opts = {comments:true,spare:true};

  gulp.src(['node_modules/bootstrap/dist/css/bootstrap.min.css', 'src/css/*.css'])
    .pipe(minifyCSS(opts))
    .pipe(concat('style.min.css'))
    .pipe(gulp.dest('./dist/css'));
});

gulp.task('watch', () => {
  gulp.watch('src/js/*.js', ['scripts']);
  gulp.watch('src/css/*.css', ['css']);
})

/**
 * Gulp default task for running other tasks
 */
gulp.task('default', ['css', 'scripts'],  () => {
  console.log("Building project!");
});
