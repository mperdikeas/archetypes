/* @flow */
require('source-map-support').install();
import 'babel-polyfill';
import {assert} from 'chai';
import AssertionError  from 'assertion-error';
assert.isOk(AssertionError);
import _ from 'lodash';

import {arr2set, foo} from '../app/util.js';
import type {Function2} from '../app/util.js';


/* example code of how to use generic type definitions in Flow start */
type RelationFT<T> = (a: T, b: T) => boolean;

function someFunction(a: number) {
    return 42;
}
(someFunction: Function2)

function partialSorting<T>(_vs: Array<T>, isGreaterThan: RelationFT<T>): Array<T> {
    const n: number = _vs.length;
    const vs : Array<T> = _vs.slice();
    while (true) {
        let flipHappened = false;
        for (let i = 0; i < n ; i++) {
            if (isGreaterThan(vs[i], vs[i+1])) {
                const tmp = vs[i];
                vs[i]     = vs[i+1];
                vs[i+1]   = tmp;
                flipHappened = true;
            }
        }
        if (!flipHappened)
            break;
    }
    return vs;
}
/* example code of how to use generic type definitions in Flow end */
 
function partialSort(ss: Array<string>): Array<string> {
    return partialSorting(ss, (a, b)=>a.startsWith(b));
}

declare type TokenT = {v: string, isDelim: boolean};

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
        describe('test throw assertions', function() {
            it('works with my own throws', function() {
                assert.throws(()=>{
                    arr2set([1,1]);
                }, Error);
            });
            it('works with chai throws #1', function() {
                assert.throws(()=>{
                    foo();
                }, AssertionError);
            });
            it('works with chai throws #2', function() {
                assert.throws(()=>{
                    foo();
                }, Error);
            });
            it('weird', function() {
                assert.throws(()=>{
                    throw 1;
                }, undefined);
            });
        });

        describe('foo', function() { // https://stackoverflow.com/q/46457351/274677
            it('works', function() {
                assert.throws( ()=>{throw new Error('foo')}
                               , Error, /f../);
                assert.throws( ()=>{throw new AssertionError()}
                               , Error);
                assert.throws( ()=>{assert.isTrue(false, 'foobar');}
                               , Error, /.*bar/);
                assert.throws( ()=>{assert.isTrue(false, 'foobarzar');}
                               , AssertionError, /^foobarzar/);
            });
        });        
    });
});
