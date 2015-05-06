package MVC.util.Cashe;

import java.util.Set;

/**
 * Created by en on 06.05.2015.
 */
public interface FrequencyObject<K> {
    Set<K> getFrequencySetKey();

    int getFrequencyCallObjectByKey(K key);
}
