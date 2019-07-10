package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceImpTest {
    @InjectMocks
    private TwitterServiceImp service;
    @Mock
    private CrdDao mockDao;

    private Tweet mockTweet;

    @Before
    public void setup() {
        mockTweet = new Tweet();
        mockTweet.setText("This is a fake tweet");
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{1, 2});
        mockTweet.setCoordinates(coordinates);
        Entities entities = new Entities();
        entities.setHashtags(new Hashtag[]{new Hashtag("tag1"), new Hashtag("tag2")});
        entities.setUser_mentions(new UserMention[]{new UserMention("mention"), new UserMention("another_one")});
        mockTweet.setEntities(entities);
    }

    @Test
    public void postTweet() {

        //Replaced by @Mock @InjectMocks
        /*CrdDao mockDao = Mockito.mock(CrdDao.class);
        TwitterService service = new TwitterServiceImp(mockDao);*/
        when(mockDao.create(any())).thenReturn(mockTweet);

        Tweet tweet = service.postTweet("some tweet", 0.0, 0.0);

        try {
            service.postTweet("", 0.0, 0.0);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            service.postTweet("hello", 345.3, 237.744);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void showTweet() {
        try {
            service.showTweet("876345h", new String[]{"text"});
            fail();
        } catch (IllegalArgumentException e) {
        }
        when(mockDao.findById(any())).thenReturn(mockTweet);
        Tweet tweet = service.showTweet("123", null);
        assertNotNull(tweet);
        assertNotNull(tweet.getCoordinates());
        assertNotNull(tweet.getEntities());

        Tweet tweet1 = service.showTweet("2345", new String[]{"text", "id"});
        assertNotNull(tweet1);
        assertNotNull(tweet1.getText());
        assertEquals(null, tweet1.getEntities());
    }

    @Test
    public void deleteTweet() {
        try {
            service.deleteTweets(new String[]{"17643", "739e"});
            fail();
        } catch (IllegalArgumentException e) {
        }
        when(mockDao.deleteById(any())).thenReturn(mockTweet);
        List<Tweet> deletedTweets = service.deleteTweets(new String[]{"376642837"});
        assertNotNull(deletedTweets);
    }
}