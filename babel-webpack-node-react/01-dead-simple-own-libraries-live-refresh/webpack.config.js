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
    entry: './main.js',
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'bundle.js'
    },
    plugins: [HTMLWebpackPluginConfig]
};

module.exports = config;
