package com.htjy.baselibrary.widget.pinyin;


import java.util.Comparator;

public class IndexableComparator implements Comparator<Indexable> {

    public int compare(Indexable o1, Indexable o2) {
        if (o1.getIndex().equals("@")
                || o2.getIndex().equals("#")) {
            return -1;
        } else if (o1.getIndex().equals("#")
                || o2.getIndex().equals("@")) {
            return 1;
        } else {
            return o1.getIndex().compareTo(o2.getIndex());
        }
    }

}
