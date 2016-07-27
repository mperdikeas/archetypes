require('./app.css');
const     _ = require('lodash');
const     $ = require('jquery');
const React = require('react');
var      cx = require('classnames');
import assert from 'assert';

import {Table, Column, Cell} from 'fixed-data-table';
require('fixed-data-table/dist/fixed-data-table.min.css');

const PersonList = React.createClass({
    propTypes: {
        persons         :  React.PropTypes.array,
        editPersonStart :  React.PropTypes.func.isRequired,
        deletePerson    :  React.PropTypes.func.isRequired,
        inTransit       :  React.PropTypes.bool.isRequired,
        editable        :  React.PropTypes.bool.isRequired
    },
    render: function() {
        let persons;
        if (this.props.inTransit) {
            assert.equal(this.persons, null);
            persons = (
                <div>
                   data being fetched from server or written to server &hellip;
                </div>
            );
        } else {
            console.log('PersonList is not in transit state');
            assert(this.props.persons);
            persons = (
                    <Table
                        width={400}
                        height={200}
                        rowsCount={this.props.persons.length}
                        rowHeight={30}
                        headerHeight={30}>
                        <Column
                                width={20}
                                flexGrow={0}
                                header={'#'}
                                cell={ ({rowIndex})=>(
                                        <Cell>{this.props.persons[rowIndex].i}</Cell>
                                )}
                            />
                        <Column
                                width={50}
                                flexGrow={1}                
                                header={'first name'}
                                cell={ ({rowIndex})=>(<Cell>
                                                      {this.props.persons[rowIndex].fname}
                                                      </Cell>
                                )}
                                width={30}
                        />
                        <Column
                                width={50}
                                flexGrow={1}                
                                header={'last name'}
                                cell={ ({rowIndex})=>(<Cell>
                                                      {this.props.persons[rowIndex].lname}
                                                      </Cell>
                                )}
                                width={30}
                    />
                        <Column
                                width={80}
                                flexGrow={0.8}                
                                header={'birth'}
                                cell={ ({rowIndex})=>(<Cell>
                                                      {this.props.persons[rowIndex].yearOfBirth}
                                                      </Cell>
                                )}
                                width={30}
                    />
                        <Column
                                width={30}
                                flexGrow={0.4}                
                                header={'age'}
                               cell={ ({rowIndex})=> {
                                   const currYear = (new Date()).getFullYear();
                                   return (<Cell>{currYear-parseInt(this.props.persons[rowIndex].yearOfBirth)}</Cell>);
                               }}
                                width={30}
                    />
                        <Column
                                width={30}
                                flexGrow={0.4}                
                                header={''}
                               cell={ ({rowIndex})=> {
                                   return (<Cell>
                                           <button
                                               type='button'
                                               disabled={!this.props.editable}
                                               onClick={()=>{this.props.editPersonStart(rowIndex);}}
                                           >
                                           edit
                                           </button>
                                           </Cell>
                                          );
                               }}
                                width={30}
                    />
                        <Column
                                width={30}
                                flexGrow={0.4}                
                                header={''}
                               cell={ ({rowIndex})=> {
                                   return (<Cell>
                                           <button
                                               type='button'
                                               disabled={!this.props.editable}
                                               onClick={()=>{this.props.deletePerson(this.props.persons[rowIndex].i);}}
                                           >
                                           delete
                                           </button>
                                           </Cell>
                                          );
                               }}
                                width={30}
                    />
                    </Table>
            );
        }
        return (<div>
                <h1>list of persons</h1>
                {persons}
                </div>
               );
    }
});

export default PersonList;

