package com.example.leeseonwoo.studydirector;

/**
 * Created by leeseonwoo on 2018. 5. 8..
 */

public class ListViewItem {
    private int imageID;
    private String titleStr;
    private String timeStr;
    private String pageStr;
    private int checkID;
    private String checkStr;
    private boolean checked;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getPageStr() {
        return pageStr;
    }

    public void setPageStr(String pageStr) {
        this.pageStr = pageStr;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) { this.checkID = checkID; }

    public String getCheckStr() {
        return checkStr;
    }

    public void setCheckStr(String checkStr) {
        this.checkStr = checkStr;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return checked;
    }
}
