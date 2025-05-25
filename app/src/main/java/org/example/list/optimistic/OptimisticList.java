package org.example.list.optimistic;

import org.example.list.Set;
import org.example.list.node.FNode;

public class OptimisticList implements Set {

    public FNode head;

    public OptimisticList() {
        head = new FNode(Integer.MIN_VALUE);
        head.next = new FNode(Integer.MAX_VALUE);
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
            FNode pred = head;
            FNode curr = pred.next;
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
                            FNode node = new FNode(i, key);
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
            FNode pred = head;
            FNode curr = pred.next;
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
        while (true) {
            FNode pred = head;
            FNode curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            pred.lock();
            try {
                curr.lock();
                try {
                    if (validate(pred, curr)) {
                        return key == curr.key;
                    }
                } finally {
                    curr.unlock();
                }
            } finally {
                pred.unlock();
            }
        }
    }

    private boolean validate(FNode pred, FNode curr) {
        FNode node = head;
        while (node.key <= pred.key) {
            if (node == pred) {
                return pred.next == curr;
            }
            node = node.next;
        }
        return false;
    }
}
