package org.thejava.assignment.model;

public enum Topics {
    Todo("todo"),
    Done("done"),
    Deleted("delete");
    private String topic;

    Topics(String topic){
        this.topic = topic;
    }
    public String getTopic(){return this.topic;}
}
