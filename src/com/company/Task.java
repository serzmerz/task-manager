package com.company;

import java.util.Objects;

public class Task implements Cloneable {
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
            throw new Exception("Title can not be null");
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
            throw new Exception("Time can not be < 0");
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
            throw new Exception("Interval can not be 0 or < 0");
        }

        if (start > end) {
            throw new Exception("Start time can not be > endTime");
        }

        if (interval >= end - start) {
            throw new Exception("Interval can not be >= EndTime - Time");
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
                    if (i + interval > current && i + interval <= end) {
                        return i + interval;
                    }
                }
                return -1;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(title, task.title)
                && Objects.equals(active, task.active)
                && Objects.equals(repeat, task.repeat)
                && Objects.equals(time, task.time);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 28 * result + title.hashCode();
        result = 28 * result + time;
        result = 28 * result + (repeat ? 1 : 0);
        result = 28 * result + (active ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        if (repeat) {
            return "Title=" + getTitle()
                    + " Active=" + isActive()
                    + " Repeated=" + isRepeated()
                    + " Time=" + getTime();
        } else {
            return "Title=" + getTitle()
                    + " Active=" + isActive()
                    + " Repeated=" + isRepeated()
                    + " Start time=" + getStartTime()
                    + " End time=" + getEndTime()
                    + " Interval=" + getRepeatInterval()
                    + " Time=" + getTime();
        }
    }

    @Override
    public Task clone() {
        Task result = null;
        try {
            result = (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (result != null) {
            try {
                result.setTitle(getTitle());
                result.time = getTime();
                result.start = getStartTime();
                result.end = getEndTime();
                result.interval = getRepeatInterval();
                result.setActive(isActive());
                result.repeat = isRepeated();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
