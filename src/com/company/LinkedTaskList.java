package com.company;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends TaskList implements Iterable<Task> {

    private int sizeList;
    private TaskNode point;
    private TaskNode first;

    @Override
    public void add(Task task) {
        TaskNode node = new TaskNode();
        node.setTask(task);

        if (first == null) {
            first = node;
            point = node;
            this.sizeList++;
        } else {
            point.setNext(node);
            point = node;
            this.sizeList++;
        }
    }

    @Override
    public boolean remove(Task task) throws Exception {
        if (task == null) {
            throw new Exception("incoming task is null");
        }

        TaskNode pointPredecessor;
        TaskNode point = this.first;

        if (point == null)
            return false;

        // if one task in a list
        if ((point.getNext() == null) && point.getTask().equals(task)) {
            this.first = null;
            this.point = null;
            this.sizeList--;
            return true;
        }

        // if the first in a list
        if (point.getTask().equals(task)) {
            this.first = point.getNext();
            this.sizeList--;
            return true;
        }

        //if the task is in the middle of the list
        pointPredecessor = point;
        point = point.getNext();

        for (int i = 0; i < size() - 2; i++) { //without first and last
            if (point.getTask().equals(task)) {
                pointPredecessor.setNext(point.getNext());
                this.sizeList--;
                return true;
            }

            pointPredecessor = point;
            point = point.getNext();
        }

        // if task is last in list
        if (point.getNext() == null && point.getTask().equals(task)) {
            pointPredecessor.setNext(null);
            this.point = pointPredecessor;
            this.sizeList--;
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return sizeList;
    }

    @Override
    public Task getTask(int index) {

        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        int         count = 0;
        TaskNode    point = first;

        if (point == null)
            return null;

        while (index >= count) {
            if (count == index) {
                break;
            }

            point = point.getNext();
            count += 1;
        }
        return point.getTask();
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskListIterator();
    }


    private class LinkedTaskListIterator implements Iterator<Task> {
        private TaskNode lastElement;
        private TaskNode next;
        private int nextIndex;

        public LinkedTaskListIterator() {
            next = first;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Task next() {
            if(!hasNext()) throw new NoSuchElementException();
            lastElement = next;
            next = next.getNext();
            nextIndex++;
            return lastElement.getTask();
        }

        @Override
        public void remove() {
            if (lastElement == null) {
                throw new IllegalStateException();
            }
            try {
                LinkedTaskList.this.remove(getTask(nextIndex -1));
                lastElement = null;
                nextIndex--;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public LinkedTaskList clone() throws CloneNotSupportedException{
            LinkedTaskList result = (LinkedTaskList) super.clone();
            result.sizeList = 0;
            result.first = null;
            result.point = null;

            for (LinkedTaskListIterator it = this; it.hasNext(); ) {
                Task task = it.next();
                result.add(task.clone());
            }
            return result;
        }
    }
}
