'use strict';

var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var _chai = require('chai');

var _ = require('lodash');


function isSetOf(setOfTs, T) {
    if (!(setOfTs instanceof Set)) return false;
    if (_.some(Array.from(setOfTs.entries()), function (_ref) {
        var _ref2 = _slicedToArray(_ref, 2),
            x = _ref2[0],
            _ = _ref2[1];

        return !(x instanceof T);
    })) return false;
    return true;
}
isSetOf.msg = function (item, klass) {
    return item + ' is not a set of ' + klass + ' values';
};

function arr2set(ts) {
    var rv = new Set(ts);
    var diff = ts.length - rv.size;
    _chai.assert.isTrue(diff >= 0);
    if (diff !== 0) throw new Error('at least ' + diff + ' duplicate elements detected');
    return rv;
}

function camelCase2Space(s) {
    return s.replace(/([A-Z])/g, ' $1') // insert a space before all caps
    .replace(/^./, function (str) {
        return str.toUpperCase();
    }); // uppercase the first character
}

function nameOfClass(klass) {
    console.log(klass.name);
    return klass.name;
}

function nameOfClassC2S(klass) {
    // C2S stands for Camel 2 Space
    return camelCase2Space(nameOfClass(klass));
}

exports.isSetOf = isSetOf;
exports.arr2set = arr2set;
exports.camelCase2Space = camelCase2Space;
exports.nameOfClass = nameOfClass;
exports.nameOfClassC2S = nameOfClassC2S;
//# sourceMappingURL=util.js.map