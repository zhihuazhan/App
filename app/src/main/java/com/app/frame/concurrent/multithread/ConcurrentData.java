package com.app.frame.concurrent.multithread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据缓存
 */

public class ConcurrentData {

    private ConcurrentHashMap<Object, Object> mMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Object, Object> get() {
        return mMap;
    }

    public Object get(Object key) {
        return mMap.get(key);
    }

    public Object put(Object key, Object value) {
        return mMap.put(key, value);
    }

    public Object remove(Object key) {
        return mMap.remove(key);
    }

}
