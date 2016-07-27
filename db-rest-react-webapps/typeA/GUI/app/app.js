require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import AppState                from './app-state.js';
import PersonList              from './person-list.js';
import PersonUnderEdit         from './person-under-edit.js';
import PersonUnderCreation     from './person-under-creation.js';


const App = React.createClass({
    propTypes: {
        appState             : React.PropTypes.object.isRequired,
        persons              : React.PropTypes.array,
        personUnderEditIdx   : React.PropTypes.number,
        editPersonStart      : React.PropTypes.func.isRequired,
        editPersonSave       : React.PropTypes.func.isRequired,
        deletePerson         : React.PropTypes.func.isRequired,
        createNewPersonStart : React.PropTypes.func.isRequired,
        createNewPersonSave  : React.PropTypes.func.isRequired,
        revertToDisplayList  : React.PropTypes.func.isRequired
    },
    getPerson(idx) {
        return _.cloneDeep(this.props.persons[idx]);
    },
    render: function() {
        console.log(`App#render`);
        assert(this.props.appState.consistentWithPersons(this.props.persons));
        switch (this.props.appState) {
        case AppState.DISPLAY_LIST:
        case AppState.DISPLAY_LIST_READING_FROM_DB:
        case AppState.DISPLAY_LIST_DELETING_AT_DB:            // intentional fall-through            
            return this.renderAtStateDisplayList();
        case AppState.MODIFYING_EXISTING_PERSON:              // intentional fall-through
        case AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB:
            return this.renderAtStateModifyingExistingPerson();
        case AppState.CREATING_NEW_PERSON:                    // intentional fall-through
        case AppState.CREATING_NEW_PERSON_WRITING_TO_DB:
            return this.renderAtStateCreatingNewPerson();
        default:
            throw new Error(`unhandled case ${this.props.appState}`);
        }
    }
    , renderAtStateDisplayList() {
        assert( (this.props.appState===AppState.DISPLAY_LIST)                 ||
                (this.props.appState===AppState.DISPLAY_LIST_READING_FROM_DB) ||
                (this.props.appState===AppState.DISPLAY_LIST_DELETING_AT_DB)  );
        let createNewButton;
        if (this.props.appState === AppState.DISPLAY_LIST)
            createNewButton=(
                <button type='button' onClick={this.props.createNewPersonStart}>create new</button>
            );
        return (<div>
                <PersonList
                    persons={this.props.persons}
                    editPersonStart={this.props.editPersonStart}
                    deletePerson={this.props.deletePerson}
                    inTransit={_.includes([AppState.DISPLAY_LIST_READING_FROM_DB, AppState.DISPLAY_LIST_DELETING_AT_DB], this.props.appState)}
                    editable={true}
                />
                {createNewButton}
                </div>
               );
    }
    , renderAtStateModifyingExistingPerson() {
        assert(this.props.personUnderEditIdx != -1);
        return (
            <div>
                <PersonList
                    persons={this.props.persons}
                    editPersonStart={this.props.editPersonStart}
                    inTransit={false}
                    editable={false}
                />
            <PersonUnderEdit
                             id={this.props.personUnderEditIdx}
                             personIdx={this.props.personUnderEditIdx}
                             getPerson={this.getPerson}
                             editPersonSave={this.props.editPersonSave}
                             inTransit={this.props.appState===AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB}
                             cancel={this.props.revertToDisplayList}
                />
                </div>
        );
    }
    , renderAtStateCreatingNewPerson() {
        return (
            <PersonUnderCreation
                 createNewPersonSave={this.props.createNewPersonSave}
                inTransit={this.props.appState===AppState.CREATING_NEW_PERSON_WRITING_TO_DB}
                cancel={this.props.revertToDisplayList}
            />
        );
    }
});

export default App;


