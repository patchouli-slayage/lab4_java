import java.util.concurrent.Semaphore;

public class Table {
    private final Semaphore[] forks = new Semaphore[5];
    private final boolean[] forkTaken = new boolean[5];
    private final Object lock = new Object();

    public Table() {
        for (int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    public void getForks(int left, int right) {
        synchronized (lock) {
            while (!canTakeForks(left, right)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            forkTaken[left] = true;
            forkTaken[right] = true;
        }

        try {
            forks[left].acquire();
            forks[right].acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void putForks(int left, int right) {
        forks[left].release();
        forks[right].release();

        synchronized (lock) {
            forkTaken[left] = false;
            forkTaken[right] = false;
            lock.notifyAll();
        }
    }

    private boolean canTakeForks(int left, int right) {
        int takenCount = 0;
        for (boolean taken : forkTaken) {
            if (taken) takenCount++;
        }
        return takenCount < 4 && !forkTaken[left] && !forkTaken[right];
    }
}