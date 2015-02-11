package week2;

import java.util.HashSet;

/**
 * Author: Mark
 * Date  : 2015/2/9
 * Time  : 16:02
 */
public class LinkedListProblem {

    public static void main(String[] args) {
        /*LkList<Integer> list = makeCircle();
        printN(list, 20);
        boolean hasCircle = hasCircle(list);
        System.out.println(hasCircle);
        LkList.Node inter = findIntersectionUseSet(list);
        System.out.println(inter.item);
        LkList.Node inter1 = findIntersection(list);
        System.out.println(inter1.item);*/

        LkList<Integer> list1 = makeCircle();
        LkList<Integer> list2 = makeCircle();
        System.out.println(intersect(list1, list2));

        LkList<Integer> list3 = new LkList<>();
        LkList<Integer> list4 = new LkList<>();
        LkList<Integer>.Node tail = list3.new Node(0, null);
        list3.header = tail;
        for (int i = 1; i < 6; i++) {
            list3.add(i);
        }
        LkList<Integer>.Node inter = list3.new Node(6, list3.header);
        list3.header = inter;
        tail.next = inter;
        for (int i = 7; i < 12; i++) {
            list3.add(i);
        }

        list4.header = inter;
        for (int i = 0; i < 3; i++) {
            list4.add(i * 3);
        }

        System.out.println(intersect(list3, list4));

        /*LkList<Integer> lkList = new LkList<>();
        for (int i = 0; i < 6; i++) {
            lkList.add(i);
        }
        printN(lkList, 6);
        lkList = reverseList(lkList);
        printN(lkList, 6);
        System.out.println(lastKth(lkList, 3).item);
        System.out.println(findMidNode(lkList).item);*/

        /*LkList<Integer> list1 = new LkList<>();
        LkList<Integer> list2 = new LkList<>();
        LkList.Node n0 = list1.new Node(0,null);
        LkList.Node n1 = list1.new Node(1,n0);
        LkList.Node n2 = list1.new Node(2,n1);
        list1.header = n2;
        list2.header = n2;
        for (int i = 0; i < 3; i++) {
            list1.add(i+5);
        }
        for (int i = 0; i < 2; i++) {
            list2.add(10+i);
        }
        printN(list1,6);
        printN(list2,5);
        System.out.println(isIntersect(list1,list2));
        System.out.println(intersect(list1,list2));
        System.out.println(findIntersect(list1, list2).item);*/
    }

