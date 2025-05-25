package org.example.list;

import org.mpisws.jmc.runtime.RuntimeUtils;
import org.mpisws.jmc.util.concurrent.JmcThread;

/**
 * InsertionThread is a thread that inserts an item into a set.
 */
public class InsertionThread extends JmcThread {

    /**
     * The set into which the item will be inserted.
     */
    private final Set set;

    /**
     * The item to be inserted into the set.
     */
    public final int item;

    /**
     * Constructs an InsertionThread with the specified set and item.
     *
     * @param set  the set into which the item will be inserted
     * @param item the item to be inserted
     */
    public InsertionThread(Set set, int item) {
        this.set = set;
        // Write event for initializing set
        RuntimeUtils.writeEvent(this, set, "org/mpisws/jmc/programs/det/lists/InsertionThread",
                "set", "Lorg/mpisws/jmc/programs/det/lists/list/Set;");

        this.item = item;
        // Write event for initializing item
        RuntimeUtils.writeEvent(this, item,
                "org/mpisws/jmc/programs/det/lists/InsertionThread", "item", "I");
    }

    /**
     * The run method that is executed when the thread starts.
     * It tries to insert the item into the set.
     */
    @Override
    public void run1() {
        set.add(item);
    }
}
