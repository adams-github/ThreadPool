package com.example.threadpoollibrary;


public abstract class RunnableWithPriority implements Runnable {

    /**
     * 优先级
     */
    public int priority;

    public RunnableWithPriority(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
