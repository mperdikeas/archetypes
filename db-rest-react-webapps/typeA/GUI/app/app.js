require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
import assert from 'assert';

import AppState        from './app-state.js';
import PersonList      from './person-list.js';
import PersonUnderEdit from './person-under-edit.js';


const App = React.createClass({
    propTypes: {
        appState: React.PropTypes.object.isRequired,
        persons: React.PropTypes.array,
        personUnderEditIdx: React.PropTypes.number,
        editPerson: React.PropTypes.func.isRequired,
        modifyPerson: React.PropTypes.func.isRequired,
        createNewPerson: React.PropTypes.func.isRequired
    },
    getPerson(idx) {
        return _.cloneDeep(this.props.persons[idx]);
    },
    render: function() {
        console.log(`App#render`);
        assert(this.props.appState.consistentWithPersons(this.props.persons));
        switch (this.props.appState) {
        case AppState.DISPLAY_LIST:
        case AppState.DISPLAY_LIST_READING_FROM_DB:           // fall-through
            return this.renderAtStateDisplayList();
        case AppState.MODIFYING_EXISTING_PERSON:              // fall-through
        case AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB:
            return this.renderAtStateModifyingExistingPerson();
        case AppState.CREATING_NEW_PERSON:
            return this.renderAtStateCreatingNewPerson();
        default:
            throw new Error(`unhandled case ${this.props.appState}`);
        }
    }
    , renderAtStateDisplayList() {
        assert( (this.props.appState===AppState.DISPLAY_LIST)                 ||
                (this.props.appState===AppState.DISPLAY_LIST_READING_FROM_DB) );
        let createNewButton;
        if (this.props.appState === AppState.DISPLAY_LIST)
            createNewButton=(
                <button type='button' onClick={this.props.createNewPerson}>create new</button>
            );
        return (<div>
                <PersonList
                    persons={this.props.persons}
                    editPerson={this.props.editPerson}
                    inTransit={this.props.appState===AppState.DISPLAY_LIST_READING_FROM_DB}
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
                    editPerson={this.props.editPerson}
                    inTransit={false}
                    editable={false}
                />
            <PersonUnderEdit
                             id={this.props.personUnderEditIdx}
                             personIdx={this.props.personUnderEditIdx}
                             getPerson={this.getPerson}
                             modifyPerson={this.props.modifyPerson}
                             inTransit={this.props.appState===AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB}
                />
                </div>
        );
    }
    , renderAtStateCreatingNewPerson() {
        throw new Error('not implemented yet');
    }
});

export default App;


