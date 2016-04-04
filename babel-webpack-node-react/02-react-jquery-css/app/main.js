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

    const Message = React.createClass({
        propTypes: {
            msg: React.PropTypes.string.isRequired
        },
        render: function render() {
            return <p className='para'>{this.props.msg}</p>;
        }
    });

    ReactDOM.render(<Message msg='hello from ReactJS using JavaScript'/>, $('#app')[0]);

}
