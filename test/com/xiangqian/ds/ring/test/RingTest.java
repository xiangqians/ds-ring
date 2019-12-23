package com.xiangqian.ds.ring.test;

import com.xiangqian.ds.ring.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangqian
 * @date 21:51 2019/12/11
 */
public class RingTest {


    public static void main(String[] args) {
        try {
            test1();
//            test2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Random RANDOM;
    public static final Ring<Integer> RING;
    public static final Ring<Integer> SYN_RING;

    static {
        RANDOM = new Random();
//        RING = new ArrayRing<>();
        RING = new LinkedRing<>();
        SYN_RING = Rings.synchronizedRing(RING);
    }

    public static void test1() {
        RingDetailsTest.info(RING);

//        System.out.println("number=" + RING.remove());
        RingDetailsTest.info(RING, "number=" + RING.poll());
        System.out.println();

        int count = 6;
        for (int i = 0; i < count; i++) {
            push(i);
            poll(i);
        }
    }

    public static final int PUSH_COUNT;
    public static final int POLL_COUNT;

    static {
        PUSH_COUNT = 20;
        POLL_COUNT = 20;
//        PUSH_COUNT = 3;
//        POLL_COUNT = 10;
    }

    public static void push(int count) {
        System.out.println("push " + count);
        for (int i = 0; i < PUSH_COUNT; i++) {
            int number = RANDOM.nextInt(100);
            RING.push(number);
            RingDetailsTest.info(RING, "push " + number);
        }
        System.out.println();
    }

    public static void poll(int count) {
        System.out.println("poll " + count);
        for (int i = 0; i < POLL_COUNT; i++) {
            RingDetailsTest.info(RING, "poll " + RING.poll());
        }
        System.out.println();
    }

    public static void test2() {
        new Thread("线程-写") {
            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    try {
                        int number = RANDOM.nextInt(100);
                        SYN_RING.push(number);
                        System.out.println("[" + Thread.currentThread().getName() + "] push " + number);
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();

        new Thread("线程-读") {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.err.println("[" + Thread.currentThread().getName() + "] poll " + SYN_RING.poll());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();
    }

}