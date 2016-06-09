'use strict';

(function() {
    const sourceMapSupport = require('source-map-support');
    sourceMapSupport.install();
})();

import assert from 'assert';
import _ from 'lodash';
const u = require('./utils.js');


assert(true);
assert(_.isEqual([1,2,3], [1,2,3]));
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


(function(){
    // default parameter expressions
    let i = 0;
    function nextNumber() {
        return ++i;
    }
    function add(a, b=nextNumber()) {
        return a+b;
    }

    u.assertEqual([add(1,3), 4]);
    u.assertEqual([add(1), 2]);
    u.assertEqual([add(1), 3]);
    u.assertEqual([add(1), 4]);
    u.assertEqual([add(1), 5]);
    u.assertEqual([add(10,20), 30]);

})();


function *simplestIterator() {
    yield 42;
}
for (let v of simplestIterator())
    console.log(v);

{   // ensure that spread and rest operators work
    {   // spread for method invocations
        function sum(a,b,c) {
            return a+b+c;
        }

        const vs = [1,2,3];
        assert.equal(6, sum(...vs));

        const vs2 = [2,2,2,4,4,5,6];
        assert.equal(6, sum(...vs));
    }

    {   // destructuring without spread
        var {a, b} = {a: 'alpha', b: 'beta'};
        assert.equal(a, 'alpha');
        assert.equal(b, 'beta');
    }

    {   // rest elements for array destructuring assignment
        let [a, ...b ] = [1,2,3,4];
        assert.equal(a, 1);
        assert.deepEqual(b, [2,3,4]);
    }

    {    // spread elements for array literals
        let three2five = [3,4,5];
        let one2six = [1,2,...three2five, 6];
        assert.deepEqual(one2six, [1,2,3,4,5,6]);
    }

    {   // rest properties for object destructuring assignment
        // this requires the following two actions:
        // 1.  npm install babel-plugin-transform-object-rest-spread
        // 2.  add the following plugin in [.babelrc]:
        //       "plugins": ["transform-object-rest-spread"]
        let {a, ...b} = {a: 1, b: 2, c: 3, d: 4};
        assert.equal(a, 1);
        assert.deepEqual(b, {b: 2, c: 3, d: 4});
    }


    {   // this goes to show that order in rest parameter destructuring is important:
        let {...b, a} = {a: 1, b: 2, c: 3, d: 4};
        assert.deepEqual(b, {b: 2, c: 3, d: 4, a: 1}); // b gets assigned the [a] value too
        assert.equal(a, 1);
    }

    { // collected rest properties (...) can be spread out again
        const orig = {c: 3, d: 4, b: 2, a: 1, e: 5, f: 6};
        const {a, ...butA} = orig;
        assert.equal(a, 1);
        assert.deepEqual(butA, {b: 2, c: 3, d: 4, e: 5, f: 6});
        const resur = {a: a, ...butA};
        assert.deepEqual(orig, resur);
        assert.equal(orig===resur, false);
    }
}
