"use strict";

const gulp = require('gulp');
const browserify = require('browserify');
const minifyCSS = require('gulp-minify-css');
const uglify = require('gulp-uglify');
const concat = require('gulp-concat');
const purify = require('gulp-purifycss');
const buffer = require('vinyl-buffer');
const source = require('vinyl-source-stream');
const ngAnnotate = require('gulp-ng-annotate');
const babelify  = require('babelify');
const htmlmin = require('gulp-htmlmin');

/**
 * Browserifies JavaScript for distribution
 */
gulp.task('scripts', () => {

  browserify('src/js/angular-scripts.js')
    .transform("babelify", { presets: ["es2015"] })
    .bundle()
    .pipe(source('angular-scripts.js'))
    .pipe(buffer())
    .pipe(ngAnnotate())
    .pipe(uglify())
    .pipe(gulp.dest('./dist/js'));

  browserify('src/js/jquery-scripts.js')
    .transform("babelify", { presets: ["es2015"] })
    .bundle()
    .pipe(source('jquery-scripts.js'))
    .pipe(buffer())
    .pipe(uglify())
    .pipe(gulp.dest('./dist/js'));
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
    .pipe(purify(['dist/js/*.js', 'dist/views/*.html']))
    .pipe(minifyCSS({keepSpecialComments : 0}))
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
    .pipe(htmlmin({collapseWhitespace: true}))
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
