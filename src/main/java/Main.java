public class Main {
    public static void main(String[] args) {
        int philosophersCount = 5;

        Table table = new Table(philosophersCount);
        Thread[] philosophers = new Thread[philosophersCount];

        for (int i = 0; i < philosophersCount; i++) {
            philosophers[i] = new Thread(new Philosopher(i, table));
        }

        for (Thread philosopher : philosophers) {
            philosopher.start();
        }
    }
}