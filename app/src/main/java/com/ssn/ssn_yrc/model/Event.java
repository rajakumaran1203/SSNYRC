
package com.ssn.ssn_yrc.model;

public class Event {

    private String title;
    private int drawableId;

    public Event(String title, int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }
}
