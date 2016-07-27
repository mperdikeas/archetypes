require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');

import App      from './app.js';
import AppState from './app-state.js';
import urls     from './urls.js';
import assert   from 'assert';

const artificialDelay = 1000;

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
    editPersonStart(idx) {
        this.setState({
            appState: AppState.MODIFYING_EXISTING_PERSON,
            personUnderEditIdx: idx
        });
    },    
    editPersonSave(idx, person) {
        assert.equal(this.state.appState, AppState.MODIFYING_EXISTING_PERSON);
        console.log(`on modifyPerson ${idx} called, person is: ${JSON.stringify(person)}`);
        this.setState({appState: AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB});
        const URL = urls.modifyPerson();
        this.modifyPersonRequest = $.ajax(URL,
                                          {
                                              type: 'PUT',
                                              data: JSON.stringify(person),
                                              contentType: 'application/json',
                                              success: (data, status)=>{
                                                  console.log(`PUT to ${URL} returned status [${status}] and the following data: [${JSON.stringify(data)}]`);
                                                  setTimeout( ()=> {
                                                      this.refreshPersonsFromDB();
                                                      this.setState({personUnderEditIdx: -1});
                                                  }, artificialDelay);
                                              },
                                              error: (xhr, status, errorThrown)=> {
                                                  window.alert(`PUT to ${URL} failed with status=[${status}] and errorThrown=[${JSON.stringify(errorThrown)}]`);
                                                  this.setState({appState: AppState.MODIFYING_EXISTING_PERSON});
                                              },
                                              complete: ()=>{
                                              }
                                          });
    },
    deletePerson(idx) {
        this.setState({appState: AppState.DISPLAY_LIST_DELETING_AT_DB});
        const URL = urls.deletePerson(idx);
        $.ajax(URL, {type: 'DELETE',
                                              success: (data, status)=>{
                                                  console.log(`DELETE to ${URL} returned status [${status}] and the following data: [${JSON.stringify(data)}]`);
                                                  setTimeout( ()=> {
                                                      this.refreshPersonsFromDB();
                                                      this.setState({personUnderEditIdx: -1});
                                                  }, artificialDelay);
                                              },
                                              error: (xhr, status, errorThrown)=> {
                                                  window.alert(`DELETE to ${URL} failed with status=[${status}] and errorThrown=[${JSON.stringify(errorThrown)}]`);
                                                  this.setState({appState: AppState.DISPLAY_LIST});
                                              },
                                              complete: ()=>{
                                              }
                    });
    },
    createNewPersonStart() {
        assert.equal(this.state.appState, AppState.DISPLAY_LIST);
        this.setState({appState: AppState.CREATING_NEW_PERSON});
    },
    createNewPersonSave(person) {
        assert(person);
        assert.equal(this.state.appState, AppState.CREATING_NEW_PERSON);
        this.setState({appState: AppState.CREATING_NEW_PERSON_WRITING_TO_DB});
        const URL = urls.createNewPerson();
        this.createNewPersonRequest = $.ajax(URL,
                                          {
                                              type: 'POST',
                                              data: JSON.stringify(person),
                                              contentType: 'application/json',
                                              success: (data, status)=>{
                                                  console.log(`Post to ${URL} returned status [${status}] and the following data: [${JSON.stringify(data)}]`);
                                                  setTimeout( ()=> {
                                                      this.refreshPersonsFromDB();
                                                      this.setState({personUnderEditIdx: -1});
                                                  }, artificialDelay);
                                              },
                                              error: (xhr, status, errorThrown)=> {
                                                  window.alert(`Post to ${URL} failed with xhr=[${JSON.stringify(xhr)}], status=[${status}] and errorThrown=[${JSON.stringify(errorThrown)}]`);
                                                  this.setState({appState: AppState.CREATING_NEW_PERSON});
                                              },
                                              complete: ()=>{
                                              }
                                          });
    },
    revertToDisplayList() {
        assert( (this.state.appState===AppState.MODIFYING_EXISTING_PERSON) ||
                (this.state.appState===AppState.CREATING_NEW_PERSON) );        
        this.setState({appState: AppState.DISPLAY_LIST});
    },
    render: function() {
        assert(this.state.appState.consistentWithPersons(this.state.persons));
        return (
                <App
                    appState={this.state.appState}
                    persons={this.state.persons}
                    personUnderEditIdx = {this.state.personUnderEditIdx}
                    editPersonStart={this.editPersonStart}
                    editPersonSave ={this.editPersonSave}
                    deletePerson = {this.deletePerson}
                    createNewPersonStart={this.createNewPersonStart}
                    createNewPersonSave={this.createNewPersonSave}
                    revertToDisplayList={this.revertToDisplayList}
                />
        );
    }
});

export default AppContainer;

