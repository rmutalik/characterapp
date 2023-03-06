package com.example.characterapp;

public class Item {
    private final String name;
    private String currentMessage = "";
    private String previousMessage = "";
    private boolean isUpdated;

    public Item(String name, String alertDialogMessage) {
        this.name = name;
        this.currentMessage = alertDialogMessage;
        this.isUpdated = false;
    }

    @Override
    public String toString() {
        return ("Name: " + name + ", " + "Message: " + currentMessage);
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public String getName() {
        return name;
    }

    public String getAlertDialogMessage() {
        return currentMessage;
    }

    public void setAlertDialogMessage(String alertDialogMessage) {
        this.currentMessage = alertDialogMessage;
    }

    public String getPreviousMessage() {
        return previousMessage;
    }

    public void setPreviousMessage(String previousMessage) {
        this.previousMessage = previousMessage;
    }

}
