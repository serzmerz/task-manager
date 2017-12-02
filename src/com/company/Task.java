package com.company;

public class Task {
    private String title;
    private boolean active;
    private boolean repeat;
    private int time;
    private int start;
    private int end;
    private int interval;

    Task(String title, int time) {
        this.title = title;
        this.time = time;
        this.repeat = false;
    }

    Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeat = true;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    int getTime() {
        if (repeat) {
            return start;
        } else {
            return time;
        }
    }

    void setTime(int time) {
        this.repeat = false;
        this.time = time;
    }

    int getStartTime() {
        if (!repeat) return time;
        else return start;
    }

    int getEndTime() {
        if (!repeat) return time;
        else return end;
    }

    int getRepeatInterval() {
        if (!repeat) return 0;
        else return interval;
    }

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeat = true;
    }

    private boolean isRepeated() {
        return repeat;
    }

    int nextTimeAfter(int current) {
        int stan = 0;
        int currentNext = 0;
        if (!this.isRepeated()) {
            if (this.getStartTime() >= current) {
                return this.getStartTime();
            } else return -1;
        } else {
            for (int i = start; i <= end; i = i + interval) {
                if (i <= current && i + interval >= current) {
                    stan = 1;
                    currentNext = i + interval;
                }
            }
            if (stan == 1) {
                return currentNext;
            } else return -1;
        }
    }
}
