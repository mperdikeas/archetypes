require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');

import App from './app.js';


const AppContainer = React.createClass({
    propTypes: {
    },
    getInitialState: function() {
        return {};
    },
    componentDidMount: function() {
        const URL = 'http://localhost:8080/db-rest-react-webapp-typeA-rest-webapp/jax-rs/oaipmh/listPersons';
        const artificialDelay = 2000;
        this.serverRequest = $.getJSON(URL, function (result) {
            setTimeout( ()=> {
                this.setState({persons: result.people});
            }, artificialDelay);
        }.bind(this));
    },
    componentWillUnmount: function() {
        this.serverRequest.abort();
    },    
    render: function() {
        return (
                <App persons={this.state.persons}/>
        );
    }
});

export default AppContainer;

