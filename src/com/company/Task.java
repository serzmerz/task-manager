package com.company;

public class Task {
    private String title;
    private boolean active;
    private boolean repeat;
    private int time;
    private int start;
    private int end;
    private int interval;

    public Task(String title, int time) throws Exception {
        this.setTitle(title);
        this.setTime(time);
    }

    public Task(String title, int start, int end, int interval) throws Exception {
        this.setTitle(title);
        this.setTime(start, end, interval);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
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

    public int getTime() {
        if (repeat) {
            return start;
        } else {
            return time;
        }
    }

    public void setTime(int time) throws Exception {
        if (time < 0) {
            throw new Exception ("Time can not be < 0");
        }
        this.repeat = false;
        this.time = time;
    }

    public int getStartTime() {
        if (!repeat) return time;
        else return start;
    }

    public int getEndTime() {
        if (!repeat) return time;
        else return end;
    }

    public int getRepeatInterval() {
        if (!repeat) return 0;
        else return interval;
    }

    public void setTime(int start, int end, int interval) throws Exception {
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

    public boolean isRepeated() {
        return repeat;
    }

    public int nextTimeAfter(int current) {
        if (this.isActive()) {
            if (!this.isRepeated()) {
                if (this.getStartTime() > current) {
                    return this.getStartTime();
                } else return -1;
            } else {
                for (int i = start; i <= end; i = i + interval) {
                    if (i > current) return i;
                    if(i + interval > current && i + interval <= end) {
                        return i + interval;
                    }
                }
                return -1;
            }
        }
        return -1;
    }
}
