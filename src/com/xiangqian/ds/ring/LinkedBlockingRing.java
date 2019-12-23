package com.xiangqian.ds.ring;

/**
 * @author xiangqian
 * @date 21:00 2019/12/11
 */
public class LinkedBlockingRing<E> extends AbstractBlockingRing<E> {

    public LinkedBlockingRing() {
        this(6, true);
    }

    /**
     * @param initialCapacity 初始化容量大小
     * @param fair            同步锁的策略，true，公平策略；非公平策略；
     */
    public LinkedBlockingRing(int initialCapacity, boolean fair) {
        super(new LinkedRing<>(initialCapacity), fair);
    }

}
