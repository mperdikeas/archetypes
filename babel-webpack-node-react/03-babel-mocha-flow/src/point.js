// @flow
'use strict';

// The rationale behind using this idiom is described in:
//     http://stackoverflow.com/a/36628148/274677
//
require('babel-polyfill'); // this is important as Babel only transforms syntax (e.g. arrow functions)
// so you need this in order to support new globals or (in my experience) well-known Symbols, e.g. the following:
//
//     console.log(Object[Symbol.hasInstance]);
//
// ... will print 'undefined' without the the babel-polyfill being required.

(function() {
    const sourceMapSupport = require('source-map-support');
    sourceMapSupport.install();
})();

import assert from 'assert';
import _ from 'lodash';

class Point {
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }
    // $SuppressFlowFinding: obvious mistyping just to demonstrate use of line-specific suppression
    rotate90Right(): boolean {
        return new Point(-this.y, this.x);
    }
    rotate90Left(): Point {
        return new Point(this.y, -this.x);
    }
    equals(p2: Point) {
        return this.x===p2.x && this.y===p2.y;
    }
    add(otherPoint: Point): Point {
        assert(otherPoint instanceof Point);
        return new Point(this.x+otherPoint.x, this.y+otherPoint.y);
    }
    clone(): Point {
        return new Point(this.x, this.y);
    }
}

function between(x: number,a: number,b: number): boolean {
    return (x>=a) && (x<b);
}


exports.Point = Point;
exports.between = between;

