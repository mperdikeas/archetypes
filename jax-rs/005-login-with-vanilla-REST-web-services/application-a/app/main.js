require('./css/style.css');
const     _ = require('lodash');
const utils = require('./scripts/util.js');
const     $ = require('jquery');
const React = require('react');
/* this is only to show that since we are using the babel transpiler it is possible to use ECMAScript6 module style as well*/
import ReactDOM from 'react-dom';


$(document).ready(fireOff);

function fireOff() {
    utils.assert(6===_.range(0, 6).length, `problem`);
    console.log('libraries have apparently loaded OK');
    doStuff();
}

function doStuff() {
    console.log('in do stuff');
    $.getJSON('http://localhost:8080/jaxrs-example-cors/jax-rs/somePath/foo', function(data) {
            console.log(data);
            showFirstPage(data);
    });

}

function showFirstPage(msg) {

    const Message = React.createClass({
        propTypes: {
            msg: React.PropTypes.string.isRequired
        },
        render: function render() {
            return (
                    <div>The return value retrieved via Ajax (from a different host) was: <b>{this.props.msg}</b></div>
            );
        }
    });

    ReactDOM.render(<Message msg={JSON.stringify(msg)}/>, $('#app')[0]);

}
