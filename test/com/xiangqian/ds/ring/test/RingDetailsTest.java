package com.xiangqian.ds.ring.test;

import com.xiangqian.ds.ring.ArrayRing;
import com.xiangqian.ds.ring.LinkedRing;
import com.xiangqian.ds.ring.Ring;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author xiangqian
 * @date 20:21 2019/12/11
 */
public class RingDetailsTest {

    public static void info(Ring<?> ring) {
        if (ring instanceof ArrayRing) {
            info((ArrayRing) ring);

        } else if (ring instanceof LinkedRing) {
            info((LinkedRing) ring);
        }
    }

    public static void info(Ring<?> ring, String x) {
        if (ring instanceof ArrayRing) {
            info((ArrayRing) ring, x);

        } else if (ring instanceof LinkedRing) {
            info((LinkedRing) ring, x);
        }
    }


    private static class LinkedRingDetails {
        private static final Field FRONT_NODE_FIELD;
        private static final Field REAR_NODE_FIELD;
        private static final Field CAPACITY_FIELD;

        static {
            try {
                Class<LinkedRing> clazz = LinkedRing.class;
                FRONT_NODE_FIELD = clazz.getDeclaredField("frontNode");
                FRONT_NODE_FIELD.setAccessible(true);

                REAR_NODE_FIELD = clazz.getDeclaredField("rearNode");
                REAR_NODE_FIELD.setAccessible(true);

                CAPACITY_FIELD = clazz.getDeclaredField("capacity");
                CAPACITY_FIELD.setAccessible(true);
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    private static void info(LinkedRing<?> linkedRing) {
        info(linkedRing, null);
    }

    private static void info(LinkedRing<?> linkedRing, String x) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("[").append(LinkedRing.class.getSimpleName()).append("] ");
            if (x != null) {
                builder.append(x);
                builder.append("  |  ");
            }
            builder.append("size=").append(linkedRing.size()).append(" | ");
            builder.append("frontNode=").append(LinkedRingDetails.FRONT_NODE_FIELD.get(linkedRing)).append(", ");
            builder.append("rearNode=").append(LinkedRingDetails.REAR_NODE_FIELD.get(linkedRing)).append(" | ");
            builder.append("capacity=").append(LinkedRingDetails.CAPACITY_FIELD.get(linkedRing)).append(" | ");
            builder.append(linkedRing.getNodeDetailsTest());
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class ArrayRingDetails {
        private static final Field FRONT_INDEX_FIELD;
        private static final Field REAR_INDEX_FIELD;
        private static final Field CAPACITY_FIELD;
        private static final Field ELEMENT_DATA_FIELD;

        static {
            try {
                Class<ArrayRing> clazz = ArrayRing.class;
                FRONT_INDEX_FIELD = clazz.getDeclaredField("frontIndex");
                FRONT_INDEX_FIELD.setAccessible(true);

                REAR_INDEX_FIELD = clazz.getDeclaredField("rearIndex");
                REAR_INDEX_FIELD.setAccessible(true);

                CAPACITY_FIELD = clazz.getDeclaredField("capacity");
                CAPACITY_FIELD.setAccessible(true);

                ELEMENT_DATA_FIELD = clazz.getDeclaredField("elementData");
                ELEMENT_DATA_FIELD.setAccessible(true);

            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    private static void info(ArrayRing<?> arrayRing) {
        info(arrayRing, null);
    }

    private static void info(ArrayRing<?> arrayRing, String x) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("[").append(ArrayRing.class.getSimpleName()).append("] ");
            if (x != null) {
                builder.append(x);
                builder.append("  |  ");
            }
            builder.append("size=").append(arrayRing.size()).append(", ");
            builder.append("frontIndex=").append(ArrayRingDetails.FRONT_INDEX_FIELD.get(arrayRing)).append(", ");
            builder.append("rearIndex=").append(ArrayRingDetails.REAR_INDEX_FIELD.get(arrayRing)).append(" | ");
            builder.append("capacity=").append(ArrayRingDetails.CAPACITY_FIELD.get(arrayRing)).append(", ");
            builder.append("elementData=").append(Arrays.asList((Object[]) ArrayRingDetails.ELEMENT_DATA_FIELD.get(arrayRing)));
            System.out.println(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
