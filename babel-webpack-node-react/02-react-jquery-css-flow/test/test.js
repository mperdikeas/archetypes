/* @flow */
require('source-map-support').install();
import 'babel-polyfill';
const assert     = require('chai').assert;
import _ from 'lodash';


describe('generic exploratory', function () {
    describe('chai', function() {
        describe('isString', function() {
            it('works with both primitives and String', function() {
                assert.isString('s');
                assert.isString(new String('foo'));
            });
        });
        describe('isBoolean', function() {
            it('works with both primitives and Boolean', function() {
                assert.isBoolean(true);
                assert.isBoolean(new Boolean(false));
            });
        });
        describe('isNumber', function() {
            it('works with both primitives and Number and infinities too', function() {
                assert.isNumber(3);
                assert.isNumber(Math.PI);
                assert.isNumber(new Number(3));
                assert.isNumber(Infinity);
                assert.isNumber(-Infinity);
            });
        });
    });
});
