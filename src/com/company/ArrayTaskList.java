package com.company;

public class ArrayTaskList {
    private int size = 3;
    Task[] array = new Task[size];
    private int index = 0;
    int number = 0;


    public int getSize() {
        return size;
    }

    public Task[] getArray() {
        return array;
    }

    public int getIndex() {
        return index;
    }

    public int getNumber() {
        return number;
    }

    void add(Task task) {
        if (number >= size - 1) {
            Task[] arraybuf = new Task[size];
            System.arraycopy(array, 0, arraybuf, 0, number);
            this.array = null;
            size = size + 10;
            this.array = new Task[size];
            System.arraycopy(arraybuf, 0, array, 0, number);

        }
        this.array[number] = task;
        number++;
    }

    boolean remove(Task task) {
        boolean result = false;
        for (int i = 0; i < number; i++) {
            if (task.equals(this.array[i])) {
                this.array[i] = this.array[number - 1];
                this.array[number - 1] = null;
                number--;
                result = true;
                break;
            }
        }
        return result;
    }

    public int size() {
        return number;
    }

    Task getTask(int index) {
        return array[index - 1];
    }

    ArrayTaskList incoming(int from, int to) {
        ArrayTaskList IncommingTask = new ArrayTaskList();
        int count = 0;

        for (int i = 0; i < number; i++) {
            if (array[i].nextTimeAfter(from) >= from && this.array[i].nextTimeAfter(from) <= to) {
                IncommingTask.add(array[i]);
                count++;
            }
        }

        IncommingTask.number = count;
        return IncommingTask;
    }
}
