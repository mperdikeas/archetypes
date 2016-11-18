 declare module "chai" {
/*    declare interface TIassert {
        isFalse       (o: any)        : boolean;
        instanceOf    (x: any, t: any): boolean;
        isNumber      (x: any)        : boolean;
        isString      (x: any)        : boolean;
        isTrue        (x: any)        : boolean;
        isNull        (x: any)        : boolean;
        throws        (f:   F)        : boolean;
    }
*/
    declare class AssertClass {
         static isFalse       (o: any)        : void;    
         static instanceOf    (x: any, t: any): void;
         static isNumber      (x: any)        : void;
         static isString      (x: any)        : void;
         static isTrue        (x: any)        : void;
         static isNull        (x: any)        : void;
         static throws        (f:   F)        : void;
    }

     declare export var assert: typeof AssertClass
     
//  declare export var assert2: typeof TIassert
 }

/*
     declare class ChaiAssert {
         isFalse       (o: any)        : boolean;    
         instanceOf    (x: any, t: any): boolean;
         isNumber      (x: any)        : boolean;
         isString      (x: any)        : boolean;
         isTrue        (x: any)        : boolean;
         isNull        (x: any)        : boolean;
         throws        (f:   F)        : boolean;
     }

*/
