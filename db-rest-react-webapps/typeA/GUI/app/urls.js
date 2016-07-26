const BASE = "http://localhost:8080/db-rest-react-webapp-typeA-rest-webapp/jax-rs/es";

function listPersons() {
    return `${BASE}/listPersons`;
}

function modifyPerson() {
    return `${BASE}/modifyPerson`;
}



exports.listPersons  = listPersons;
exports.modifyPerson = modifyPerson;
