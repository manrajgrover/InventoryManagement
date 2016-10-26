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
      debug : false
    }))
    .pipe(gulp.dest('./dist/js'))
});

/**
 * Combines and minifies CSS
 */
gulp.task('css', () => {
  let opts = {comments:true,spare:true};

  gulp.src([
      'node_modules/bootstrap/dist/css/bootstrap.min.css',
      'node_modules/selectize/dist/css/selectize.bootstrap3.css',
      'src/css/*.css'
    ])
    .pipe(minifyCSS(opts))
    .pipe(concat('style.min.css'))
    .pipe(gulp.dest('./dist/css'));
});

/**
 * Copies images from src to dist
 */
gulp.task('images', () => {
  gulp.src('src/images/*')
    .pipe(gulp.dest('./dist/images'));
});

/**
 * Copies views from src to dist
 */
gulp.task('views', () => {
  gulp.src('src/views/*')
    .pipe(gulp.dest('./dist/views'));
});

gulp.task('watch', () => {
  gulp.watch('src/js/*.js', ['scripts']);
  gulp.watch('src/css/*.css', ['css']);
  gulp.watch('src/views/*.html', ['views']);
  gulp.watch('src/images/*', ['images']);
})

/**
 * Gulp default task for running other tasks
 */
gulp.task('default', ['css', 'scripts', 'images', 'views'],  () => {
  console.log("Building project!");
});
