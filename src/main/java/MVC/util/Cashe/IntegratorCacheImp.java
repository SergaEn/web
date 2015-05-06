package MVC.util.Cashe;

/**
 * Created by en on 06.05.2015.
 */
public class IntegratorCacheImp implements IntegratorCache {
    @Override
    public FileCache getFileCache() {
        return new FileCacheImp();
    }

    @Override
    public RamCache getRamCache() {
        return new RamCacheImp();
    }
}
