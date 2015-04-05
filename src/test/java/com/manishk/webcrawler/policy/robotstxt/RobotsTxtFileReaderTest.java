package com.manishk.webcrawler.policy.robotstxt;

import org.junit.Assert;
import org.junit.Test;

public class RobotsTxtFileReaderTest {

    @Test
    public void testGetDisAllowedPathsForURL() throws Exception {
        RobotsTxtFileReader robotsTxtFileReader = new RobotsTxtFileReader(null);
        Assert.assertTrue(robotsTxtFileReader.getDisAllowedPathsForURL("").size() == 0);
    }

    @Test
    public void testGetDisAllowedPathsForNullURL() throws Exception {
        RobotsTxtFileReader robotsTxtFileReader = new RobotsTxtFileReader(null);
        boolean exception = false;
        try {
            robotsTxtFileReader.getDisAllowedPathsForURL(null);
        } catch (Exception e) {
            exception = true;
        }
        Assert.assertTrue(exception);
    }
}