    private static LkList.Node findIntersection(LkList<Integer> list) {
        if (!hasCircle(list)) {
            return null;
        }
        LkList.Node p1 = list.header;
        LkList.Node p2 = list.header;
        LkList.Node node = getMeetNode(p1, p2);
        int len1 = 0;
        while (p1 != node) {
            len1++;
            p1 = p1.next;
        }

        p2 = node.next;
        int lenCircle = 1;
        while (p2 != node) {
            lenCircle++;
            p2 = p2.next;
        }

        System.out.println(len1);
        System.out.println(lenCircle);

        p1 = list.header;
        int diffLen = Math.abs(len1 - lenCircle);
        if (len1 > lenCircle) {
            for (int i = 0; i < diffLen; i++) {
                p1 = p1.next;
            }
        } else {
            for (int i = 0; i < diffLen; i++) {
                p2 = p2.next;
            }
        }
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    private static LkList.Node findIntersectionUseSet(LkList<Integer> list) {
        if (!hasCircle(list)) {
            return null;
        }
        HashSet<LkList.Node> set = new HashSet<>();
        LkList.Node p = list.header;
        while (p != null) {
            if (set.contains(p)) {
                return p;
            }
            set.add(p);
            p = p.next;
        }
        return null;
    }

    /*
    判断两个链表（链表可能有环）是否相交
     */
    private static boolean intersect(LkList<Integer> lk1, LkList<Integer> lk2) {

        boolean lk1hasCircle = true;
        boolean lk2hasCircle = true;

        LkList.Node p11 = lk1.header;
        if (p11 == null) return false;
        LkList.Node p12 = lk1.header;
        while (p11 != null && p12 != null) {
            p11 = p11.next;
            p12 = p12.next;
            if (p12 != null) {
                p12 = p12.next;
            }
            if (p11 == p12) {
                break;
            }
        }
        if (p11 != p12) {
            lk1hasCircle = false;
        }
        LkList.Node p21 = lk2.header;
        if (p21 == null) {
            return false;
        }
        LkList.Node p22 = p21.next;
        while (p21 != null && p22 != null) {
            p21 = p21.next;
            p22 = p22.next;
            if (p22 != null) {
                p22 = p22.next;
            }
            if (p21 == p22) {
                break;
            }
        }
        if (p21 != p22) {
            lk2hasCircle = false;
        }

        if (!lk1hasCircle && !lk2hasCircle) { // 两个链表都没有环
            return isIntersect(lk1, lk2);
        } else if (lk1hasCircle && !lk2hasCircle || !lk1hasCircle) { //lk1hasCircle && !lk2hasCircle || !lk1hasCircle && lk2hasCircle  只有一个链表有环
            return false;
        }

        //两个都有环
        if (p11 == p21) {
            return true;
        }
        p21 = p21.next;
        while (p21 != p22) {
            if (p11 == p21) return true;
            p21 = p21.next;
        }
        return false;
    }

    private static boolean isIntersect(LkList<Integer> lk1, LkList<Integer> lk2) {
        LkList.Node p1 = lk1.header;
        LkList.Node p2 = lk2.header;
        if (p1 == null || p2 == null) return false;
        while (p1.next != null) {
            p1 = p1.next;
        }
        while (p2.next != null) {
            p2 = p2.next;
        }
        return p1 == p2;
    }

    private static LkList.Node findIntersect(LkList<Integer> lk1, LkList<Integer> lk2) {
        LkList.Node p1 = lk1.header;
        LkList.Node p2 = lk2.header;
        if (p1 == null || p2 == null) return null;
        int len1 = 1;
        int len2 = 1;
        while (p1.next != null) {
            p1 = p1.next;
            len1++;
        }
        while (p2.next != null) {
            p2 = p2.next;
            len2++;
        }
        if (p1 != p2) return null;
        int k = Math.abs(len1 - len2);
        p1 = lk1.header;
        p2 = lk2.header;
        if (len1 > len2) {
            for (int i = 0; i < k; i++) {
                p1 = p1.next;
            }
        } else {
            for (int i = 0; i < k; i++) {
                p2 = p2.next;
            }
        }
        while (p1 != null && p2 != null && p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    private static boolean hasCircle(LkList<Integer> list) {
        LkList.Node p1 = list.header;
        if (p1 == null) return false;
        LkList.Node p2 = list.header.next;
        LkList.Node node = getMeetNode(p1, p2);
        return node != null;
    }

    private static boolean hasCircleUseSet(LkList<Integer> list) {
        HashSet<LkList.Node> set = new HashSet<>();
        LkList.Node p = list.header;
        while (p != null) {
            if (set.contains(p)) {
                return true;
            }
            set.add(p);
            p = p.next;
        }
        return false;
    }

    private static LkList.Node getMeetNode(LkList.Node p1, LkList.Node p2) {
        while (p1 != null && p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
            if (p2 != null) {
                p2 = p2.next;
            }
            if (p1 == p2) {
                return p1;
            }
        }
        return null;
    }

    public static LkList.Node deleteNode(LkList.Node p) {
        LkList.Node deleted = p.next;
        p.item = deleted.item;
        p.next = deleted.next;
        deleted.next = null;
        return deleted;
    }

    public static LkList<Integer> reverseList(LkList<Integer> list) {
        LkList<Integer> nl = new LkList<>();
        LkList.Node p;
        while (list.header != null) {
            p = list.header.next;
            list.header.next = nl.header;
            nl.header = list.header;
            list.header = p;
        }
        return nl;
    }

    /*
     * assume k is less than the size of list
     */
    public static LkList.Node lastKth(LkList<Integer> list, int k) {
        LkList.Node p1 = list.header;
        LkList.Node p2 = list.header;
        for (int i = 0; i < k && p1 != null; i++) {
            p1 = p1.next;
        }
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    public static LkList.Node findMidNode(LkList<Integer> list) {
        LkList.Node p1 = list.header;
        LkList.Node p2;
        if (p1 == null) { // 链表为空
            return null;
        } else if (p1.next == null) { //链表只有一个节点
            return p1;
        }
        p2 = p1.next;
        while (p2.next != null) {
            p1 = p1.next;
            p2 = p2.next;
            if (p2.next == null) { //链表有偶数个节点的时候，p2走到最后一个节点
                break;
            }
            p2 = p2.next;
        }
        return p1;
    }

    private static LkList<Integer> makeCircle() {
        LkList<Integer> list = new LkList<>();
        LkList<Integer>.Node tail = list.new Node(0, null);
        list.header = tail;
        for (int i = 1; i < 6; i++) {
            list.add(i);
        }
        LkList<Integer>.Node inter = list.new Node(6, list.header);
        list.header = inter;
        tail.next = inter;
        for (int i = 7; i < 12; i++) {
            list.add(i);
        }
        return list;
    }

    private static void printN(LkList<Integer> l, int n) {
        LkList.Node tmp = l.header;
        for (int i = 0; i < n; i++) {
            if (tmp != null) {
                System.out.print(tmp.item + " ");
                tmp = tmp.next;
            }
        }
        System.out.println();
    }

    static class LkList<T> {

        Node header;
        int size;

        public LkList() {
            this.header = null;
            this.size = 0;
        }

        public void add(T v) {
            Node n = new Node(v, header);
            header = n;
        }

        private class Node {
            T item;
            Node next;

            public Node(T item, Node next) {
                this.item = item;
                this.next = next;
            }
        }

    }

}
