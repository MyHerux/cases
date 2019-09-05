package com.heroxu.cases.cache.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUHashMapCacheService {


    class LRULinkedHashMap extends LinkedHashMap {

        private final int max;
        private Object lock;

        public LRULinkedHashMap(int max, Object lock) {
            super((int) (max * 1.4f), 0.75f, true);
            this.max = max;
            this.lock = lock;
        }


        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > max;
        }

        public Object getValue(Object key) {
            synchronized (lock) {
                return get(key);
            }
        }
        public void putValue(Object key, Object value) {
            synchronized (lock) {
                put(key, value);
            }
        }


        public boolean removeValue(Object key) {
            synchronized (lock) {
                return remove(key) != null;
            }
        }
        public boolean removeAll(){
            clear();
            return true;
        }
    }


}
