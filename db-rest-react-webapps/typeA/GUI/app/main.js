const     _ = require('lodash');
const     $ = require('jquery');
import React        from 'react';
import ReactDOM     from 'react-dom';
import AppContainer from './app-container.js';

$(document).ready(doStuff);


function doStuff() {

    ReactDOM.render(<AppContainer/>, $('#app')[0]);

}
