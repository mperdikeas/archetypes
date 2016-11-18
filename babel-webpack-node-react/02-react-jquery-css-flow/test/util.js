require('source-map-support').install();
import 'babel-polyfill';
const assert     = require('chai').assert;
import _ from 'lodash';

import {isSetOf, arr2set} from '../lib/util.js';

describe('isSetOf', function () {
    it('should work'
       , function () {
           const s = new Set();
           assert.isTrue(isSetOf(s), String);
       });     
});
