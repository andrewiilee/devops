package com.example.model;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class SFOrder {
    private String id;
    private Date created;
    private Date modified;
    private int priority;
    private String path;                //absolute path to order directory
    private OrderState state;

    private int fileCount;
    private List<SFFile> sfFileList;

    public SFOrder() {}
    public SFOrder(String id,
                   Date created,
                   Date modified,
                   int priority,
                   String path,
                   OrderState state,
                   List<SFFile> files) {
        this.id = id;
        this.created = new Date(created.getTime());
        this.modified = new Date(modified.getTime());
        this.priority = priority;
        this.path = path;
        this.state = state;
        fileCount = files.size();
        this.sfFileList = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    public void setCreated(Date created) {
        this.created = new Date(created.getTime());
    }

    public Date getModified() {
        return new Date(modified.getTime());
    }

    public void setModified(Date modified) {
        this.modified = new Date(modified.getTime());
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public List<SFFile> getSFFileList() {
        return sfFileList;
    }

    public void setSFFileList(List<SFFile> sfFileList) {
        this.sfFileList = sfFileList;
    }
}
