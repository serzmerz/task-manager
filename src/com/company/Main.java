package com.company;

public class Main {

    public static void main(String[] args) {
	Task task1 = new Task("task_name",36);
        System.out.println("Назва задачі=" + task1.getTitle()+ " Час=" + task1.getTime());
        task1.setTime(48);
        System.out.println("час виконаня змінений="+ task1.getTime());
        Task task2 = new Task("task_name2",12,72,40);
        System.out.println("Назва задачі=" + task2.getTitle()
                + " Час початку=" + task2.getStartTime()
                + " Час завершення=" +task2.getEndTime()
                + " Інтервал повторення=" + task2.getRepeatInterval()
                + " час виконаня="+ task2.getTime()
        );
        System.out.println("Насупне виконання після 32 ="+task2.nextTimeAfter(32));
        ArrayTaskList list1 = new ArrayTaskList();
       for(int i=0;i<5;i++){
        list1.add(task2);
        list1.add(task1);}
        list1.remove(task1);
        list1.remove(task1);
        for(int i=0;i<list1.number;i++){
        System.out.println(i+". "+list1.array[i].getTitle());}
        System.out.println(list1.getTask(3).getTitle());

        for(int i=0;i<list1.incoming(20,59).number;i++){
            System.out.println("w "+list1.incoming(20,59).array[i].getTitle()
                    +" Час виконання: "+list1.incoming(20,59).array[i].nextTimeAfter(20)
            );
        }

    }
}