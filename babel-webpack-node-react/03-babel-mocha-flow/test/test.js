import 'babel-polyfill';
const assert     = require('assert');
import _ from 'lodash';

import {Grid, Point, Brick, GridWithBrick, GridWithBrickAndActionResultp} from '../es6/app.js';

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


describe('Grid', function () {
    describe('constructor', function () {
        it('should not fail if occupied array contains reasonable values'
           , function () {
               new Grid([[2]]);
               new Grid([[1], [2]]);
               new Grid([[1,2,3], [2,3,4]]);
               new Grid([[1,2,3], [2,3,4], [5,6,6]]);
           });     
        it('should fail if occupied array contains unreasonable values'
           , function () {
               assert.throws(()=>{
                   new Grid([[], [1]]);
               });
               assert.throws(()=>{
                   new Grid([[1,2,3], [2,3,4], [5,6,,6]]);
               });
               assert.throws(()=>{
                   new Grid([[1,2,3], [2,3,4], [5,6,7,6]]);
               });               
               assert.throws(()=>{
                   new Grid([[1,2,3], [2,3,4], [3,2]]);
               });
               assert.throws(()=>{
                   new Grid([[1,2,,3]]);
               });               
           });
    });
    describe('fits', function() {
        it('should work #1', function() {
            const grid = new Grid([[0, 0, 0], [null, null, null], [0, 0, 0]]);
            const brick = new Brick([new Point(0,1), new Point(0,0), new Point(0, -1)], 0);
            for (let i = 0 ; i < 2; i++) {
                for (let j = 0 ; j < 2; j++) {
                    if (i==1)
                        assert(grid.fits(i, j, brick));
                    else
                        assert(!grid.fits(i, j, brick));
                }
            }
        });
        it('should work #2', function() {
            const grid = new Grid([[0, 0, 0], [0, null, 0], [0, 0, 0]]);
            const brick = new Brick([new Point(0,0)], 0);
            for (let i = 0 ; i < 2; i++) {
                for (let j = 0 ; j < 2; j++) {
                    if ((i==1) && (j==1))
                        assert(grid.fits(i, j, brick));
                    else
                        assert(!grid.fits(i, j, brick));
                }
            }
        });
        it('should work #3', function() {
            const grid = new Grid([[0, null, 0], [0, null, 0], [0, null, 0]]);
            const brickOriginal = new Brick([new Point(0,1), new Point(0,0), new Point(0, -1)], 0);
            const bricks = [brickOriginal.rotate90Right(), brickOriginal.rotate90Left()];
            for (let brick of bricks) {
                for (let i = 0 ; i < 2; i++) {
                    for (let j = 0 ; j < 2; j++) {
                        if ((i==1) && (j==1))
                            assert(grid.fits(i, j, brick));
                        else
                            assert(!grid.fits(i, j, brick));
                    }
                }
            }
        });
    });
    describe('isStuck', function() {
        it('should work #1', function() {
            const grid = new Grid([[null, null, null], [null, null, null], [0, 0, 0]]);
            const brick = new Brick([new Point(0,-1), new Point(0,0), new Point(0, 1)], 0);
            assert(!grid.isStuck(0, 0, brick));
            assert( grid.isStuck(0, 1, brick));
            assert( grid.isStuck(0, 2, brick));
            assert(!grid.isStuck(1, 0, brick));
            assert( grid.isStuck(1, 1, brick));
            assert( grid.isStuck(1, 2, brick));
            assert( grid.isStuck(2, 0, brick));
            assert( grid.isStuck(2, 1, brick));
            assert( grid.isStuck(2, 2, brick));            
        });
    });
});


         

