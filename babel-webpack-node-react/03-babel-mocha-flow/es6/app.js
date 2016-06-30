// @flow
'use strict';

(function() {
    const sourceMapSupport = require('source-map-support');
    sourceMapSupport.install();
})();

import assert from 'assert';
import _ from 'lodash';
const u = require('./utils.js');

class Point {
    x: number;
    y: number;
    constructor(x: number, y: number) {
        this.x = x;
        this.y = y;
    }
    rotate90Right() {
        return new Point(-this.y, this.x);
    }
    rotate90Left() {
        return new Point(this.y, -this.x);
    }
    equals(p2: Point) {
        return this.x===p2.x && this.y===p2.y;
    }
    moveRight() {
        return new Point(this.x+1, this.y);
    }
    moveLeft() {
        return new Point(this.x-1, this.y);
    }
    lower() {
        return new Point(this.x, this.y+1);
    }
    add(otherPoint: Point) {
        assert(otherPoint instanceof Point);
        return new Point(this.x+otherPoint.x, this.y+otherPoint.y);
    }
}

function between(x,a,b) {
    return (x>=a) && (x<b);
}

function notBetween(x,a,b) {
    return !between(x,a,b);
}

const MAX_BRICK_HEIGHT = 3;


class Grid {
    occupied: Array<Array<number>>;
    x: number;
    y: number;
    constructor(occupied: Array<Array<number>>) {
        assert(arguments.length===1);
        assert(Array.isArray(occupied));
        assert ( Object.keys(_.groupBy(occupied.map(x => x.length))).length == 1 );
        assert (_.every([].concat.apply([], occupied), (x)=>x===null || ((typeof x)===(typeof 0))));
        this.x = occupied.length;
        this.y = occupied[0].length;
        this.occupied = occupied;
    }

    fits(i: number, j: number, brick: Brick) {
        assert( (i>=0) && (i<this.x) && (j>=0) && (j<this.y));
        const rv = [];
        const offendingValues = _.filter(brick.points, ({x,y})=>{
            return notBetween(i+x, 0, this.x) || notBetween(j+y, -MAX_BRICK_HEIGHT, this.y) || (this.occupied[i+x][j+y]!=null);});
        return (offendingValues.length === 0);
    }
    isStuck(i: number, j: number, brick: Brick) {
        return !this.fits(i, j, brick.lower());
    }
}

class Brick {
    points: Array<Point>;
    v: number;
    static rotate90Right = function () {return new Brick(this.points.map( x => x.rotate90Right()), this.v); };
    static rotate90Left = function () {return new Brick(this.points.map( x => x.rotate90Left()), this.v); };

    constructor(points: Array<Point>, v: number) {
        assert(_.every(points, (x)=>x instanceof Point));
        assert(typeof v === typeof 0);
        this.points = points;
        this.v = v;
    }
    rotate90Right() {
        return Brick.rotate90Right.apply(this, []);
    }
    rotate90Left() {
        return Brick.rotate90Left.apply(this, []);
    }
    lower() {
        return new Brick(this.points.map( (x) => x.lower() ), this.v);
    }
    projectAt(point: Point) {
        assert(point instanceof Point);
        return new Brick(this.points.map( (x)=>x.add(point)), this.v);
    }
}

class GridWithBrick {
    grid: Grid;
    brick: ?Brick;
    brickLocation: ?Point;
    constructor(grid: Grid, brick: ?Brick, brickLocation: ?Point) {
        assert(grid instanceof Grid);
        if (!((brick!==null) && (brickLocation!==null)))
            assert ((brick===null) && (brickLocation===null));
        this.grid = grid;
        this.brick = brick;
        this.brickLocation = brickLocation;
    }
    rotate90Right() {
        return this.reportRotationalResult( Brick.rotate90Right );
    }

    rotate90Left() {
        return this.reportRotationalResult( Brick.rotate90Left );
    }
    
