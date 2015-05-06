package MVC.util.Cashe;

/**
 * Created by en on 06.05.2015.
 */
public interface LeveledCache<K, V> extends Cashe<K, V>, FrequencyObject<K> {
    void recache();
}
