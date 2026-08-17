import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int poolSize = 10;
        DataProcessor processor = new DataProcessor(poolSize);

        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            //случайный список чисел
            List<Integer> numbers = new ArrayList<>();
            int count = 3 + (int)(Math.random() * 5); //
            for (int j = 0; j < count; j++) {
                numbers.add((int)(Math.random() * 100));
            }
            futures.add(processor.submitTask(numbers));
        }
        System.out.println("\nОжидание завершения всех задач");
        while (processor.getActiveTaskCount() > 0) {
            System.out.println("Активных задач: " + processor.getActiveTaskCount());
            Thread.sleep(100); // небольшая пауза между проверками
        }

        //Выводим результаты
        System.out.println("\n Результаты всех задач");
        for (int i = 1; i <= 100; i++) {
            String taskName = "task" + i;
            Optional<Integer> result = processor.getResult(taskName);
            System.out.println(taskName + " -> " + (result.isPresent() ? result.get() : "не найден"));
        }

        // Завершаем работу
        processor.shutdown();
        System.out.println("\nПрограмма завершена.");
    }
}
