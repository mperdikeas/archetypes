require('./css/style.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
var      cx = require('classnames');

import PropTypes from 'prop-types';

const createReactClass = require('create-react-class');



const App = createReactClass({
    propTypes: {
        msg: PropTypes.string.isRequired
    },
    render: function() {
        const {a, ...rest} = {b: 2, a:1, c: 3};
        return (
                <div>
                   {this.props.msg}
                </div>
               );
    }
});


export default App;

