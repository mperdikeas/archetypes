require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import EditForm from './edit-form.js';

const PersonUnderEdit = React.createClass({
    propTypes: {
        personIdx   : React.PropTypes.number.isRequired,
        getPerson   : React.PropTypes.func  .isRequired,
        modifyPerson: React.PropTypes.func  .isRequired,
        inTransit   : React.PropTypes.bool  .isRequired
    },
    /*
    getInitialState() {
        return {person: this.props.getPerson(this.props.personIdx)};
    },

    componentWillReceiveProps: function (nextProps) {
        assert(this.props.getPerson === nextProps.getPerson);
        assert(this.props.onSubmit  === nextProps.onSubmit);
        this.setState({person: this.props.getPerson(nextProps.personIdx)});
    },*/
    modifyPerson: function (person) {
        this.props.modifyPerson(this.props.personIdx, person);
    },
    render: function() {
        return (
                <EditForm
                    person={this.props.getPerson(this.props.personIdx)}
                    modifyPerson={this.modifyPerson}
                />
        );
    }
});

export default PersonUnderEdit;


