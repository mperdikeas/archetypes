function theAnswer() {
    if (1==1) throw new Error();
    return 42;
}

exports.theAnswer = theAnswer;
