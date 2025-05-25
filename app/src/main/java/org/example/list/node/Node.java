package org.example.list.node;

import org.mpisws.jmc.runtime.RuntimeUtils;

/**
 * Node class representing an element in a concurrent coarse-grained linked list.
 * It contains an item, a key, and a reference to the next node.
 */
public class Node {

    /**
     * The item stored in the node.
     */
    public int item;

    /**
     * The key associated with the node.
     */
    public int key;

    /**
     * Reference to the next node in the list.
     */
    public Node next;

    /**
     * Constructor to create a Node.
     *
     * @param i the item to be stored in the node and also used as the key.
     */
    public Node(int i) {
        item = i;
        // Write event for initializing item
        RuntimeUtils.writeEvent(this, i, "org/example/list/node/Node", "item", "I");

        key = i;
        // Write event for initializing key
        RuntimeUtils.writeEvent(this, i, "org/example/list/node/Node", "key", "I");
    }

    /**
     * Constructor to create a Node with specified item and key.
     *
     * @param item the item to be stored in the node.
     * @param key  the key associated with the node.
     */
    public Node(int item, int key) {
        this.item = item;
        // Write event for initializing item
        RuntimeUtils.writeEvent(this, item, "org/example/list/node/Node", "item", "I");

        this.key = key;
        // Write event for initializing key
        RuntimeUtils.writeEvent(this, key, "org/example/list/node/Node", "key", "I");
    }
}
