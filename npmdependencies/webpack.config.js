var webpack = require('webpack');

var path = require('path');

var PROD = (process.env.NODE_ENV === 'production');

module.exports = {
    mode: 'development',
    entry: [
        './vendor.js'
    ],
    output: {
        publicPath: './',
        path: path.resolve(__dirname, 'output'),
        filename: PROD ? 'bundle.min.js' : 'bundle.js'
    },
    module: {
        rules: [
                /* url-loader uses inline encoding with base64
                 * I rather test file-loader which load images as separate files through webpack
                 */
            /*{

                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 100000
                    }
                }]
            },
            {
                test: /\.(ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 100000
                    }
                }]
            },
            {
                test: /\.(png|jpg|gif)$/i,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 100000
                    }
                }]
            },*/
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {}
                    }
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {}
                    }
                ]
            },
            {
                test: /\.css(\?v=\d+\.\d+\.\d+)?$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                exclude: [
                    path.resolve(__dirname, "node_modules")
                ]
            }
        ]
    },
    plugins: PROD ? [
	new webpack.optimize.UglifyJsPlugin({
	    minimize: true,
	    mangle: true,
	    compressor: { warnings: false }
        }),
        new webpack.DefinePlugin({
            'process.env': {
                // setting this again here, cause react needs it this way to
                // generate a real build-version of itself
                'NODE_ENV': JSON.stringify('production')
            }
        }),
    ] : [
	// ignores 3 warnings, that are probably irrelevant, when webpacking
	// new webpack.IgnorePlugin(/regenerator|nodent|js\-beautify/, /ajv/)
    ]
};
