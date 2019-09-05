package com.heroxu.cases.cache.service;

import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class HashMapCacheService {

    private HashMap<String, String> hashMap = new HashMap<>();

    @Resource
    private TestDbMapper dbMapper;

    public String getValue(String key) {
        String value = hashMap.get(key);
        if (value == null) {
            value = dbMapper.get(key);
            hashMap.put(key, value);
        }
        return value;
    }

}
