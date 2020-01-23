require('./css/style.css');
const     _ = require('lodash');
const utils = require('./util.js');
const     $ = require('jquery');
const React = require('react');
var      cx = require('classnames');
import {Table} from 'reactabular-table';

var data = [
    {
        name: 'React.js',
        type: 'library',
        description: 'Awesome library for handling view.',
        followers: 23252,
        worksWithReactabular: true,
        id: 123
    },
    {
        name: 'Angular.js',
        type: 'framework',
        description: 'bloated; comes from Microsoft (yikes); really does a lot of stuff wrong.',
        followers: 35159,
        worksWithReactabular: false,
        id: 456
    },
    {
        name: 'Aurelia',
        type: 'framework',
        description: 'Framework for the next generation.',
        followers: 229,
        worksWithReactabular: false,
        id: 789
    },
];



var columns = [
    {
        property: 'name',
        header: 'Name',
        headerClass: cx({'name-column': true})
    },
    {
        property: 'type',
        header: 'Type'
    },
    {
        property: 'description',
        header: 'Description'
    },
    {
        property: 'followers',
        header: 'Followers',
        // accuracy per hundred is enough for demoing
        cell: (followers) => followers - (followers % 100)
    },
    {
        property: 'worksWithReactabular',
        header: '1st Class Reactabular',
        // render UTF ok if works
        cell: (works) => works && <span>&#10003;</span>
    }
];



const App = React.createClass({
    propTypes: {
        msg: React.PropTypes.string.isRequired
    },
    render: function() {
        const {a, ...rest} = {b: 2, a:1, c: 3};
        return (
            <div>
                <div>
                Rest property destructuring assignment works? <b>{JSON.stringify(rest)===JSON.stringify({b:2, c:3})
                                                               ?"yes"
                                                               :"sadly no"}</b>
                </div>
                <h1>{this.props.msg}</h1>
                <Table columns={columns} data={data}/>
            </div>
        );
    }


});


export default App;

