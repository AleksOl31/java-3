package car_race;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;

    private static final CyclicBarrier startCyclicBarrier = new CyclicBarrier(MainClass.CARS_COUNT);
    public static final CountDownLatch startCountDownLatch = new CountDownLatch(MainClass.CARS_COUNT);
    public static final CountDownLatch finishCountDownLatch = new CountDownLatch(MainClass.CARS_COUNT);


    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            startCyclicBarrier.await();
            startCountDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if (i == race.getStages().size() - 1) {
                finishCountDownLatch.countDown();
                if (finishCountDownLatch.getCount() == MainClass.CARS_COUNT-1) {
                    System.out.println(this.name + " - WIN");
                }
            }
        }
    }
}
