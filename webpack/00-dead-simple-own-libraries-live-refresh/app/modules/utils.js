var assert = function(condition, failMsg, successMsg) {
    if (!condition)
        throw new Error(failMsg || 'assertion error');
    else if (successMsg != undefined)
        console.log(successMsg);
};



if ((typeof module === 'object') && (typeof module.exports === 'object')) {
    exports.assert              = assert;
} else { // I must be in the browser
    (function (){
        if (typeof mjb44 === 'undefined') {
            mjb44 = {};
        };
        if (typeof mjb44 != 'object')
            throw new Error('clash on the mjb44 name: '+mjb44);
        var theLibrary;
        mjb44.createUtilsLibrary = function () {
            if (typeof theLibrary != 'object') {
                theLibrary = 
                    {
                        assert                  : assert
                    };
            }
            return theLibrary;
        };
    })();
}
