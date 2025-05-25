package org.example.list.coarse;

import org.example.list.Set;
import org.example.list.node.Node;
import org.mpisws.jmc.runtime.RuntimeUtils;
import org.mpisws.jmc.util.concurrent.JmcReentrantLock;

/**
 * CoarseList is a coarse-grained linked list implementation of the Set interface.
 * It uses a single lock to synchronize access to the entire list, ensuring thread safety.
 * The list supports adding, removing, and checking for elements.
 */
public class CoarseList implements Set {

    /**
     * Head node of the coarse-grained linked list.
     */
    private final Node head;

    /**
     * Lock for synchronizing access to the list.
     */
    private final JmcReentrantLock lock;


    /**
     * Constructor to initialize the coarse-grained linked list.
     * It creates a head node with Integer.MIN_VALUE and a tail node with Integer.MAX_VALUE.
     */
    public CoarseList() {
        Node newNode1 = new Node(Integer.MIN_VALUE);
        head = newNode1;
        // Write event for initializing head node
        RuntimeUtils.writeEvent(this, newNode1, "org/example/list/coarse/CoarseList",
                "head", "Lorg/example/list/node/Node;");

        Node headNode = head;
        // Read event for accessing head node
        RuntimeUtils.readEvent(this, "org/example/list/coarse/CoarseList",
                "head", "Lorg/example/list/node/Node;");

        Node newNode2 = new Node(Integer.MAX_VALUE);
        headNode.next = newNode2;
        // Write event for initializing next member of head node
        RuntimeUtils.writeEvent(headNode, newNode2,
                "org/example/list/node/Node",
                "next",
                "Lorg/example/list/node/Node;");

        JmcReentrantLock lock = new JmcReentrantLock();
        this.lock = lock;
        // Write event for initializing lock
        RuntimeUtils.writeEvent(this, lock,
                "org/example/list/coarse/CoarseList",
                "lock",
                "Lorg/mpisws/jmc/util/concurrent/JmcReentrantLock;");
    }

    /**
     * Adds an element to the set. If the element already exists, it does not add it again.
     * This method uses a coarse-grained locking mechanism to ensure thread safety. If first
     * tries to lock the list, then traverses the list to find the correct position.
     *
     * @param i the element to be added
     */
    @Override
    public boolean add(int i) {
        Node pred, curr;
        int key = i;
        JmcReentrantLock l = lock;
        // Read event to read the lock object
        RuntimeUtils.readEvent(this, "org/example/list/coarse/CoarseList",
                "lock", "Lorg/mpisws/jmc/util/concurrent/JmcReentrantLock;");

        try {
            l.lock();
            Node h = head;
            // Read event to read the head node
            RuntimeUtils.readEvent(this, "org/example/list/coarse/CoarseList",
                    "head", "Lorg/example/list/node/Node;");
            pred = h;

            curr = pred.next;
            // Read event to read the next member of the head node
            RuntimeUtils.readEvent(pred, "org/example/list/node/Node",
                    "next", "Lorg/example/list/node/Node;");

            int currKey = curr.key;
            // Read event to read the key of the current node
            RuntimeUtils.readEvent(curr, "org/example/list/node/Node",
                    "key", "I");

            while (currKey < key) {
                pred = curr;
                Node n = curr.next;
                // Read event to read the next member of the current node
                RuntimeUtils.readEvent(curr, "org/example/list/node/Node",
                        "next", "Lorg/example/list/node/Node;");
                curr = n;

                currKey = curr.key;
                // Read event to read the key of the current node
                RuntimeUtils.readEvent(curr, "org/example/list/node/Node",
                        "key", "I");
            }

            currKey = curr.key;
            // Read event to read the key of the current node
            RuntimeUtils.readEvent(curr, "org/example/list/node/Node",
                    "key", "I");

            if (key == currKey) {
                return false;
            } else {
                Node node = new Node(i, key);
                node.next = curr;
                // Write event to write the next member of the new node
                RuntimeUtils.writeEvent(node, curr,
                        "org/example/list/node/Node",
                        "next",
                        "Lorg/example/list/node/Node;");

                pred.next = node;
                // Write event to write the next member of the predecessor node
                RuntimeUtils.writeEvent(pred, node,
                        "org/example/list/node/Node",
                        "next",
                        "Lorg/example/list/node/Node;");

                return true;
            }
        } finally {
            l.unlock();
        }
    }

    /**
     * Removes an element from the set. If the element is not present, it does nothing. It uses a coarse-grained locking
     * mechanism to ensure thread safety. The method first locks the list, then traverses the list to find the element.
     * If the element is found, it removes it by updating the next pointer of the predecessor node.
     *
     * @param i the element to be removed
     * @return true if the element was successfully removed, false if it was not present
     */
    @Override
    public boolean remove(int i) {
        Node pred, curr;
        int key = i;
        JmcReentrantLock l = lock;

        // Read event to read the lock object
        RuntimeUtils.readEvent(this, "org/mpisws/jmc/programs/det/lists/list/coarse/CoarseList",
                "lock", "Lorg/mpisws/jmc/util/concurrent/JmcReentrantLock;");
        try {
            l.lock();
            pred = head;

            // Read event to read the head node
            RuntimeUtils.readEvent(this, "org/mpisws/jmc/programs/det/lists/list/coarse/CoarseList",
                    "head", "Lorg/mpisws/jmc/programs/det/lists/list/node/Node;");

            curr = pred.next;

            // Read event to read the next member of the head node
            RuntimeUtils.readEvent(pred, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                    "next", "Lorg/mpisws/jmc/programs/det/lists/list/node/Node;");

            int currKey = curr.key;
            // Read event to read the key of the current node
            RuntimeUtils.readEvent(curr, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                    "key", "I");

            while (currKey < key) {
                pred = curr;

                Node n = curr.next;
                // Read event to read the next member of the current node
                RuntimeUtils.readEvent(curr, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                        "next", "Lorg/mpisws/jmc/programs/det/lists/list/node/Node;");

                curr = n;

                currKey = curr.key;
                // Read event to read the key of the current node
                RuntimeUtils.readEvent(curr, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                        "key", "I");
            }
            currKey = curr.key;
            // Read event to read the key of the current node
            RuntimeUtils.readEvent(curr, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                    "key", "I");

            if (key == currKey) {
                Node n = curr.next;

                // Read event to read the next member of the current node
                RuntimeUtils.readEvent(curr, "org/mpisws/jmc/programs/det/lists/list/node/Node",
                        "next", "Lorg/mpisws/jmc/programs/det/lists/list/node/Node;");

                pred.next = n;
                // Write event to write the next member of the predecessor node
                RuntimeUtils.writeEvent(pred, n,
                        "org/mpisws/jmc/programs/det/lists/list/node/Node", "next",
                        "Lorg/mpisws/jmc/programs/det/lists/list/node/Node;");

                return true;
            } else {
                return false;
            }
        } finally {
            l.unlock();
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
        Node pred, curr;
        int key = i;
        synchronized (lock) {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                curr = curr.next;
            }
            return key == curr.key;
        }
    }
}
