package com.company;

import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) throws Exception {
        ArrayTaskList IncomingTask = new ArrayTaskList();

        for (Task task : tasks) {
            Date afterStart = task.nextTimeAfter(start);
            if (afterStart != null && (afterStart.before(end) || afterStart.equals(end))) {
                IncomingTask.add(task);
            }
        }

        return IncomingTask;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> sortedMap = new TreeMap<>();
        Date day;

        for (Task task : tasks) {
            day = task.nextTimeAfter(start);

            while (day != null && (day.before(end) || day.equals(end))) {
                if (!sortedMap.containsKey(day)) {
                    sortedMap.put(day, new HashSet<Task>());
                }

                sortedMap.get(day).add(task);
                day = task.nextTimeAfter(day);
            }
        }
        return sortedMap;
    }
}
