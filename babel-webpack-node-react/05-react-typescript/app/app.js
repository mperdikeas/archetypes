require('./css/style.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
var      cx = require('classnames');

import PropTypes from 'prop-types';

const createReactClass = require('create-react-class');
import {theAnswer} from './util.js';


const App = createReactClass({
    propTypes: {
        msg: PropTypes.string.isRequired
    },
    render: function() {
        const {a, ...b} = {b: 2, a:1, c: 3};
        return (
            <div>
                <div>
                   {this.props.msg}
                </div>
                <div>
                    the value of the spread property is {JSON.stringify(b)}
                </div>
                <div>
                    The answer to everything is {theAnswer()}.
                </div>
            </div>
               );
    }
});


export default App;

