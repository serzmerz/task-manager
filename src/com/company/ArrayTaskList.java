package com.company;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends TaskList implements Iterable<Task>, Cloneable {
    private Task[] array = new Task[20];
    private int number = 0;

    public Task[] getArray() {
        return array;
    }

    public int getNumber() {
        return number;
    }

    public void add(Task task) throws Exception {
        if (task == null) throw new Exception("Empty task don't be added");
        Task tempArrayTask[] = new Task[number + 1];

        System.arraycopy(array, 0, tempArrayTask, 0, number);

        tempArrayTask[number] = task;
        array = tempArrayTask;
        number++;
    }

    public boolean remove(Task task) {
        boolean result = false;
        for (int i = 0; i < number; i++) {
            if (task.equals(this.array[i])) {
                if (i == number - 1) {
                    this.array[i] = this.array[number - 1];
                    this.array[number - 1] = null;
                    number--;

                    Task tempArrayTask[] = new Task[number];
                    System.arraycopy(array, 0, tempArrayTask, 0, number);
                    array = tempArrayTask;

                    result = true;
                    break;
                }
                System.arraycopy(array, i + 1, array, i, number - 1 - i);
                array[number - 1] = null;
                number--;

                Task tempArrayTask[] = new Task[number];
                System.arraycopy(array, 0, tempArrayTask, 0, number);
                array = tempArrayTask;

                result = true;
                break;
            }
        }
        return result;
    }

    public int size() {
        return number;
    }

    public Task getTask(int index) throws Exception {
        if (array[index] == null) throw new IndexOutOfBoundsException("This task not found");
        return array[index];
    }


    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    private class ArrayTaskListIterator implements Iterator<Task> {
        private int pointer;
        private int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return pointer < size();
        }

        @Override
        public Task next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastReturned = pointer;
            pointer = pointer + 1;
            return array[lastReturned];
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }
            try {
                ArrayTaskList.this.remove(getTask(lastReturned));
                pointer = lastReturned;
                lastReturned = -1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.number = 0;

        for (Task task : array) {
            try {
                clone.add(task.clone());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clone;
    }
}
