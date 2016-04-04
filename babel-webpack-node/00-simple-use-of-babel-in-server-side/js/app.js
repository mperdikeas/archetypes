'use strict';

const u = require('./utils.js');

function test(f) {
    const s = 'foobarzar';
    u.assert(f('foo')               ===s);
    u.assert(f('foo', 'bar')        ===s);
    u.assert(f('foo', 'bar', 'zar') ===s);
    u.assert(f('foo', 'bar2', 'zar')==='foobar2zar');
    u.assert(f('foo', 'bar', 'zar2')==='foobarzar2');
}

(function () {
    // pre-ECMAScript 6

    function f(p1, p2, p3) {
        p2 = (typeof p2 === 'undefined')?'bar':p2;
        p3 = (typeof p3 === 'undefined')?'zar':p3;
        return p1+p2+p3;
    }
    test(f);
})();

(function () {
    // ECMAScript 6
    function f(p1, p2='bar', p3='zar') {
        return p1+p2+p3;
    }
    test(f);
})();
