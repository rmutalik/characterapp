package com.example.characterapp;

import androidx.annotation.NonNull;

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

    // Override equals() to compare two Item objects based on their field values
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item))
            return false;

        Item other = (Item) obj;
        return this.name.equals(other.name) && this.currentMessage.equals(other.currentMessage);
    }

    // Override hashCode() to ensure objects' hash codes are the same when compared for equality
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.currentMessage.hashCode();
        return result;
    }

    @NonNull
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
