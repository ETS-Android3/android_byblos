package com.example.segproject;

public class Feedback {

    String userID;
    String branchID;
    String feedbackID;
    String comment;
    float rating;
    public Feedback() {
    }

    public Feedback(String userID, String branchID, String comment, float rating, String feedbackID) {
        this.userID = userID;
        this.branchID = branchID;
        this.comment = comment;
        this.rating = rating;
        this.feedbackID = feedbackID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBranchID() {
        return branchID;
    }

    public String getComment() {
        return comment;
    }

    public float getRating() {
        return rating;
    }

    public String getFeedbackID() {
        return feedbackID;
    }
}
