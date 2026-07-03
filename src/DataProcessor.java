import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {
    private ExecutorService executorService;
    private Map<String,Integer> results = new HashMap<>();
    private AtomicInteger atomicCounter = new AtomicInteger();
    private AtomicInteger activeTasks = new AtomicInteger();


    public DataProcessor(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }




}
