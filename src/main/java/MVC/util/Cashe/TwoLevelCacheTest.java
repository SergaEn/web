package MVC.util.Cashe;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class TwoLevelCacheTest {
    TwoLevelCache twoLevelCache = new TwoLevelCache(100, new IntegratorCacheImp());
    final CountDownLatch latch = new CountDownLatch(450);
    final ExecutorService service = Executors.newCachedThreadPool();
    final Random random = new Random();

    @Test
    public void testCashe() throws Exception {
        for (int i = 0; i < 200; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    twoLevelCache.cashe(random.nextInt(50), random.nextInt(10000));
                    System.out.println(Thread.currentThread());
                    latch.countDown();
                }
            });
        }

        for (int j = 0; j < 3000; j++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("GEt " + Thread.currentThread() + " ---- " + twoLevelCache.getObject(random.nextInt(50)));
                    latch.countDown();
                }
            });
        }
        latch.await();
        twoLevelCache.clearCache();
    }

}