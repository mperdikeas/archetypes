const path = require('path');

module.exports = {
    entry: path.resolve(__dirname, 'js/app.js'),
    output: {
        path: path.resolve(__dirname, 'build/'),
        filename: 'bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'babel-loader'
            }
        ]
    }
};
