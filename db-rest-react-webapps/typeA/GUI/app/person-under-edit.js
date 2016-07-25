require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

const PersonUnderEdit = React.createClass({
    propTypes: {
        personIdx: React.PropTypes.number.isRequired,
        getPerson: React.PropTypes.func.isRequired,
        modifyPerson: React.PropTypes.func.isRequired
    },
    getInitialState() {
        return {person: this.props.getPerson(this.props.personIdx)};
    },
    componentWillReceiveProps: function (nextProps) {
        assert(this.props.getPerson === nextProps.getPerson);
        assert(this.props.onSubmit  === nextProps.onSubmit);
        this.setState({person: this.props.getPerson(nextProps.personIdx)});
    },
    modifyPerson: function (se) {
        se.preventDefault();
        this.props.modifyPerson(this.props.personIdx, this.state.person);
    },
    onReset: function (se) {
        se.preventDefault();
        this.setState(this.getInitialState());
    },
    submitShouldAppearEnabled: function() {
        return true;
    },
    resetShouldAppearEnabled: function() {
        const initialPerson = this.getInitialState().person;
        return !_.isEqual(initialPerson, this.state.person);
    },
    onChangeFName: function (se) {
        const newPerson = Object.assign({}, this.state.person, {fname: se.target.value});
        this.setState({person: newPerson});
    },
    onChangeLName: function (se) {
        const newPerson = Object.assign({}, this.state.person, {lname: se.target.value});
        this.setState({person: newPerson});
    },
    onChangeComments: function (se) {
        const newPerson = Object.assign({}, this.state.person, {comments: se.target.value});
        this.setState({person: newPerson});
    },        
    render: function() {
         return (
                    <form onSubmit={this.modifyPerson} onReset={this.onReset} noValidate={true}>
                    <table>
                    <tbody>
                    <tr>
                        <td> <label htmlFor='form-fname'>First Name:</label> </td>
                    <td> <input id='form-fname'
                                type='text'
                                placeholder='first name'
                                value={this.state.person.fname}
                                onChange={this.onChangeFName}/> </td>
                    </tr>
                    <tr>
                        <td> <label htmlFor='form-lname'>Last Name:</label> </td>
                    <td> <input id='form-lname'
                                type='text'
                                placeholder='last name'
                                value={this.state.person.lname}
                                onChange={this.onChangeLName}/> </td>
                    </tr>
                    <tr>
                        <td> <label htmlFor='form-comments'>Comments:</label> </td>
                    <td> <textarea id='form-comments'
                                   placeholder='some comments about this person'
                                   value={this.state.person.comments}
                                   onChange={this.onChangeComments}
                          />
                    </td>
                    
                    </tr>
                    <tr>
                        <td> <input type='submit' disabled={!this.submitShouldAppearEnabled()}/> </td>
                        <td> <input type='reset'  disabled={!this.resetShouldAppearEnabled()}/> </td>
                    </tr>
                    </tbody>
                    </table>
                    </form>
        );
    }
});

export default PersonUnderEdit;


