'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _reactabular = require('reactabular');

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

require('./css/style.css');
var _ = require('lodash');
var utils = require('./scripts/util.js');
var $ = require('jquery');
var React = require('react');
var cx = require('classnames');


var data = [{
    name: 'React.js',
    type: 'library',
    description: 'Awesome library for handling view.',
    followers: 23252,
    worksWithReactabular: true,
    id: 123
}, {
    name: 'Angular.js',
    type: 'framework',
    description: 'bloated; comes from Microsoft (yikes); really does a lot of stuff wrong.',
    followers: 35159,
    worksWithReactabular: false,
    id: 456
}, {
    name: 'Aurelia',
    type: 'framework',
    description: 'Framework for the next generation.',
    followers: 229,
    worksWithReactabular: false,
    id: 789
}];

var columns = [{
    property: 'name',
    header: 'Name',
    headerClass: cx({ 'name-column': true })
}, {
    property: 'type',
    header: 'Type'
}, {
    property: 'description',
    header: 'Description'
}, {
    property: 'followers',
    header: 'Followers',
    // accuracy per hundred is enough for demoing
    cell: function cell(followers) {
        return followers - followers % 100;
    }
}, {
    property: 'worksWithReactabular',
    header: '1st Class Reactabular',
    // render UTF ok if works
    cell: function cell(works) {
        return works && React.createElement(
            'span',
            null,
            '\u2713'
        );
    }
}];

var App = React.createClass({
    displayName: 'App',

    propTypes: {
        msg: React.PropTypes.string.isRequired
    },
    render: function render() {
        var _b$a$c = { b: 2, a: 1, c: 3 },
            a = _b$a$c.a,
            rest = _objectWithoutProperties(_b$a$c, ['a']);

        return React.createElement(
            'div',
            null,
            React.createElement(
                'div',
                null,
                'Rest property destructuring assignment works? ',
                React.createElement(
                    'b',
                    null,
                    JSON.stringify(rest) === JSON.stringify({ b: 2, c: 3 }) ? "yes" : "sadly no"
                )
            ),
            React.createElement(
                'h1',
                null,
                this.props.msg
            ),
            React.createElement(_reactabular.Table, { columns: columns, data: data })
        );
    }

});

// $SuppressFlowFinding: this is just to showcase the functionality
var a = 'anything but';

exports.default = App;
//# sourceMappingURL=app.js.map