/* @flow */

require('source-map-support').install();
import 'babel-polyfill';
import {assert} from 'chai';
import _ from 'lodash';



describe('it', function () {
    it('fails flow but succeeds0 as a test', function() {
        const f: number = 'string';
    });
    it('this is spotted by Flow and serves to show that Flow when type-checking Mocha has access to the declarations defined in [decls] - succeeds as a test', function() {
        const f : F = (x)=>(x/3);
    });
    it('this passes muster with both Flow and Mocha', function() {
        const f : F2 = (x)=>(x/3);
    });
    it('shows that we\'re using chai', function() {
        assert.isTrue(true);
    });
});




