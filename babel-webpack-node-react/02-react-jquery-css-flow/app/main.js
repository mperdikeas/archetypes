/* @flow */
const     _ = require('lodash');
const utils = require('./scripts/util.js');
const     $ = require('jquery');
import React    from 'react';
import ReactDOM from 'react-dom';
import App      from './app.js';

$(document).ready(doStuff);


function doStuff(): void {

    ReactDOM.render(<App msg={'some of the currently popular Javascript libraries / frameworks'}/>, $('#app')[0]);

}
