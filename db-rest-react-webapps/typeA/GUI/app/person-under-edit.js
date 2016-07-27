require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import EditForm from './edit-form.js';

const PersonUnderEdit = React.createClass({
    propTypes: {
        personIdx     : React.PropTypes.number.isRequired,
        getPerson     : React.PropTypes.func  .isRequired,
        editPersonSave: React.PropTypes.func  .isRequired,
        inTransit     : React.PropTypes.bool  .isRequired,
        cancel        : React.PropTypes.func  .isRequired
    },
    modifyPerson: function (person) {
        this.props.editPersonSave(this.props.personIdx, person);
    },
    render: function() {
        if (this.props.inTransit)
            return (<div> writing to database &hellip; </div>);
        else return (
            <EditForm
                person={this.props.getPerson(this.props.personIdx)}
                editPersonSave={this.modifyPerson}
                cancel={this.props.cancel}
            />
        );
    }
});

export default PersonUnderEdit;


