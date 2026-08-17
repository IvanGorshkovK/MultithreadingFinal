import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {
    private ExecutorService executor;
    private Map<String,Integer> resultMap = new HashMap<>();
    private final AtomicInteger taskCounter = new AtomicInteger();
    private AtomicInteger activeTaskCounter = new AtomicInteger();


    public DataProcessor(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public Future<Integer> submitTask(List<Integer> numbers) {
        String taskName = "task" + taskCounter.incrementAndGet();
        CalculateSumTask task = new CalculateSumTask(numbers, taskName);

        activeTaskCounter.incrementAndGet();

        Future<Integer> future = executor.submit(() -> {
            try {
                Integer result = task.call();
                synchronized (resultMap) {
                    resultMap.put(taskName, result);
                }
                return result;
            } finally {
                activeTaskCounter.decrementAndGet();
            }
        });

        return future;
    }
    public int getActiveTaskCount() {
        return activeTaskCounter.get();
    }

    public Optional<Integer> getResult(String taskName) {
        synchronized (resultMap) {
            return Optional.ofNullable(resultMap.get(taskName));
        }
    }


    public void shutdown() {
        executor.shutdown();
    }

}
