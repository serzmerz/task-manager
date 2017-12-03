package com.company;

import java.io.Serializable;
import java.util.Iterator;

public abstract class TaskList implements Serializable {
    abstract void add(Task task) throws Exception;

    abstract boolean remove(Task task) throws Exception;

    abstract int size();

    abstract Task getTask(int index) throws Exception;

    public abstract Iterator<Task> iterator();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TaskList)) return false;
        if (((TaskList) o).size() != this.size()) return false;

        Iterator<Task> itCurrent = iterator();
        Iterator<Task> itObj = ((TaskList) o).iterator();

        while (itCurrent.hasNext() && itObj.hasNext()) {
            if (!itCurrent.next().equals(itObj.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = size();

        try {
            result = 28 * result + getTask(0).hashCode() + getTask(result - 1).hashCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("TaskList[" + size() + "] {\n");
        Iterator<Task> iterator = iterator();

        int i = 0;
        while (iterator.hasNext()) {
            Task taskIt = iterator.next();
            result.append("   task[").append(i).append("]: ").append(taskIt.toString()).append('\n');
            i++;
        }
        result.append("\n}");
        return result.toString();
    }
}
