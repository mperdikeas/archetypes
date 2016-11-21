/* @flow */
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
/* this is only to show that since we are using the babel transpiler it is possible to use ECMAScript6 module style as well*/
import ReactDOM from 'react-dom';

const LOGIN_URL = "http://127.0.0.1:8080/jaxrs-example-cors/jax-rs/somePath/login";

const SyntheticEvent = require('react/lib/SyntheticEvent');

const App = React.createClass({
    propTypes: {
//        msg: React.PropTypes.string.isRequired
    },
    getInitialState: function() {
        return {username: '', password: ''};
    },
    updateUsername: function (ev: SyntheticEvent) {
        console.log('x');
        this.setState({username: ev.target.value});
    },
    updatePassword: function(ev: SyntheticEvent) {
        console.log('x');        
        this.setState({password: ev.target.value});
    },
    loginPost: function(ev: SyntheticEvent) {
        ev.preventDefault();
        const data = {  username: this.state.username
                        , password: this.state.password};

        // see: http://stackoverflow.com/a/24689738/274677
        const COOKIES_ARE_BY_DEFAULT_NOT_INCLUDED_IN_CORS_REQUESTS = false;
        if (COOKIES_ARE_BY_DEFAULT_NOT_INCLUDED_IN_CORS_REQUESTS)
            $.post( LOGIN_URL, data, this.loginResponse);
        else
            $.ajax({
                type: 'post',
                url: LOGIN_URL,
                crossDomain: true,
                dataType: 'text',
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    username : this.state.username,
                    password : this.state.password
                },
                success: this.loginResponse
            });
    },
    loginResponse: function(returnedData: any, status: string) {
        console.log(`loginResponse method called with status: ${status} and returned data: ${returnedData}`);
    },
    render: function() {
        return (
            <div>
                <form onSubmit={this.loginPost}>
                    <input type='text' value={this.state.username} onChange={this.updateUsername}/><br/>
                    <input type='text' value={this.state.password} onChange={this.updatePassword}/><br/>
                    <input type='submit' value='login'/>
                </form>
            </div>
        );
    }


});

// $SuppressFlowFinding: this is just to showcase the functionality
const a : number = 'anything but';


export default App;

/*
$(document).ready(fireOff);

function fireOff() {
    utils.assert(6===_.range(0, 6).length, `problem`);
    console.log('libraries have apparently loaded OK');
    doStuff();
}

function doStuff() {
    console.log('in do stuff');
    $.getJSON('http://localhost:8080/jaxrs-example-cors/jax-rs/somePath/foo', function(data) {
            console.log(data);
            showFirstPage(data);
    });

}

function showFirstPage(msg) {

    const Message = React.createClass({
        propTypes: {
            msg: React.PropTypes.string.isRequired
        },
        render: function render() {
            return (
                    <div>The return value retrieved via Ajax (from a different host) was: <b>{this.props.msg}</b></div>
            );
        }
    });

    ReactDOM.render(<Message msg={JSON.stringify(msg)}/>, $('#app')[0]);

}
*/
