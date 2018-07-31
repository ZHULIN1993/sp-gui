var webpack = require('webpack');

module.exports = require('./scalajs.webpack.config');

var React = require('react');

var ReactDOM = require('react-dom');

var ReactGridLayout = require('react-grid-layout');
var ReactGridLayoutStyles = require('react-grid-layout/css/styles.css');


var JSONEditor = require('jsoneditor');
var JSONEditorCSS = require('jsoneditor/dist/jsoneditor.css');

var jQuery = require('jquery');

var chart = require('chart.js');

var pathlib = require('paths-js/path.js');

var bootstrap = require('bootstrap');
var bootstrapCSS = require('bootstrap/dist/css/bootstrap.css');
var glyphiconsTTF = require('bootstrap/fonts/glyphicons-halflings-regular.ttf');
var glyphiconsEOT = require('bootstrap/fonts/glyphicons-halflings-regular.eot');
var glyphiconsSVG = require('bootstrap/fonts/glyphicons-halflings-regular.svg');

var fontAwsome = require('font-awesome/css/font-awesome.css');

var angular = require('angular');
var angularGantt = require('angular-gantt');
var angularGanttCSS = require('angular-gantt/assets/angular-gantt.css');
var angularGanttJSPlugin = require('angular-gantt/assets/angular-gantt-plugins.js');
var angularGanttCSSPlugin = require('angular-gantt/assets/angular-gantt-plugins.css');
var angularMoment = require('angular-moment');

module.exports = {
    module: {
        loaders: [
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'url-loader?limit=100000'
            }, {
                test: /\.(ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=100000'
            }, {
                test: /\.css(\?v=\d+\.\d+\.\d+)?$/,
                loader: "style-loader!css-loader"
            }, {
                test: /\.png(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=100000'
            }, {
		test: /\.json$/,
		loader: 'json-loader'
	    }
        ]
    }
};
