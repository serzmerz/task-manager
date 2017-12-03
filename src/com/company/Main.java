package com.company;

import java.util.Date;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        try {
            Task task1 = new Task("Task1", new Date());
            task1.setActive(true);
            System.out.println("Назва задачі=" + task1.getTitle() + " Час=" + task1.getTime());
            System.out.println(" Час=" + task1.nextTimeAfter(new Date(1000)));
            task1.setTime(new Date());
            System.out.println("час виконаня змінений=" + task1.getTime());
            Task task2 = new Task("task_name2", new Date(), new Date(new Date().getTime() + 100000), 40);
            System.out.println("Назва задачі=" + task2.getTitle()
                    + " Час початку=" + task2.getStartTime()
                    + " Час завершення=" + task2.getEndTime()
                    + " Інтервал повторення=" + task2.getRepeatInterval()
                    + " час виконаня=" + task2.getTime()
            );
            System.out.println("Насупне виконання після 32 =" + task2.nextTimeAfter(new Date(new Date().getTime() + 1000)));
            ArrayTaskList list1 = new ArrayTaskList();
            for (int i = 0; i < 5; i++) {
                list1.add(task2);
                list1.add(task1);
            }
            list1.remove(task1);
            list1.remove(task1);
            ArrayTaskList TestList = new ArrayTaskList();
            TestList.add(new Task("A", new Date()));
            TestList.add(new Task("B", new Date()));
            TestList.add(new Task("C", new Date()));
            TestList.add(new Task("D", new Date()));
            TestList.add(new Task("E", new Date()));
            /* System.out.println(TestList.toString());
            Iterator<Task> ArrayTaskListIterator =TestList.iterator();
            System.out.println(ArrayTaskListIterator.next().getTitle());
            ArrayTaskListIterator.remove();
            System.out.println(TestList.toString());
            System.out.println(ArrayTaskListIterator.next().getTitle()); */
            System.out.println(TestList.toString());
            TestList.remove(new Task("A", new Date()));
            System.out.println(TestList.toString());
            /* for (int i = 0; i < list1.number; i++) {
                System.out.println(i + ". " + list1.array[i].getTitle());
            }
            System.out.println(list1.getTask(3).getTitle());

            for (int i = 0; i < list1.incoming(20, 59).number; i++) {
                System.out.println("w " + list1.incoming(20, 59).array[i].getTitle()
                        + " Час виконання: " + list1.incoming(20, 59).array[i].nextTimeAfter(20)
                );
            }
            LinkedTaskList linkedList = new LinkedTaskList();
            for (int i = 0; i < 5; i++) {
                linkedList.add(task2);
                linkedList.add(task1);
            }
            linkedList.remove(task1);
            linkedList.remove(task1);
            for (int i = 0; i < linkedList.size(); i++) {
                System.out.println(i + ". " + linkedList.getTask(i).getTitle());
            } */
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
