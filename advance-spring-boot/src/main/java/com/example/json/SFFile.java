package com.example.json;

import com.example.enumeration.FileState;
import java.util.Date;

/**
 *
 */
public class SFFile {
    private String id;
    private int priority;
    private Date received;
    private String filePath;
    private FileState state;

    public SFFile() {}
    public SFFile(String id,
                  int priority,
                  Date received,
                  String filePath,
                  FileState state) {
        this.id = id;
        this.priority = priority;
        this.received = new Date(received.getTime());
        this.state = state;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getReceived() {
        return new Date(received.getTime());
    }

    public void setReceived(Date received) {
        this.received = new Date(received.getTime());
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileState getState() {
        return state;
    }

    public void setState(FileState state) {
        this.state = state;
    }
}
