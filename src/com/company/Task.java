package com.company;

public class Task {
    private String title;
    private boolean active;
    private boolean repeat;
    private int time;
    private int start;
    private int end;
    private int interval;

    Task(String title, int time) throws Exception {
        this.setTitle(title);
        this.setTime(time);
    }

    Task(String title, int start, int end, int interval) throws Exception {
        this.setTitle(title);
        this.setTime(start, end, interval);
    }

    String getTitle() {
        return title;
    }

    private void setTitle(String title) throws Exception {
        if (title == null) {
            throw new Exception ("Title can not be null");
        }
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

    void setTime(int time) throws Exception {
        if (time < 0) {
            throw new Exception ("Time can not be < 0");
        }
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

    private void setTime(int start, int end, int interval) throws Exception {
        if (interval <= 0) {
            throw new Exception ("Interval can not be 0 or < 0");
        }

        if (start > end) {
            throw new Exception ("Start time can not be > endTime");
        }

        if (interval >= end - start) {
            throw new Exception ("Interval can not be >= EndTime - Time");
        }
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
