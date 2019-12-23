package com.xiangqian.ds.ring;

import java.util.NoSuchElementException;

/**
 * Ring工具类
 *
 * @author xiangqian
 * @date 13:59 2019/09/22
 */
public class Rings {

    /**
     * 获取线程安全的Ring实例
     *
     * @param ring
     * @param <E>
     * @return
     */
    public static <E> Ring<E> synchronizedRing(Ring<E> ring) {
        if (ring instanceof BlockingRing) {
            return ring;
        }
        return new SynchronizedRing<E>(ring);
    }

    private static class SynchronizedRing<E> implements Ring<E> {
        final Ring<E> ring; // Backing Ring
        final Object mutex; // Object on which to synchronize

        public SynchronizedRing(Ring<E> ring) {
            super();
            this.ring = ring;
            this.mutex = this;
        }

        @Override
        public int size() {
            synchronized (this.mutex) {
                return this.ring.size();
            }
        }

        @Override
        public boolean isFull() {
            synchronized (this.mutex) {
                return this.ring.isFull();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.ring.isEmpty();
            }
        }

        @Override
        public boolean push(E e) {
            synchronized (this.mutex) {
                return this.ring.push(e);
            }
        }

        @Override
        public E remove() throws NoSuchElementException {
            synchronized (this.mutex) {
                return this.ring.remove();
            }
        }

        @Override
        public E poll() {
            synchronized (this.mutex) {
                return this.ring.poll();
            }
        }

        @Override
        public void reset() {
            synchronized (this.mutex) {
                this.ring.reset();
            }
        }

        @Override
        public String toString() {
            return this.ring.toString();
        }
    }

}
