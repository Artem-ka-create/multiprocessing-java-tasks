package CarTunnelTask;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NoThreadRace {

    private static final int CARS_COUNT_IN_TUNNEL = 2;
    private static final int CARS_COUNT = 5;
    private static final CyclicBarrier cyclicBarier = new CyclicBarrier(CARS_COUNT);


    public static void main(String[] args) throws InterruptedException {

        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Car> carArrayList = new ArrayList<>(CARS_COUNT);
        long startTime = System.nanoTime();


        for(int i=0; i<CARS_COUNT; i++) {
            final int index = i;

            // waiting visualization
            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        prepare(index);
                        cyclicBarier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            // add new car
            carArrayList.add(new Car(index));

            // start tree and add to thread list
            thr.start();
            threads.add(thr);
        }

        waitThreads(threads);
        threads.clear();


        for (Car c: carArrayList) {
            long t = roadFirst(c.getId());
            c.setTime(t);
        }

        Collections.sort(carArrayList);

        ArrayList<Car> tunnelList = new ArrayList<>();

        for (int i=0 ; i< CARS_COUNT; i++){

            tunnelList.add(carArrayList.get(i));
            if (tunnelList.size() == CARS_COUNT_IN_TUNNEL || (CARS_COUNT-i) < CARS_COUNT_IN_TUNNEL){
                for (Car c: tunnelList) {
                    long t = tunnel(c.getId());
                    c.setTime(c.getTime() + t);
                }
                tunnelList.clear();
            }
        }

        tunnelList.clear();
        Collections.sort(carArrayList);

        for (Car c: carArrayList) {
            long t = roadSecond(c.getId());
            c.setTime(c.getTime() + t);
        }
        System.out.println( "Time : " +(System.nanoTime() - startTime)/100_000_000 + " Milliseconds");

        Collections.sort(carArrayList);

        ListPrint(carArrayList);



    }

    private static void waitThreads( ArrayList<Thread> threads) throws InterruptedException {
        for (Thread  t: threads) {
            t.join();
        }
    }


    private static long sleepRandomTime() throws InterruptedException {
        long millis = (long) (Math.random() * 2000 + 1000);
        Thread.sleep(millis);

        return millis;
    }

    private static long roadFirst(int index) throws InterruptedException {

        System.out.println(index + " started roadFirst");
        long millis = sleepRandomTime();
        System.out.println(index + " finished roadFirst");
        return millis;
    }
    private static long roadSecond(int index) throws InterruptedException {

        System.out.println(index + " started roadSecond");
        long millis = sleepRandomTime();
        System.out.println(index + " finished roadSecond");
        return  millis;
    }

    private static long tunnel(int index) throws InterruptedException {

        System.out.println(index + " started Tunnel");
        long millis = sleepRandomTime();
        System.out.println(index + " finished Tunnel");
        return millis;
    }
    private static void prepare(int index) throws InterruptedException {

        System.out.println(index + " started prepaering");
        sleepRandomTime();
        System.out.println(index + " finished prepaering");
    }
    private static void ListPrint(ArrayList<Car> cars){

        for (Car c : cars) {
            System.out.println(c.getId() + " : " + c.getTime());
        }
        System.out.println("Winner is --> " + cars.get(0).getId() + " Time is -->" + cars.get(0).getTime());

    }



}
