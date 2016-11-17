declare class ChaiAssert {
    isFalse       (o: any)        : boolean;    
    instanceOf    (x: any, t: any): boolean;
    isNumber      (x: any)        : boolean;
    isString      (x: any)        : boolean;
    isTrue        (x: any)        : boolean;
    isNull        (x: any)        : boolean;
    throws        (f:   F)        : boolean;
}
