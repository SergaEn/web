package MVC.util.Cashe;

/**
 * Created by en on 06.05.2015.
 */
public interface Cashe<K, V> {
    void cashe(K key, V value);

    V getObject(K key) throws DoesNotExistException;

    void deleteObject(K key);

    void clearCache();

    int size();

    boolean containsKey(K key);

}
