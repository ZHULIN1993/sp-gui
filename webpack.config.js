const webpack = require('webpack');
const path = require('path');
const rootDir = path.resolve(__dirname, "./");
const outoutDir = path.resolve(ROOT_DIR, "/output")
const scalajsBundler = require('./scalajs.webpack.config');
const webpackMerge = require('webpack-merge');

const spGUI = webpackMerge(scalajsBundler,
{
    mode: 'developement',
    entry: [
        vendors: [path.resolve(rootDir, './vendor.js')]
    ],
    output: {
        publicPath: [path.resolve(rootDir, './')],
        path: [path.resolve(outoutDir, './')],
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'url-loader?limit=100000'
            },
            {
                test: /\.css(\?v=\d+\.\d+\.\d+)?$/,
                loader: "style-loader!css-loader"
            },
            {
                test: /\.png(\?v=\d+\.\d+\.\d+)?$/,
                loader: 'url-loader?limit=100000'
            },
            {
       	        test: /\.json$/,
                loader: 'json-loader'
            }
        ]
    }

});

module.exports = SPGUI;
