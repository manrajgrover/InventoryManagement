"use strict";

const gulp = require('gulp');
const browserify = require('gulp-browserify');
const minifyCSS = require('gulp-minify-css');
const uglify = require('gulp-uglify');
const concat = require('gulp-concat');
const purify = require('gulp-purifycss');

/**
 * Browserifies JavaScript for distribution
 */
gulp.task('scripts', () => {
  gulp.src('src/js/angular-scripts.js')
    .pipe(browserify({
      debug : false
    }))
    .pipe(gulp.dest('./dist/js'));

  gulp.src('src/js/jquery-scripts.js')
    .pipe(browserify({
      debug : false
    }))
    .pipe(gulp.dest('./dist/js'))
});

/**
 * Combines and minifies CSS
 */
gulp.task('css', () => {
  gulp.src([
      'node_modules/bootstrap/dist/css/bootstrap.min.css',
      'node_modules/selectize/dist/css/selectize.bootstrap3.css',
      'src/css/*.css'
    ])
    .pipe(minifyCSS())
    .pipe(concat('style.min.css'))
    .pipe(purify(['dist/js/*.js', 'dist/views/*.html']))
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
  gulp.watch('src/views/*.html', ['views']);
  gulp.watch('src/css/*.css', ['css']);
  gulp.watch('src/images/*', ['images']);
})

/**
 * Gulp default task for running other tasks
 */
gulp.task('default', ['scripts', 'views', 'css', 'images'],  () => {
  console.log("Building project!");
});
