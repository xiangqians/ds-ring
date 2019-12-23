package com.xiangqian.ds.ring;

/**
 * @author xiangqian
 * @date 21:14 2019/11/27
 */
public class ArrayBlockingRing<E> extends AbstractBlockingRing<E> {

    public ArrayBlockingRing() {
        this(6, true);
    }

    /**
     * @param initialCapacity 初始化容量大小
     * @param fair            同步锁的策略，true，公平策略；非公平策略；
     */
    public ArrayBlockingRing(int initialCapacity, boolean fair) {
        super(new ArrayRing<>(initialCapacity), fair);
    }

}

