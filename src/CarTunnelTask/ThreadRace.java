package CarTunnelTask;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadRace {

    private static final int CARS_COUNT_IN_TUNNEL = 3;
    private static final int CARS_COUNT = 10;

    private  static final Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT_IN_TUNNEL);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final CyclicBarrier cyclicBarier = new CyclicBarrier(CARS_COUNT);
    private static ArrayList<Car> carArrayList = new ArrayList<>();

    private static final CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
    private static final Object monitor = new Object();
    private static int Winner = -1;

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();

        // cerating and doing all operations in loop
        for(int i=0; i<CARS_COUNT; i++){
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        prepare(index);
                        cyclicBarier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                    long before = System.currentTimeMillis();
                    try {
                        roadFirst(index);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        tunnel(index);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        roadSecond(index);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (monitor){
                        if ( Winner == -1){
                            Winner = index;
                        }
                    }

                    // add car with time to list
                    long after = System.currentTimeMillis();

                    carArrayList.add(new Car(index,after - before));
                    countDownLatch.countDown();
                }
            });
        }

        // shutdown all thread operations
        countDownLatch.await();
        executorService.shutdown();

        // show results
        System.out.println( "Time : " +(System.nanoTime() - startTime)/100_000_000 + " Milliseconds");

        for (Car c : carArrayList) {
            System.out.println(c.getId() + " : " + c.getTime());

        }
        System.out.println("Winner is --> " + carArrayList.get(Winner).getId() + " Time is -->" + carArrayList.get(Winner).getTime());

    }

    public static void sleepRandomTime() throws InterruptedException {
        long millis = (long) (Math.random() * 2000 + 1000);
        Thread.sleep(millis);
    }

    public static void prepare(int index) throws InterruptedException {
        System.out.println(index + " started prepaering");
        sleepRandomTime();
        System.out.println(index + " finished prepaering");
    }
    public static void roadFirst(int index) throws InterruptedException {
        System.out.println(index + " started roadFirst");
        sleepRandomTime();
        System.out.println(index + " finished roadFirst");
    }
    public static void roadSecond(int index) throws InterruptedException {
        System.out.println(index + " started roadSecond");
        sleepRandomTime();
        System.out.println(index + " finished roadSecond");
    }


    public static void tunnel(int index) throws InterruptedException {

        try {
            tunnelSemaphore.acquire();
            System.out.println(index + " started Tunnel");
            sleepRandomTime();
            System.out.println(index + " finished Tunnel");
        }catch (Exception e ){
            System.out.println(e);
        }finally {
            tunnelSemaphore.release();
        }


    }

}
