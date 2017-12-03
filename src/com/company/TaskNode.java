package com.company;

public class TaskNode {
    private Task     task;
    private TaskNode next;

    /**
     * @return task from node
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task
     * add task in list
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return next task in list
     */
    public TaskNode getNext() {
        return next;
    }

    /**
     * @param next
     * set next task in list
     */
    public void setNext(TaskNode next) {
        this.next = next;
    }
}
