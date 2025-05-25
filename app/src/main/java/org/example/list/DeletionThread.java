package org.example.list;

import org.mpisws.jmc.runtime.RuntimeUtils;
import org.mpisws.jmc.util.concurrent.JmcThread;

/**
 * DeletionThread is a thread that deletes an item from a set.
 */
public class DeletionThread extends JmcThread {

    /**
     * The set from which the item will be deleted.
     */
    private final Set set;

    /**
     * The item to be deleted from the set.
     */
    private final int item;

    /**
     * Constructs a DeletionThread with the specified set and item.
     *
     * @param set  the set from which the item will be deleted
     * @param item the item to be deleted
     */
    public DeletionThread(Set set, int item) {
        this.set = set;
        // Write event for initializing set
        RuntimeUtils.writeEvent(this, set,
                "org/mpisws/jmc/programs/det/lists/DeletionThread", "set",
                "Lorg/mpisws/jmc/programs/det/lists/list/Set;");

        this.item = item;
        // Write event for initializing item
        RuntimeUtils.writeEvent(this, item,
                "org/mpisws/jmc/programs/det/lists/DeletionThread", "item", "I");
    }

    /**
     * The run method that is executed when the thread starts.
     * It tries to delete the item from the set.
     */
    @Override
    public void run1() {
        set.remove(item);
    }
}
