package org.example.list.lazy;

import org.example.list.Set;
import org.example.list.node.LNode;

public class LazyList implements Set {

    private final LNode head;

    public LazyList() {
        head = new LNode(Integer.MIN_VALUE);
        head.next = new LNode(Integer.MAX_VALUE);
    }

    /**
     * Adds an element to the set.
     *
     * @param i the element to be added
     */
    @Override
    public boolean add(int i) {
        int key = i;
        while (true) {
            LNode pred = head;
            LNode curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (key == curr.key) {
                            return false;
                        } else {
                            LNode node = new LNode(i, key);
                            node.next = curr;
                            pred.next = node;
                            return true;
                        }
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    /**
     * Removes an element from the set.
     *
     * @param i the element to be removed
     * @return true if the element was successfully removed, false if it was not present
     */
    @Override
    public boolean remove(int i) {
        int key = i;
        while (true) {
            LNode pred = head;
            LNode curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        if (key == curr.key) {
                            curr.marked = true;
                            pred.next = curr.next;
                            return true;
                        } else {
                            return false;
                        }
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    /**
     * Checks if the set contains a specific element.
     *
     * @param i the element to check for
     * @return true if the element is present in the set, false otherwise
     */
    @Override
    public boolean contains(int i) {
        int key = i;
        LNode curr = head;
        while (curr.key < key) {
            curr = curr.next;
        }
        return key == curr.key && !curr.marked;
    }

    private boolean validate(LNode pred, LNode curr) {
        return !pred.marked && !curr.marked && pred.next == curr;
    }
}
