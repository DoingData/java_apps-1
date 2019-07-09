package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TwitterRestDaoIntTest {

    private Tweet expectedTweet;
    private CrdDao dao;

    @Before
    public void setup() {
        expectedTweet = new Tweet();
        String tweetText = "this is a test #testing " + System.currentTimeMillis();
        expectedTweet.setText(tweetText);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{15.3, 23.2});
        coordinates.setType("Point");
        expectedTweet.setCoordinates(coordinates);

        HttpHelper httpHelper = new ApacheHttpHelper();
        dao = new TwitterRestDao(httpHelper);
    }

    @Test
    public void test() {
        //test create()
        Tweet actualTweet = (Tweet) dao.create(expectedTweet);
        assertTweets(expectedTweet, actualTweet);

        //test findById()
        Tweet showTweet = (Tweet) dao.findById(actualTweet.getId_str());
        assertTweets(expectedTweet, showTweet);

        //test deleteById()
        Tweet deletedTweet = (Tweet) dao.deleteById(actualTweet.getId_str());
        assertTweets(expectedTweet, deletedTweet);
    }

    public void assertTweets(Tweet expected, Tweet actual) {
        assertNotNull(actual);
        assertNotNull(actual.getId_str());
        assertEquals(actual.getText(), expectedTweet.getText());
        assertEquals(actual.getCoordinates(), expectedTweet.getCoordinates());
    }
}