'use strict';
const path = require('path');

const APPDIR = 'app/';

const config = {
    context: path.resolve(__dirname, APPDIR),
    entry: './main.js',
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
    devServer: {
        headers: { "Access-Control-Allow-Origin": "*" }
    }
};

module.exports = config;
