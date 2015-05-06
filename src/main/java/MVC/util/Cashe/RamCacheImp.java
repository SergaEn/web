package MVC.util.Cashe;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by en on 06.05.2015.
 */
public class RamCacheImp<K extends Serializable, V extends Serializable> implements RamCache<K, V> {
    private ConcurrentHashMap<K, V> hashMap;
    private ConcurrentHashMap<K, Integer> frequencyMap;
    private volatile int frequency;

    public RamCacheImp() {
        hashMap = new ConcurrentHashMap<>();
        frequencyMap = new ConcurrentHashMap<>();
    }

    @Override
    public void cashe(final K key, final V v) {
        frequencyMap.put(key, 1);
        hashMap.put(key, v);
    }

    @Override
    public synchronized V getObject(final K key) throws DoesNotExistException {
        if (hashMap.containsKey(key)) {
            frequency = frequencyMap.get(key);
            frequencyMap.put(key, ++frequency);
            return hashMap.get(key);
        }
        throw new DoesNotExistException();
    }

    @Override
    public void deleteObject(final K key) {
        if (hashMap.containsKey(key)) {
            hashMap.remove(key);
            frequencyMap.remove(key);
        }
    }

    @Override
    public void clearCache() {
        hashMap.clear();
        frequencyMap.clear();
    }


    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean containsKey(final K key) {
        return hashMap.containsKey(key);
    }

    @Override
    public Set<K> getFrequencySetKey() {
        return frequencyMap.keySet();
    }

    @Override
    public int getFrequencyCallObjectByKey(final K key) {
        if (hashMap.containsKey(key)) {
            return frequencyMap.get(key);
        }
        return 0;
    }

}
