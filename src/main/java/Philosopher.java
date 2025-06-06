public class Philosopher extends Thread {
    private final Table table;
    private final int leftFork, rightFork;
    private final int id;

    public Philosopher(int id, Table table) {
        this.id = id;
        this.table = table;
        this.rightFork = id;
        this.leftFork = (id + 1) % 5;
        start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            try {
                System.out.println("Philosopher " + id + " is thinking (" + (i + 1) + ")");
                Thread.sleep((long)(Math.random() * 300 + 200));

                table.getForks(leftFork, rightFork);

                System.out.println("Philosopher " + id + " is eating (" + (i + 1) + ")");
                Thread.sleep((long)(Math.random() * 300 + 200));

                table.putForks(leftFork, rightFork);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Philosopher " + id + " was interrupted.");
                break;
            }
        }

        System.out.println("Philosopher " + id + " has finished eating.");
    }
}