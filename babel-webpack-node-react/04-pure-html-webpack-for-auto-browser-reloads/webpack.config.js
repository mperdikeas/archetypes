'use strict';
const path = require('path');

const APPDIR = 'app/';

const HtmlWebpackPlugin = require('html-webpack-plugin');
const HTMLWebpackPluginConfig = new HtmlWebpackPlugin({
    template: path.resolve(__dirname, APPDIR, 'index.html'),
    filename: 'index.html',
    inject: 'body'
});

const config = {
    context: path.resolve(__dirname, APPDIR),
    entry__NOT_USED_FOR_PURE_HTML_ARCHETYPE: './main.js',
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel',
                include: path.resolve(__dirname, 'app/')
            },{
                test: /\.css$/,
                loaders: ['style', 'css']
            }
        ]
    },
    plugins: [HTMLWebpackPluginConfig],

    
    node___README___: {
        text: `This is to account for what appears to be a bug:
                   https://github.com/josephsavona/valuable/issues/9`
    },
    node: {
        fs: "empty"
    }
};

module.exports = config;
