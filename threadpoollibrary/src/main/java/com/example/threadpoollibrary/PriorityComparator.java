package com.example.threadpoollibrary;

import java.util.Comparator;

/**
 * 线程优先级比较器
 * */
public class PriorityComparator <T extends RunnableWithPriority> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o2.getPriority() - o1.getPriority();
    }

}
