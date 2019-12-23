package com.xiangqian.ds.ring;

import java.util.NoSuchElementException;

/**
 * @author xiangqian
 * @date 13:55 2019/09/22
 */
public class LinkedRing<E> implements Ring<E> {

    private volatile Node<E> elementNode;

    protected int capacity; // 环形链表容量
    private volatile int size;
    private volatile Node<E> frontNode; // 当前环形结构前端Node
    private volatile Node<E> rearNode; // 当前环形结构后端Node

    public LinkedRing() {
        this(6);
    }

    public LinkedRing(int initialCapacity) {
        super();
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.capacity = initialCapacity;
        this.elementNode = new Node(0, null, null);

        this.size = 0;
        this.frontNode = this.elementNode;
        this.rearNode = this.elementNode;
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
        if (frontNode.index == rearNode.index && frontNode.value != null) {
            nextRearNode();
        }

        frontNode.value = e;
        nextFrontNode();
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

        E e = rearNode.value;
        rearNode.value = null;
        nextRearNode();
        return e;
    }

    @Override
    public void reset() {
        Node<E> node = elementNode;
        while (node != null) {
            node.value = null;
            node = node.next;
        }
        frontNode = elementNode;
        rearNode = elementNode;
    }

    private void checkNotNull(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    private void nextFrontNode() {
        Node<E> next = null;
        if (frontNode.index < capacity - 1) {
            next = frontNode.next;
            if (next == null) {
                next = new Node<>(frontNode.index + 1, null, null);
                frontNode.next = next;
            }
        } else {
            next = elementNode;
        }

        frontNode = next;
    }


    private void nextRearNode() {
        Node<E> next = null;
        if (rearNode.index < capacity - 1) {
            next = rearNode.next;
            if (next == null) {
                next = new Node<>(rearNode.index + 1, null, null);
                rearNode.next = next;
            }
        } else {
            next = elementNode;
        }
        rearNode = next;
    }

    private void calculateSize() {
        if (frontNode.index == rearNode.index) {
            size = frontNode.value == null ? 0 : capacity;

        } else if (frontNode.index > rearNode.index) {
            size = frontNode.index - rearNode.index;

        } else if (frontNode.index < rearNode.index) {
            size = capacity - rearNode.index + frontNode.index;
        }
    }

    protected static class Node<V> {
        int index;
        V value;
        Node<V> next;

        public Node(int index, V value, Node<V> next) {
            this.index = index;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "index=" + index +
                    ", value=" + value +
                    '}';
        }
    }

    public String getNodeDetailsTest() {
        StringBuilder builder = new StringBuilder();
        Node node = elementNode;
        builder.append("elementNode=[");
        while (node != null) {
            builder.append(node);
            builder.append(", ");
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

}
