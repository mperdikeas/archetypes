/* @flow */
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
/* this is only to show that since we are using the babel transpiler it is possible to use ECMAScript6 module style as well*/
import ReactDOM from 'react-dom';

const LOGIN_URL = "http://127.0.0.1:8080/jaxrs-example-cors/jax-rs/somePath/login";
const LOGOUT_URL = "http://127.0.0.1:8080/jaxrs-example-cors/jax-rs/somePath/logout";

const SyntheticEvent = require('react/lib/SyntheticEvent');

const App = React.createClass({
    propTypes: {
        //        msg: React.PropTypes.string.isRequired
    },
    getInitialState: function() {
        return {
            loggedIn: false,
            alreadyLoggedInFlag: false,
            logoutFailure: false,
            username: '',
            password: ''
        };
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
                data: data
            })
            .done(this.loginResponse)
            .fail(this.loginResponseFail);
    },
    logoutPost: function(ev: SyntheticEvent) {
        ev.preventDefault();
        const data = {  username: this.state.username };

        $.ajax({
            type: 'post',
            url: LOGOUT_URL,
            crossDomain: true,
            dataType: 'text',
            xhrFields: {
                withCredentials: true
            },
            data: data})
            .done(this.logoutResponse)
            .fail(this.logoutResponseFail)
    },    
    loginResponse: function(rvJSON: string, status: string, jqXHR: any) {
        console.log(`loginResponse method called with status: ${status} and returned data: ${rvJSON}`);
        const rv = JSON.parse(rvJSON);
        if (rv.data === 'ok')
            this.setState({loggedIn: true, alreadyLoggedInFlag: false, logoutFailure: false});
        else if ((rv.data === 'alrlc') || (rv.data === 'alrle'))
            this.setState({loggedIn: true, alreadyLoggedInFlag: true, logoutFailure: false});
    },
    loginResponseFail: function(jqXHR: any, status: string, errorThrown: any) {
        console.log(`loginResponseFail method called with status: ${status} and error: ${errorThrown}`);
        throw new Error();
    },
    logoutResponse: function(rvJSON: string, status: string, jqXHR: any) {
        console.log(`logoutResponse method called with status: ${status} and returned data: ${rvJSON}`);
        const rv = JSON.parse(rvJSON);
        if (rv.data === 'ok')
            this.setState({loggedIn: false, alreadyLoggedInFlag: false, logoutFailure: false});
        else if (rv.data === 'fail-not-logged-in')
            this.setState({loggedIn: true, alreadyLoggedInFlag: true, logoutFailure: true});
    },
    logoutResponseFail: function(jqXHR: any, status: string, errorThrown: any) {
        console.log(`logoutResponseFail method called with status: ${status} and error: ${errorThrown}`);
        throw new Error();
    },                                  
    backToLoginScreen: function() {
        this.setState({loggedIn: false});
    },
    render: function() {
        if (!this.state.loggedIn) {
            return (
                    <div>
                    <form onSubmit={this.loginPost}>
                    <input type='text' value={this.state.username} onChange={this.updateUsername}/><br/>
                    <input type='text' value={this.state.password} onChange={this.updatePassword}/><br/>
                    <input type='submit' value='login'/>
                    </form>
                    </div>
            );
        } else {
            const header = (()=>{
                if (this.state.alreadyLoggedInFlag)
                    return (
                            <h3>user is already logged-in</h3>
                    );
                else
                    return (
                            <h3>successful login</h3>                        
                    );
            })();
            const footer = (()=>{
                if (this.state.logoutFailure)
                    return (
                        <h4>some weird logout failure detected </h4>
                    );
                else return null;
            })();
            return (
                    <div>
                    {header}
                private data &hellip;
                    <input type='button' value='back to login screen' onClick={this.backToLoginScreen}/>
                    <input type='button' value='logout' onClick={this.logoutPost}/>
                    {footer}
                    </div>
            );
        }
    }


});

// $SuppressFlowFinding: this is just to showcase the functionality
const a : number = 'anything but';


export default App;
