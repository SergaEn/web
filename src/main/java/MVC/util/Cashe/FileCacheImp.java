package MVC.util.Cashe;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by en on 06.05.2015.
 */
public class FileCacheImp<K extends Serializable, V extends Serializable> implements FileCache<K, V> {
    private ConcurrentHashMap<K, String> hashMap;
    private ConcurrentHashMap<K, Integer> frequencyMap;
    private static final String DIR = "temp\\";
    private volatile int frecquency;

    public FileCacheImp() {
        hashMap = new ConcurrentHashMap<>();
        frequencyMap = new ConcurrentHashMap<>();

        File tempFolder = new File(DIR);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
    }


    @Override
    public void cashe(final K key, final V value) {
        String pathToObject = DIR + UUID.randomUUID().toString() + ".cache";

        frequencyMap.put(key, 1);
        hashMap.put(key, pathToObject);

        try (ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(pathToObject))) {
            objectStream.writeObject(value);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public V getObject(final K key) throws DoesNotExistException {
        if (hashMap.containsKey(key)) {
            String pathToObject = hashMap.get(key);
            try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(pathToObject))) {

                V deserializedObject = (V) objectStream.readObject();
                frecquency = frequencyMap.remove(key);
                frequencyMap.put(key, ++frecquency);
                return deserializedObject;
            } catch (Exception e) {
                throw new DoesNotExistException();
            }
        }
        throw new DoesNotExistException();
    }

    @Override
    public void deleteObject(final K key) {
        if (hashMap.containsKey(key)) {
            File deletingFile = new File(hashMap.remove(key));
            frequencyMap.remove(key);
            deletingFile.delete();
        }
    }

    @Override
    public void clearCache() {
        for (K key : hashMap.keySet()) {
            File deletingFile = new File(hashMap.get(key));
            deletingFile.delete();
        }

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
