'use strict';
// The rationale behind using this idiom is described in:
//     http://stackoverflow.com/a/36628148/274677
//

require('babel-polyfill'); // this is important as Babel only transforms syntax (e.g. arrow functions)
// so you need this in order to support new globals or (in my experience) well-known Symbols, e.g. the following:
//
//     console.log(Object[Symbol.hasInstance]);
//
// ... will print 'undefined' without the the babel-polyfill being required.
require('./app.js');