describe('GridWithBrick', function () {
    describe('rotate90Right or rotate90Left', function () {
        it('should work #1'
           , function () {
               const fs = ['rotate90Right', 'rotate90Left'];
               for (let f of fs) {
                   const grid = new Grid([[null, null, null], [null, null, null], [null, null, null],]);
                   const brick = new Brick([new Point(0, -1), new Point(0, 0), new Point(0, 1)], 0);
                   const gridWithBrick = new GridWithBrick(grid, brick, new Point(1,1));
                   assert.equal(gridWithBrick.toString(),
`X-0-X
X-0-X
X-0-X`);
                   const gridWithBrickAndActionResult = gridWithBrick[f]();
                   assert(gridWithBrickAndActionResult.actionResult);
                   assert.equal(gridWithBrickAndActionResult.gridWithBrick.toString(),
`X-X-X
0-0-0
X-X-X`);
               }
           });     
        it('should work #2'
           , function () {
               const fs = ['rotate90Right', 'rotate90Left'];
               for (let f of fs) {               
                   const grid = new Grid([[null, 1, null], [null, null, null], [null, null, null],]);
                   const brick = new Brick([new Point(0, -1), new Point(0, 0), new Point(0, 1)], 0);
                   const gridWithBrick = new GridWithBrick(grid, brick, new Point(1,1));
                   assert.equal(gridWithBrick.toString(),
`X-0-X
1-0-X
X-0-X`);
                   const gridWithBrickAndActionResult = gridWithBrick.rotate90Right();
                   assert(!gridWithBrickAndActionResult.actionResult);
                   assert.equal(gridWithBrickAndActionResult.gridWithBrick.toString(), gridWithBrick.toString());
               }
           });
        it('should work #3'
           , function () {
               const grid = new Grid([[null, null, null], [null, null, null], [null, null, null],]);
               const brick = new Brick([new Point(0, -1), new Point(1, -1), new Point(0, 0), new Point(0, 1)], 0);
               const gridWithBrick = new GridWithBrick(grid, brick, new Point(1,1));
               assert.equal(gridWithBrick.toString(),
`X-0-0
X-0-X
X-0-X`);
               const gridWithBrickAndActionResult = gridWithBrick.rotate90Right();
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrickAndActionResult.gridWithBrick.toString(),
`X-X-X
0-0-0
X-X-0`);
           });
        it('should work #4'
           , function () {
               const grid = new Grid([[null, null, null], [null, null, null], [null, null, null],]);
               const brick = new Brick([new Point(0, -1), new Point(1, -1), new Point(0, 0), new Point(0, 1)], 0);
               const gridWithBrick = new GridWithBrick(grid, brick, new Point(1,0));
               assert.equal(gridWithBrick.toString(),
`X-0-X
X-0-X
X-X-X`);
               const gridWithBrickAndActionResult = gridWithBrick.rotate90Right();
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrickAndActionResult.gridWithBrick.toString(),
`0-0-0
X-X-0
X-X-X`);
           });                     
    });
    describe('lower', function () {
        it('should work #1'
           , function () {
               const grid = new Grid([[null, null, null], [null, null, null], [null, null, null],]);
               const brick = new Brick([new Point(-1, 0), new Point(0, 0), new Point(1, 0)], 2);
               let gridWithBrick = new GridWithBrick(grid, brick, new Point(1,0));
               assert.equal(gridWithBrick.toString(),
`2-2-2
X-X-X
X-X-X`);
               let gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X
2-2-2
X-X-X`);

               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X
X-X-X
2-2-2`);
               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(!gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X
X-X-X
2-2-2`);                              

           });
        it('should work #2'
           , function () {
               const grid = new Grid([[null, null, null, null], [null, null, null, 1], [null, null, null, null],[0, 0, 0, 0]]);
               const brick = new Brick([new Point(-1, 0), new Point(0, 0), new Point(1, 0)], 2);
               let gridWithBrick = new GridWithBrick(grid, brick, new Point(1,0));
               assert.equal(gridWithBrick.toString(),
`2-2-2-0
X-X-X-0
X-X-X-0
X-1-X-0`);
               let gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
2-2-2-0
X-X-X-0
X-1-X-0`);

               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
X-X-X-0
2-2-2-0
X-1-X-0`);
               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(!gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
X-X-X-0
2-2-2-0
X-1-X-0`);

           });
        it('should work #3'
           , function () {
               const grid = new Grid([[null, null, null, null], [null, null, null, 1], [null, null, null, null],[0, 0, 0, 0]]);
               const brick = new Brick([new Point(-1, 0), new Point(-1, 1), new Point(0, 0), new Point(1, 0), new Point(1,1)], 2);
               let gridWithBrick = new GridWithBrick(grid, brick, new Point(1,0));
               assert.equal(gridWithBrick.toString(),
`2-2-2-0
2-X-2-0
X-X-X-0
X-1-X-0`);
               let gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
2-2-2-0
2-X-2-0
X-1-X-0`);

               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
X-X-X-0
2-2-2-0
2-1-2-0`);
               gridWithBrickAndActionResult = gridWithBrick.lower();
               gridWithBrick = gridWithBrickAndActionResult.gridWithBrick;
               assert(!gridWithBrickAndActionResult.actionResult);
               assert.equal(gridWithBrick.toString(),
`X-X-X-0
X-X-X-0
2-2-2-0
2-1-2-0`);

           });                
    });
    
});
