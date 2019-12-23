package com.xiangqian.ds.ring;

import java.util.NoSuchElementException;

/**
 * @author xiangqian
 * @date 23:58 2019/09/22
 */
public class ArrayRing<E> implements Ring<E> {

    private volatile Object[] elementData;

    protected int capacity; // 环形数组容量
    private volatile int size;
    private volatile int frontIndex; // 环形结构前端index
    private volatile int rearIndex; // 环形结构后端index

    public ArrayRing() {
        this(6);
    }

    /**
     * @param initialCapacity 初始化环形数组结构容量
     */
    public ArrayRing(int initialCapacity) {
        super();
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.capacity = initialCapacity;
        this.elementData = new Object[initialCapacity];

        this.size = 0;
        this.frontIndex = 0;
        this.rearIndex = 0;
    }

    @Override
    public int size() {
        calculateSize();
        return size;
    }

    @Override
    public boolean isFull() {
        calculateSize();
        return size == capacity;
    }

    @Override
    public boolean isEmpty() {
        calculateSize();
        return size == 0;
    }

    @Override
    public boolean push(E e) {
        checkNotNull(e);

        // 前端碰撞后端index，并且frontIndex位置的元素不为null时，后端向前取下一个节点。
        // 如果frontIndex位置的元素为null，说明已经没有前驱已经没有可读的数据了；
        // 否则说明前驱还有元素未读，需要移动rearIndex向前一个位置。
        if (frontIndex == rearIndex && elementData[frontIndex] != null) {
            nextRearIdx();
        }

        elementData[frontIndex] = e;
        nextFrontIdx();
//        System.out.println("frontIndex: " + frontIndex + ", rearIndex: " + rearIndex);

        return true;
    }

    @Override
    public E remove() throws NoSuchElementException {
        E e = poll();
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }

    @Override
    public E poll() {
        calculateSize();
        if (size == 0) {
            return null;
        }

        E e = (E) elementData[rearIndex];
        elementData[rearIndex] = null;
        nextRearIdx();
        return e;
    }

    @Override
    public void reset() {
        for (int i = 0; i < capacity; i++) {
            elementData[i] = null;
        }
        frontIndex = 0;
        rearIndex = 0;
    }

    private void checkNotNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    private void nextFrontIdx() {
        frontIndex++;

        // 前端index大于环形结构容量时，前端index从0开始
        if (frontIndex >= capacity) {
            frontIndex = 0;
        }
    }

    private void nextRearIdx() {
        rearIndex++;
        if (rearIndex >= capacity) {
            rearIndex = 0;
        }
    }

    private void calculateSize() {
        if (frontIndex == rearIndex) {
            size = elementData[frontIndex] == null ? 0 : capacity;

        } else if (frontIndex > rearIndex) {
            size = frontIndex - rearIndex;

        } else if (frontIndex < rearIndex) {
            size = capacity - rearIndex + frontIndex;
        }
    }

}