    reportRotationalResult(f: ()=>Brick) { // in a rotational brick transformation the location of the brick does not change
        if (this.brick===null)
            return this;
        else {
            const brickNewShape = f.apply(this.brick, []);
            if (this.brickLocation!=null) {
                if (this.grid.fits(this.brickLocation.x, this.brickLocation.y, brickNewShape))
                    return new GridWithBrickAndActionResult(
                        new GridWithBrick(this.grid, brickNewShape, this.brickLocation)
                        , true);
                else
                    return new GridWithBrickAndActionResult(
                        this
                        , false);
            } else
                throw new Error('it is not possible for brick to be present and brickLocation to be null');
        }
    }
    ossifyBrickOnTheImmediatelyLowerPosition() {
        const brickLocation: Point = this.brickLocation || (()=>{throw new Error('ossify should not be called on a grid with no brickLocation');})();
        const brick: Brick         = this.brick         || (()=>{throw new Error('ossify should not be called on a grid with no brick'        );})();
        assert(this.grid.fits(brickLocation.x, brickLocation.y, brick));
        const projectedBrick = brick.projectAt(brickLocation.lower());
        assert(this.grid.fits   (brickLocation.lower().x, brickLocation.lower().y, brick));
        assert(this.grid.isStuck(brickLocation.lower().x, brickLocation.lower().y, brick));        
        const newOccupiedMap = this.grid.occupied.slice();
        for (let point of projectedBrick.points) {
            assert(this.grid.occupied[point.x][point.y]===null);
            newOccupiedMap[point.x][point.y] = brick.v;
        }
        return new GridWithBrick(new Grid(newOccupiedMap), null, null);
    }
    lower() {
        if (this.brick===null)
            return new GridWithBrickAndActionResult(this, false);
        else {
            const brickLocation: Point = this.brickLocation || (()=>{throw new Error('should have already escaped at this point');})();
            const brick: Brick         = this.brick         || (()=>{throw new Error('should have already escaped at this point');})();
            if (this.grid.isStuck(brickLocation.x, brickLocation.y, brick))
                return new GridWithBrickAndActionResult(this, false);
            else {
                assert(this.grid.fits(brickLocation.x, brickLocation.y+1, brick));
                if (this.grid.isStuck(brickLocation.x, brickLocation.y+1, brick)) {
                    return new GridWithBrickAndActionResult(this.ossifyBrickOnTheImmediatelyLowerPosition(), true);
                } else {
                    const newBrickLocation = brickLocation.lower();
                    return new GridWithBrickAndActionResult(
                        new GridWithBrick(this.grid, brick, newBrickLocation)
                        , true);
                }
            }
        }
    }
    stuffInCoords(i: number, j: number) {
        if (this.grid.occupied[i][j]!==null) {
            return this.grid.occupied[i][j];
        }
        else if (this.brick) {
            const brickLocation: Point = this.brickLocation || (()=>{throw new Error('should have already escaped at this point');})();
            const brick: Brick         = this.brick         || (()=>{throw new Error('should have already escaped at this point');})();
            const projectedBrick: Brick = brick.projectAt(brickLocation);
            for (let point of projectedBrick.points) {
                if (point.equals(new Point(i, j)))
                    return projectedBrick.v;
            }
        }
        return 'X';
    }
    toString() {
        const lines = [];
        for (let j = 0 ; j < this.grid.occupied[0].length ; j++) {
            const symbolsOnLine = [];
            for (let i = 0 ; i < this.grid.occupied.length; i++) {
                symbolsOnLine.push(this.stuffInCoords(i, j));
            }
            lines.push(symbolsOnLine.join('-'));
        }
        return lines.join('\n');
    }
}


class GridWithBrickAndActionResult {
    gridWithBrick: GridWithBrick;
    actionResult: boolean;
    constructor(gridWithBrick: GridWithBrick, actionResult: boolean) {
        assert((gridWithBrick instanceof GridWithBrick) && (typeof actionResult === typeof true));
        this.gridWithBrick = gridWithBrick;
        this.actionResult = actionResult;
    }
}


exports.Grid  = Grid;
exports.Point = Point;
exports.Brick = Brick;
exports.GridWithBrick = GridWithBrick;
exports.GridWithBrickAndActionResult = GridWithBrickAndActionResult;
