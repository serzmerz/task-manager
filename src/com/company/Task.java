package com.company;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private boolean active;
    private boolean repeat;
    private Date time = new Date(0);
    private Date start = new Date(0);
    private Date end = new Date(0);
    private int interval;

    public Task(String title, Date time) throws Exception {
        this.setTitle(title);
        this.setTime(time);
    }

    public Task(String title, Date start, Date end, int interval) throws Exception {
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

    public Date getTime() {
        if (repeat) {
            return new Date(start.getTime());
        } else {
            return new Date(time.getTime());
        }
    }

    public void setTime(Date time) {
        this.repeat = false;
        this.time.setTime(time.getTime());
    }

    public Date getStartTime() {
        if (!repeat) return new Date(time.getTime());
        else return new Date(start.getTime());
    }

    public Date getEndTime() {
        if (!repeat) return new Date(time.getTime());
        else return new Date(end.getTime());
    }

    public int getRepeatInterval() {
        if (!repeat) return 0;
        else return interval;
    }

    public void setTime(Date start, Date end, int interval) throws Exception {
        if (interval <= 0) {
            throw new Exception("Interval can not be 0 or < 0");
        }

        if (start.after(end)) {
            throw new Exception("Start time can not be > endTime");
        }

        if (interval >= end.getTime() - start.getTime()) {
            throw new Exception("Interval can not be >= EndTime - Time");
        }
        this.start.setTime(start.getTime());
        this.end.setTime(end.getTime());
        this.interval = interval;
        this.repeat = true;
    }

    public boolean isRepeated() {
        return repeat;
    }

    public Date nextTimeAfter(Date current) {
        if (this.isActive()) {
            if (!this.isRepeated()) {
                if (this.getStartTime().after(current)) {
                    return this.getStartTime();
                } else return null;
            } else {
                for (long i = start.getTime(); i <= end.getTime(); i = i + interval * 1000) {
                    if (i > current.getTime()) return new Date(i);
                    if (i + interval * 1000 > current.getTime() && i + interval * 1000 <= end.getTime()) {
                        return new Date(i + interval * 1000);
                    }
                }
                return null;
            }
        }
        return null;
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
        result = 28 * result + (int) time.getTime();
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
