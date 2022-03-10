// IEventAidlInterface.aidl
package com.stone.stoneviewskt.service;

// Declare any non-default types here with import statements

import com.stone.stoneviewskt.service.data.ServiceRequest;
import com.stone.stoneviewskt.service.data.ServiceResponse;

interface IEventAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    ServiceResponse send(in ServiceRequest request);
}