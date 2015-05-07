package MVC.util.Cashe;

import java.util.Set;

/**
 * Created by en on 06.05.2015.
 */
public interface IntegratorCache<K, V> {
    FileCache<K, V> getFileCache();
    RamCache<K, V> getRamCache();

}
