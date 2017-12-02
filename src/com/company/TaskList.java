package com.company;

public abstract class TaskList {
    abstract void add(Task task);
    abstract boolean remove(Task task);
    abstract int size();
    abstract Task getTask(int index);
    abstract int getSize();
    abstract int getIndex();
    abstract int getNumber();
    abstract Task[] getArray();
   /*TaskList incoming(int from, int to){
        TaskList IncommingTask = new TaskList();
        int count=0;
        for(int i=0;i<getNumber();i++){
            if(this.getArray()[i].nextTimeAfter(from)>=from && this.getArray()[i].nextTimeAfter(from)<=to){
                IncommingTask.add(this.getArray()[i]);
                count++;
            }
        }
        IncommingTask.number=count;
        return IncommingTask;
   }*/
}
