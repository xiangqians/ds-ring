package com.xiangqian.ds.ring;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangqian
 * @date 21:04 2019/12/11
 */
public abstract class AbstractBlockingRing<E> implements BlockingRing<E> {

    private Ring<E> ring;

    private ReentrantLock lock;
    private Condition notEmpty;
    private Condition notFull;

    /**
     * @param ring
     * @param fair 同步锁的策略，true，公平策略；非公平策略；
     */
    public AbstractBlockingRing(Ring<E> ring, boolean fair) {
        this.ring = ring;
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }


    @Override
    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (ring.isFull()) {
                notFull.await();
            }
            ring.push(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (ring.isEmpty()) {
                notEmpty.await();
            }
            E e = ring.poll();
            notFull.signalAll();
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return ring.size();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        lock.lock();
        try {
            return ring.isFull();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        try {
            return ring.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean push(E e) {
        lock.lock();
        try {
            ring.push(e);
            notEmpty.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E remove() throws NoSuchElementException {
        lock.lock();
        try {
            if (ring.isEmpty()) {
                throw new NoSuchElementException();
            }
            E e = ring.poll();
            notFull.signalAll();
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll() {
        lock.lock();
        try {
            E e = ring.poll();
            notFull.signalAll();
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reset() {
        lock.lock();
        try {
            ring.reset();
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
