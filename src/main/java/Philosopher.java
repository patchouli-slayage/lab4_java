public class Philosopher implements Runnable {
    private final int id;
    private final Table table;

    public Philosopher(int id, Table table) {
        this.id = id;
        this.table = table;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            
            try {
                synchronized (System.out) {
                    System.out.println("Philosopher " + id + " is thinking " + i +  " time.");
                    Thread.sleep(500);

                    System.out.println("Philosopher " + id + " asks waiter to eat " + i + " time.");
                    table.requestForks(this);

                    System.out.println("Philosopher " + id + " is eating " + i + " time.");
                    Thread.sleep(1000);

                    table.returnForks(this);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}