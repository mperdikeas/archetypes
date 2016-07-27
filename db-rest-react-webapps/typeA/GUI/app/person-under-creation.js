require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import EditForm from './edit-form.js';

const PersonUnderCreation = React.createClass({
    propTypes: {
        createNewPersonSave: React.PropTypes.func  .isRequired,
        inTransit          : React.PropTypes.bool  .isRequired,
        cancel             : React.PropTypes.func  .isRequired        
    },
    render: function() {
        if (this.props.inTransit) {
            return (<div> writing to database &hellip; </div>);
        } return (
            <EditForm
                person={ {fname:"",lname:"",comments:"", yearOfBirth:1980} }
                editPersonSave={this.props.createNewPersonSave}
                cancel={this.props.cancel}            
            />
        );
    }
});

export default PersonUnderCreation;


