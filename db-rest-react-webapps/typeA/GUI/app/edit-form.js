require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import AppState        from './app-state.js';
import PersonList      from './person-list.js';
import PersonUnderEdit from './person-under-edit.js';


const EditForm = React.createClass({
    propTypes: {
        person        : React.PropTypes.object.isRequired,
        editPersonSave: React.PropTypes.func.isRequired,
        cancel        : React.PropTypes.func.isRequired
    },
    getInitialState() {
        return {
            initialPerson: _.cloneDeep(this.props.person),
            person: _.cloneDeep(this.props.person)
        };
    },
    onReset: function (se) {
        se.preventDefault();
        this.setState(this.getInitialState());
    },   
    editPersonSave: function (se) {
        se.preventDefault();
        this.props.editPersonSave(this.state.person);
    },    
    dataHasChanged: function() {
        return !_.isEqual(this.state.initialPerson, this.state.person);
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
                <form onSubmit={this.editPersonSave} onReset={this.onReset} noValidate={true}>
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
                    <td> <input type='submit' disabled={!this.dataHasChanged()}/> </td>
                    <td> <input type='reset'  disabled={!this.dataHasChanged()}/> </td>
                    <td> <button type='button' onClick={this.props.cancel}>cancel</button> </td>
                </tr>
                </tbody>
                </table>
        </form>
        );
    }
});

export default EditForm;


