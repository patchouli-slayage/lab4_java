import java.util.concurrent.Semaphore;

public class Table {
    private final Semaphore[] forks;
    private final Semaphore waiter;  // офіціант

    public Table(int forksCount) {
        this.forks = new Semaphore[forksCount];
        this.waiter = new Semaphore(forksCount - 1); // офіціант пускає лише 4 філософів

        for (int i = 0; i < forksCount; i++)
            forks[i] = new Semaphore(1);
    }

    private int leftForkId(int philosopherId) {
        return philosopherId;
    }

    private int rightForkId(int philosopherId) {
        return (philosopherId + 1) % forks.length;
    }

    public void requestForks(Philosopher philosopher) throws InterruptedException {
        waiter.acquire();  // просить дозволу в офіціанта
        int left = leftForkId(philosopher.getId());
        int right = rightForkId(philosopher.getId());

        forks[left].acquire();
        synchronized (System.out) {
            System.out.println("Philosopher " + philosopher.getId() + " took left fork (" + left + ")");
            forks[right].acquire();
            System.out.println("Philosopher " + philosopher.getId() + " took right fork (" + right + ")");
        }
    }

    public void returnForks(Philosopher philosopher) {
        int left = leftForkId(philosopher.getId());
        int right = rightForkId(philosopher.getId());

        forks[left].release();
        forks[right].release();
        synchronized (System.out) {
            System.out.println("Philosopher " + philosopher.getId() + " put down both forks");
        }

        waiter.release();
    }
}