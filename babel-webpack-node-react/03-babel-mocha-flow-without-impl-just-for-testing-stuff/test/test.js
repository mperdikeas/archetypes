require('source-map-support').install();
import 'babel-polyfill';
const assert     = require('assert');
import _ from 'lodash';

class A {
    constructor(v) {
        this.v = v;
    }
}


describe('isEqual', function () {
    describe('on maps', function () {
        it('should work with values'
           , function () {
               const m1 = new Map();
               m1.set('alpha', 1);
               m1.set('beta', 2);
               const m2 = new Map();
               m2.set('beta', 2);
               m2.set('alpha', 1);
               assert(_.isEqual(m1, m2));
           });
        it('should work with pointers based on deep comparison'
           , function () {
               const m1 = new Map();
               m1.set('alpha', new A(1));
               m1.set('beta', new A(2));
               const m2 = new Map();
               m2.set('beta', new A(2));
               m2.set('alpha', new A(1));
               assert(_.isEqual(m1, m2));
           });
        it('should work with pointers based on deepest comparison'
           , function () {
               const m1 = new Map();
               m1.set('alpha', new A(new A(new A(new A(new A(new A(1)))))));
               m1.set('beta', new A(2));
               const m2 = new Map();
               m2.set('beta', new A(2));
               m2.set('alpha', new A(new A(new A(new A(new A(new A(1)))))));
               assert(_.isEqual(m1, m2));
           });                
    });
});
