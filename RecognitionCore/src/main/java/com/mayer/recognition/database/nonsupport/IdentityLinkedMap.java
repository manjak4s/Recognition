package com.mayer.recognition.database.nonsupport;

import com.google.common.collect.Lists;

import java.util.List;

class IdentityLinkedMap<K, V> {

    private static class Entry<Key, Value> {
        Key key;
        Value value;

        private Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<Entry<K, V>> entries = Lists.newLinkedList();

    public synchronized void put(K key, V value) {
        final int index = findIndexOf(key);
        if (index == -1) {
            entries.add(new Entry<K, V>(key, value));
        }
    }

    public synchronized void remove(K keyToRemove) {
        final int index = findIndexOf(keyToRemove);
        if (index != -1) {
            entries.remove(index);
        }
    }

    private int findIndexOf(K keyToFind) {
        int i = 0;
        for (Entry<K, V> entry : entries) {
            if (entry.key == keyToFind) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public synchronized V get(K key) {
        final int indexOf = findIndexOf(key);
        if (indexOf != -1) {
            return entries.get(indexOf).value;
        }
        return null;
    }
}