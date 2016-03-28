'use strict';
const path = require('path');

const config = {
    context: path.resolve(__dirname, 'app/'),
    entry: './main.js',
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel'
            },{
                test: /\.css$/,
                loaders: ['style', 'css']
            }
        ]
    }
};

module.exports = config;
