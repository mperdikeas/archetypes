class AppState {
    constructor(name) {this.name = name;}
    consistentWithPersons(persons) {
        if (  ( (this===AppState.DISPLAY_LIST) && !persons)
                   ||
                   ( (this===AppState.DISPLAY_LIST_READING_FROM_DB) && persons)
           ) return false;
        return true;
    }
    toString() {
        return this.name;
    }
}
AppState.DISPLAY_LIST                            = new AppState('DSPL');
AppState.DISPLAY_LIST_READING_FROM_DB            = new AppState('DSPL.R');
AppState.DISPLAY_LIST_DELETING_AT_DB            = new AppState('DSPL.D');
AppState.MODIFYING_EXISTING_PERSON               = new AppState('MDEP');
AppState.MODIFYING_EXISTING_PERSON_WRITING_TO_DB = new AppState('MDEP.W');
AppState.CREATING_NEW_PERSON                     = new AppState('CRNP');
AppState.CREATING_NEW_PERSON_WRITING_TO_DB       = new AppState('CRNP.W');


export default AppState;
