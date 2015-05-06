package MVC.util.Cashe;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by en on 06.05.2015.
 */
public class TwoLevelCache<K extends Serializable, V extends Serializable> implements LeveledCache<K, V> {

    private RamCache<K, V> memoryCache;
    private FileCache<K, V> fileCashe;
    private volatile int numberOfRequests;
    private final int numberOfRequestsForRecahce;

    public TwoLevelCache(int numberOfRequestsForRecahce, IntegratorCache cacheIntegrator) {
        this.numberOfRequestsForRecahce = numberOfRequestsForRecahce;
        this.memoryCache = cacheIntegrator.getRamCache();
        this.fileCashe = cacheIntegrator.getFileCache();
        numberOfRequests = 0;
    }


    @Override
    public void recache() {
        ConcurrentSkipListSet<K> ramCache = new ConcurrentSkipListSet<K>(memoryCache.getFrequencySetKey());
        int boundFrecquency = 0;
        for (K key : ramCache) {
            boundFrecquency += memoryCache.getFrequencyCallObjectByKey(key);
        }
        boundFrecquency /= ramCache.size();


        for (K key : ramCache) {
            if (memoryCache.getFrequencyCallObjectByKey(key) <= boundFrecquency) {
                fileCashe.cashe(key, memoryCache.getObject(key));
                memoryCache.deleteObject(key);
            }
        }

        ConcurrentSkipListSet<K> fileCache = new ConcurrentSkipListSet<K>(fileCashe.getFrequencySetKey());
        for (K key : fileCache) {
            if (fileCashe.getFrequencyCallObjectByKey(key) > boundFrecquency) {
                memoryCache.cashe(key, fileCashe.getObject(key));
                fileCashe.deleteObject(key);
            }

        }
    }

    @Override
    public void cashe(final K key, final V value) {
        memoryCache.cashe(key, value);
    }

    @Override
    public synchronized V getObject(final K key) throws DoesNotExistException {
        if (memoryCache.containsKey(key)) {
            numberOfRequests = numberOfRequests + 1;
            if (numberOfRequests > numberOfRequestsForRecahce) {
                this.recache();
                numberOfRequests = 0;
            }

            System.out.println("Из памяти : " + memoryCache.getObject(key));
            return memoryCache.getObject(key);
        } else if (fileCashe.containsKey(key)) {
            numberOfRequests = numberOfRequests + 1;
            if (numberOfRequests > numberOfRequestsForRecahce) {
                this.recache();
                numberOfRequests = 0;
            }
            System.out.println("Из файла : " + fileCashe.getObject(key));
            return fileCashe.getObject(key);
        }
        throw new DoesNotExistException();
    }

    @Override
    public void deleteObject(final K key) {
        if (memoryCache.containsKey(key)) {
            memoryCache.deleteObject(key);
        }
        if (fileCashe.containsKey(key)) {
            fileCashe.deleteObject(key);
        }
    }

    @Override
    public void clearCache() {
        memoryCache.clearCache();
        fileCashe.clearCache();
    }

    @Override
    public int size() {
        return memoryCache.size() + fileCashe.size();
    }

    @Override
    public boolean containsKey(final K key) {
        return memoryCache.containsKey(key) || fileCashe.containsKey(key);
    }

    @Override
    public Set<K> getFrequencySetKey() {
        ConcurrentSkipListSet<K> set = new ConcurrentSkipListSet<K>(memoryCache.getFrequencySetKey());
        set.addAll(fileCashe.getFrequencySetKey());
        return set;
    }

    @Override
    public int getFrequencyCallObjectByKey(final K key) {
        if (memoryCache.containsKey(key))
            return memoryCache.getFrequencyCallObjectByKey(key);
        if (fileCashe.containsKey(key))
            return fileCashe.getFrequencyCallObjectByKey(key);
        return 0;
    }
}


