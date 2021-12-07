package com.example.segproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.TestCase;

import static org.junit.Assert.*;

import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class LocalTests extends TestCase {

    String address = "75 street city country";
    String service = "truck rental, car rental";
    String hours = "Wednesday: 9:00am - 5:00pm";

    String invalidSearch = "invalid";


    String validComment = "Thank you for the lovely service!";
    String emptyComment = "";

    String fuckComment = "this service fucking sucked";
    String shitComment = "this service was shit!";
    String bitchComment = "worker was a bitch!";
    String assComment = "the service was ass!";

    @Test
    public void validAddressMatch(){
        boolean validResult;
        validResult = CustomerWelcomeActivity.match(address, service, hours ,"street");
        assertTrue(validResult);
    }
    @Test
    public void validServiceMatch(){
        boolean validResult;
        validResult = CustomerWelcomeActivity.match(address, service, hours ,"Truck");
        assertTrue(validResult);
    }
    @Test
    public void validHoursMatch(){
        boolean validResult;
        validResult = CustomerWelcomeActivity.match(address, service, hours ,"Wednesday");
        assertTrue(validResult);
    }

    @Test
    public void invalidMatchTest(){
        boolean invalidResult;
        invalidResult = CustomerWelcomeActivity.match(address, service, hours ,invalidSearch);
        assertFalse(invalidResult);
    }

    @Test
    public void validCommentFeedback(){
        boolean validResult;
        validResult = FeedbackPage.validComment(validComment);
        assertTrue(validResult);
    }

    @Test
    public void emptyCommentFeedback(){
        boolean Result;
        Result = FeedbackPage.validComment(emptyComment);
        assertFalse(Result);
    }

    @Test
    public void fuckCommentFeedback(){
        boolean Result;
        Result = FeedbackPage.validComment(fuckComment);
        assertFalse(Result);
    }

    @Test
    public void shitCommentFeedback(){
        boolean Result;
        Result = FeedbackPage.validComment(shitComment);
        assertFalse(Result);
    }

    @Test
    public void assCommentFeedback(){
        boolean Result;
        Result = FeedbackPage.validComment(assComment);
        assertFalse(Result);
    }

    @Test
    public void bitchCommentFeedback(){
        boolean Result;
        Result = FeedbackPage.validComment(bitchComment);
        assertFalse(Result);
    }


}