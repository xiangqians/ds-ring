package com.xiangqian.ds.ring;

import java.util.NoSuchElementException;

/**
 * @author xiangqian
 * @date 13:55 2019/09/22
 */
public interface Ring<E> extends java.io.Serializable {

    /**
     * Returns the number of elements in this ring.
     *
     * @return the number of elements in this ring
     */
    int size();

    /**
     * 环形队列是否已满
     *
     * @return
     */
    boolean isFull();

    /**
     * Returns <tt>true</tt> if this ring contains no elements.
     *
     * @return <tt>true</tt> if this ring contains no elements
     */
    boolean isEmpty();

    /**
     * 添加一个元素，如果环形队列满了，则覆盖以前的数据
     *
     * @param e
     * @return
     */
    boolean push(E e);

    /**
     * 移除并返回环形队列尾部的元素，如果队列为空，则抛出一个NoSuchElementException异常
     *
     * @return
     */
    E remove() throws NoSuchElementException;

    /**
     * 移除并返回环形队列尾部的元素，如果队列为空，则返回null
     *
     * @return
     */
    E poll();

    /**
     * Reset all of the elements from this ring (optional operation).
     * The ring will be null after this call returns.
     */
    void reset();

}
