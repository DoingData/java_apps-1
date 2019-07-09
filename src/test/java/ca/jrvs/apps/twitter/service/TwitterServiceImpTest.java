package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdRepository;
import ca.jrvs.apps.twitter.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceImpTest {
    @InjectMocks
    private TwitterServiceImp service;
    @Mock
    private CrdRepository mockDao;

    private Tweet mockTweet;

    @Before
    public void setup() {
        mockTweet = new Tweet();
        mockTweet.setText("This is a fake tweet");
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{1, 2});
        mockTweet.setCoordinates(coordinates);
        Entities entities = new Entities();
        Hashtag hashtag = new Hashtag();
        hashtag.setText("test");
        entities.setHashtags(new Hashtag[]{hashtag});
        UserMention userMention = new UserMention();
        userMention.setScreen_name("user_mention");
        entities.setUser_mentions(new UserMention[]{userMention});
        mockTweet.setEntities(entities);
    }

    @Test
    public void postTweet() {

        //Replaced by @Mock @InjectMocks
        /*CrdRepository mockDao = Mockito.mock(CrdRepository.class);
        TwitterService service = new TwitterServiceImp(mockDao);*/
        when(mockDao.create(any())).thenReturn(mockTweet);

        service.postTweet("some tweet", 0.0, 0.0);

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
        //service.showTweet("123", new String[]{"text", "coordinates", "user mention"});
        service.showTweet("123", null);
    }

    @Test
    public void deleteTweet() {
        try {
            service.deleteTweets(new String[]{"17643", "739e"});
            fail();
        } catch (IllegalArgumentException e) {

        }
        when(mockDao.deleteById(any())).thenReturn(mockTweet);
        service.deleteTweets(new String[]{"376642837"});
    }
}