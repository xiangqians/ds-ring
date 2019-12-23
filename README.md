# 环形数据结构

## 概述
使用数组和链表实现了环形数据结构！

## 快速开始

### Ring

#### 单线程环境下
```java
import com.xiangqian.ds.ring.Ring;
import com.xiangqian.ds.ring.ArrayRing;
import com.xiangqian.ds.ring.LinkedRing;
public class RingTest {
    public static void main(String[] args) {
        Ring<String> ring = new ArrayRing<>();
//        Ring<String> ring = new LinkedRing<>();

        // 1. 添加
        // 添加一个元素，如果环形队列满了，则覆盖以前的数据
        ring.push("Hello World!");

        String value = null;
        
        // 2. 获取
        // 2.1 移除并返回环形队列尾部的元素，如果队列为空，则抛出一个NoSuchElementException异常
        value = ring.remove();

        // 2.2 移除并返回环形队列尾部的元素，如果队列为空，则返回null
        value = ring.poll();

        // 重置，环中所有元素将置为null
        ring.reset();
    }
}
```

#### 多线程环境下
```java
import com.xiangqian.ds.ring.Ring;
import com.xiangqian.ds.ring.ArrayRing;
import com.xiangqian.ds.ring.LinkedRing;
import com.xiangqian.ds.ring.Rings;
public class RingTest {
    public static void main(String[] args) {
        Ring<String> ring = new ArrayRing<>();
//        Ring<String> ring = new LinkedRing<>();

        // 获取安全的Ring实例
        Ring<String> safeRing = Rings.synchronizedRing(ring);

        safeRing.push("Hello World!"); // push
        String value = safeRing.poll(); // poll
    }
}
```

### BlockingRing
```java
import com.xiangqian.ds.ring.BlockingRing;
import com.xiangqian.ds.ring.ArrayBlockingRing;
import com.xiangqian.ds.ring.LinkedBlockingRing;
public class BlockingRingTest {
    public static void main(String[] args) throws InterruptedException {
        // ArrayBlockingRing本身就是支持在多线程环境下使用
        BlockingRing<String> blockingRing = new ArrayBlockingRing<>();
//        BlockingRing<String> blockingRing = new LinkedBlockingRing<>();

        // 1. 添加元素
        // 1.1 添加一个元素，如果环形队列满了，则覆盖以前的数据
        blockingRing.push("Hello World!");

        // 1.2 添加一个元素，如果环形队列满了，则阻塞，直到环形队列有空闲位置
        blockingRing.put("Hello World!");

        String value = null;

        // 2. 获取元素
        // 2.1 返回环形队列尾部的元素，如果队列为空，则阻塞，直到环形队列有元素
        value = blockingRing.take();

        // 2.2 移除并返回环形队列尾部的元素，如果队列为空，则抛出一个NoSuchElementException异常
        value = blockingRing.remove();

        // 2.3 移除并返回环形队列尾部的元素，如果队列为空，则返回null
        value = blockingRing.poll();
    }
}
```

