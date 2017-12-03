package com.company;

public class ArrayTaskList extends TaskList {
    private int size = 10;
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

    public void add(Task task) throws Exception {
        if(task == null) throw new Exception("Empty task don't be added");
        if (number >= size - 1) {
            Task[] arraybuf = new Task[size];
            System.arraycopy(array, 0, arraybuf, 0, number);
            this.array = null;
            size = size * 2;
            this.array = new Task[size];
            System.arraycopy(arraybuf, 0, array, 0, number);

        }
        this.array[number] = task;
        number++;
    }

    public boolean remove(Task task) {
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

    public Task getTask(int index) throws Exception {
        if(array[index] == null) throw new Exception("This task not found");
        return array[index];
    }

    public ArrayTaskList incoming(int from, int to) throws Exception {
        ArrayTaskList IncommingTask = new ArrayTaskList();
        int count = 0;

        for (int i = 0; i < number; i++) {
            if (this.array[i].nextTimeAfter(from) >= from && this.array[i].nextTimeAfter(from) <= to) {
                IncommingTask.add(array[i]);
                count++;
            }
        }

        IncommingTask.number = count;
        return IncommingTask;
    }
}
