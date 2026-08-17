import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class CalculateSumTask implements Callable<Integer> {

    private List<Integer> sumTask;
    private String taskName;


    public CalculateSumTask(List<Integer> sumTask, String taskName) {
        this.sumTask = sumTask;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Имя задачи: " + taskName + ", имя потока: " + Thread.currentThread().getName());
        Thread.sleep(200);
        return sumTask.stream().mapToInt(Integer::intValue).sum();
    }


    @Override
    public String toString() {
        return "CalculateSumTask{" +
                "sumTask=" + sumTask +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
