require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');

import PersonList      from './person-list.js';
import PersonUnderEdit from './person-under-edit.js';

class AppState {
    constructor() {}
}
AppState.DISPLAY_LIST              = new AppState();
AppState.MODIFYING_EXISTING_PERSON = new AppState();
AppState.CREATING_NEW_PERSON       = new AppState();

const App = React.createClass({
    propTypes: {
        persons: React.PropTypes.array
    },
    getInitialState: function() {
        return {
            appState: AppState.DISPLAY_LIST,
            personUnderEditIdx: -1
        };
    },
    getPerson(idx) {
        return _.cloneDeep(this.props.persons[idx]);
    },
    editPerson(idx) {
        this.setState({personUnderEditIdx: idx});
    },
    modifyPerson(idx, person) {
        console.log(`on modifyPerson ${idx} called, person is: ${JSON.stringify(person)}`);
    },
    render: function() {
        console.log(`App#render`);
        let personUnderEdit;
        console.log(`this.state.personUnderEditIdx=${this.state.personUnderEditIdx}`);
        if ((this.state.personUnderEditIdx!=-1) && (this.props.persons!=null))
            personUnderEdit=(<PersonUnderEdit
                             id={this.state.personUnderEditIdx}
                             personIdx={this.state.personUnderEditIdx}
                             getPerson={this.getPerson}
                             modifyPerson={this.modifyPerson}
                             />);
        else
            personUnderEdit = null;
        return (
            <div>
                <PersonList
                    persons={this.props.persons}
                    editPerson={this.editPerson}
                />
                {personUnderEdit}
            </div>
        );
    }
});

export default App;


