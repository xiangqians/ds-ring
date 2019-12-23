package com.xiangqian.ds.ring;

/**
 * @author xiangqian
 * @date 22:24 2019/12/11
 */
public interface BlockingRing<E> extends Ring<E> {

    /**
     * 添加一个元素，如果环形队列满了，则阻塞，直到环形队列有空闲位置
     *
     * @param e
     */
    void put(E e) throws InterruptedException;

    /**
     * 返回环形队列尾部的元素，如果队列为空，则阻塞，直到环形队列有元素
     *
     * @return
     */
    E take() throws InterruptedException;
}
