require('source-map-support').install();
import 'babel-polyfill';
const assert     = require('assert');
import _ from 'lodash';

import {Point, between, foo} from '../lib/index.js';

describe('Point', function () {
    describe('rotate90Right', function () {
        it('should work'
           , function () {
               const p = new Point(1,3);
               const expected = new Point(-3, 1);
               assert(p.rotate90Right().equals(expected));
               assert(_.isEqual(p.rotate90Right(), expected));
           });     
    });
    describe('rotate90Left', function () {
        it('should work'
           , function () {
               const p = new Point(1,3);
               const expected = new Point(3, -1);
               assert(p.rotate90Left().equals(expected));
               assert(_.isEqual(p.rotate90Left(), expected));
           });     
    });
    describe('rotate90Left and rotate90Right', function () {
        it('should cancel each other out', function () {
            const p = new Point(Math.random()*10-5, Math.random()*10-5);
            const p2 = p.rotate90Right().rotate90Left();
            const p3 = p.rotate90Left().rotate90Right();
            assert(p2.equals(p));
            assert(_.isEqual(p, p2));
            assert(p3.equals(p));
            assert(_.isEqual(p, p3));
        });
        describe ('four of the kind is back where we started', function() {
            it('rotate90Right', function() {
                const p = new Point(Math.random()*10-5, Math.random()*10-5);
                const p2 = p.rotate90Right().rotate90Right().rotate90Right().rotate90Right();
                assert(p2.equals(p));
                assert(_.isEqual(p, p2));
            });
            it('rotate90Left', function() {
                const p = new Point(Math.random()*10-5, Math.random()*10-5);
                const p2 = p.rotate90Left().rotate90Left().rotate90Left().rotate90Left();
                assert(p2.equals(p));
                assert(_.isEqual(p, p2));
            });            
        });
    });
});

if (false)
describe('between', function () {
    it('should work' , function () {
        assert(!between(-0.00000000001, 0, 1)); 
        assert( between(0,0,1));
        assert( between(0.9999,0,1));
        assert(!between(1,1,0,1));
        assert( between(2,1,3));
        assert(!between(2,3,1));
    });
    it('do stack traces work?' , function () { // apparently the stack traces are not correctly reported
        assert(false);
    });
    it('do stack traces work? (2)' , function () {
        (new Point(0,0)).equals(new Point(1,1));
    });
    it('do stack traces work? (3)' , function () {
        foo();
    });            
});
