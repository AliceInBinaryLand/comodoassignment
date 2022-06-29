package org.thejava.assignment.model;

public enum Status {
    Todo(0),
    Done(1),
    Deleted(2);
    private long status;

    Status(long status){
        this.status = status;
    }
    public long getStatus(){return this.status;}
}
