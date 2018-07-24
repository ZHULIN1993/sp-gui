const webpack = require('webpack');
const path = require('path');
const rootDir = path.resolve(__dirname, "./");
const outoutDir = path.resolve(ROOT_DIR, "/output")
const scalajsBundler = require('./scalajs.webpack.config');
const webpackMerge = require('webpack-merge');

const spGUI = webpackMerge(scalajsBundler,
{
    mode: 'developement',
    output: {
        publicPath: [path.resolve(rootDir, './')],
        path: [path.resolve(outoutDir, './')],
        filename: 'bundle.js'
    }
});

module.exports = spGUI;
