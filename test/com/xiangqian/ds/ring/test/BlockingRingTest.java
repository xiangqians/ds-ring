package com.xiangqian.ds.ring.test;

import com.xiangqian.ds.ring.BlockingRing;
import com.xiangqian.ds.ring.LinkedBlockingRing;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangqian
 * @date 21:15 2019/12/11
 */
public class BlockingRingTest {

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Random RANDOM;
    public static final BlockingRing<Integer> BLOCKING_RING;

    static {
        RANDOM = new Random();
//        BLOCKING_RING = new ArckingRing<>();
        BLOCKING_RING = new LinkedBlockingRing<>();
    }

    private static void test1() {
        new Thread(new WriteRunnable(), "线程-写").start();
        new Thread(new ReadRunnable(), "线程-读").start();
    }

    public static class WriteRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
//                    push();
                    put();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void push() throws InterruptedException {
            int number = RANDOM.nextInt(100);
            BLOCKING_RING.push(number);
            System.out.println("[" + Thread.currentThread().getName() + "] push " + number + BlockingRingTest.details());
            TimeUnit.SECONDS.sleep(2);
        }

        public void put() throws InterruptedException {
            int number = RANDOM.nextInt(100);
            BLOCKING_RING.put(number);
            System.out.println("[" + Thread.currentThread().getName() + "] put " + number + BlockingRingTest.details());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static class ReadRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    poll();
//                    take();
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void poll() throws InterruptedException {
            System.err.println("[" + Thread.currentThread().getName() + "] poll " + BLOCKING_RING.poll() + BlockingRingTest.details());
            TimeUnit.SECONDS.sleep(3);
//            TimeUnit.MILLISECONDS.sleep(500);
        }

        public void take() throws InterruptedException {
            System.err.println("[" + Thread.currentThread().getName() + "] take " + BLOCKING_RING.take() + BlockingRingTest.details());
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }


    public static String details() {
        StringBuilder builder = new StringBuilder();
        builder.append(" | ");
        builder.append("isEmpty=").append(BLOCKING_RING.isEmpty()).append(", ");
        builder.append("isFull=").append(BLOCKING_RING.isFull()).append(", ");
        builder.append("size=").append(BLOCKING_RING.size());
        return builder.toString();
    }

}

