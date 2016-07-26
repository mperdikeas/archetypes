require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');

import App      from './app.js';
import AppState from './app-state.js';
import urls     from './urls.js';
import assert   from 'assert';

const AppContainer = React.createClass({
    propTypes: {
    },
    getInitialState: function() {
        return {
                appState: AppState.DISPLAY_LIST_READING_FROM_DB,
                personUnderEditIdx: -1
               };
    },
    componentDidMount: function() {
        this.refreshPersonsFromDB();
    },
    refreshPersonsFromDB() {
        this.setState({appState: AppState.DISPLAY_LIST_READING_FROM_DB,
                       persons: null});
        const URL = urls.listPersons();
        const artificialDelay = 2000;
        this.serverRequest = $.getJSON(URL, function (result) {
            setTimeout( ()=> {
                console.log(`seting state to DISPLAY LIST with ${result.people.length} people`);
                this.setState({
                    appState: AppState.DISPLAY_LIST,
                    persons: result.people
                });
            }, artificialDelay);
        }.bind(this));
    },
    componentWillUnmount: function() {
        if (this.serverRequest)       this.serverRequest.abort();
        if (this.modifyPersonRequest) this.modifyPersonRequest.abort();
    },
    editPerson(idx) {
        this.setState({
            appState: AppState.MODIFYING_EXISTING_PERSON,
            personUnderEditIdx: idx
        });
    },    
    modifyPerson(idx, person) {
        assert.equal(this.state.appState, AppState.MODIFYING_EXISTING_PERSON);
        console.log(`on modifyPerson ${idx} called, person is: ${JSON.stringify(person)}`);
        this.setState({appState: AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB});
        const URL = urls.modifyPerson();
        const artificialDelay = 2000;
        this.modifyPersonRequest = $.ajax(URL,
                                          {
                                           type: 'POST',
                                           data: JSON.stringify(person),
                                           contentType: 'application/json',
                                           success: ()=>{
                                               setTimeout( ()=> {
                                                    this.refreshPersonsFromDB();
                                                    this.setState({personUnderEditIdx: -1});
                                                }, artificialDelay);
                                           }
                                          });
    },
    createNewPerson() {
        assert.equal(this.state.appState, AppState.DISPLAY_LIST);
        this.setState({
            appState: AppState.CREATING_NEW_PERSON
        });
    },
    render: function() {
        assert(this.state.appState.consistentWithPersons(this.state.persons));
        return (
                <App
                    appState={this.state.appState}
                    persons={this.state.persons}
                    personUnderEditIdx = {this.state.personUnderEditIdx}
                    editPerson={this.editPerson}
                    modifyPerson={this.modifyPerson}
                    createNewPerson={this.createNewPerson}
                />
        );
    }
});

export default AppContainer;

