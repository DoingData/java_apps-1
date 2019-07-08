package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdRepository;
import ca.jrvs.apps.twitter.dto.Tweet;
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

    @Test
    public void postTweet() {

        //Replaced by @Mock @InjectMocks
        /*CrdRepository mockDao = Mockito.mock(CrdRepository.class);
        TwitterService service = new TwitterServiceImp(mockDao);*/
        Tweet mockTweet = new Tweet();
        mockTweet.setText("This is a fake tweet");
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
    }

    @Test
    public void deleteTweet() {
        try {
            service.deleteTweets(new String[]{"17643", "739e"});
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
}