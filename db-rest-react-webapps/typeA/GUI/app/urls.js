const BASE = "http://localhost:8080/db-rest-react-webapp-typeA-rest-webapp/jax-rs/es";

function listPersons() {
    return `${BASE}/listPersons`;
}

function modifyPerson() {
    return `${BASE}/modifyPerson`;
}


function createNewPerson() {
    return `${BASE}/createNewPerson`;
}


function deletePerson(i) {
    return `${BASE}/deletePerson/${i}`;
}



exports.listPersons     = listPersons;
exports.modifyPerson    = modifyPerson;
exports.createNewPerson = createNewPerson;
exports.deletePerson    = deletePerson;
