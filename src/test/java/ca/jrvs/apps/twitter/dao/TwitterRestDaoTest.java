package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterRestDaoTest {

    private Tweet expectedTweet;
    private CrdRepository dao;
    private String id;

    @Before
    public void setup() {
        expectedTweet = new Tweet();
        String tweetText = "this is a test expectedTweet " + System.currentTimeMillis();
        expectedTweet.setText(tweetText);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{15.3, 23.2});
        coordinates.setType("Point");
        expectedTweet.setCoordinates(coordinates);

        HttpHelper httpHelper = new ApacheHttpHelper();
        dao = new TwitterRestDao(httpHelper);
    }

    @After
    public void cleanup() {
        dao.deleteById(id);
    }

    @Test
    public void create() {
        Tweet actualTweet = (Tweet) dao.create(expectedTweet);
        id = actualTweet.getId_str();
        assertTweets(expectedTweet, actualTweet);

        Tweet showTweet = (Tweet) dao.findById(id);
        assertTweets(expectedTweet, showTweet);
    }

    public void assertTweets(Tweet expected, Tweet actual) {
        assertNotNull(actual);
        assertNotNull(actual.getId_str());
        assertEquals(actual.getText(), expectedTweet.getText());
        assertEquals(actual.getCoordinates(), expectedTweet.getCoordinates());
    }
}