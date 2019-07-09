package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TwitterServiceImp implements TwitterService {

    private static final int MAX_TWEET_LENGTH = 140;

    private CrdDao<Tweet, String> crdDao;

    @Autowired
    public TwitterServiceImp(CrdDao<Tweet, String> crdDao) {
        this.crdDao = crdDao;
    }

    /**
     * Search a Tweet by id and print Tweet Object which is returned by the Twitter API
     *
     * @param id     tweet id
     * @param fields print Tweet fields from this parameter. Print all fields if empty
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);
        Tweet tweet = crdDao.findById(id);
        printTweet(tweet, fields);
        return tweet;
    }

    /**
     * Checks that an id only contains digits
     *
     * @param id the string to be checked
     */
    private void validateId(String id) {
        char[] str = id.toCharArray();
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("Twitter ids only contain digits.");
            }
        }
    }

    /**
     * Helper method for printing a tweet
     *
     * @param tweet  tweet to print
     * @param fields names of the fields to be printed
     */
    private void printTweet(Tweet tweet, String[] fields) {
        if (!(fields == null || fields.length == 0)) {
            LinkedList<String> fieldsList = new LinkedList<>();
            fieldsList.addAll(Arrays.stream(fields).map(s -> s.trim().toLowerCase()).collect(Collectors.toList()));
            if (!fieldsList.contains("created at")) {
                tweet.setCreated_at(null);
            }
            if (!fieldsList.contains("id")) {
                tweet.setId(null);
                tweet.setId_str(null);
            }
            if (!fieldsList.contains("text")) {
                tweet.setText(null);
            }
            if (!fieldsList.contains("retweet count")) {
                tweet.setRetweet_count(null);
            }
            if (!fieldsList.contains("favorited count")) {
                tweet.setFavorite_count(null);
            }
            if (!fieldsList.contains("favorited")) {
                tweet.setFavorited(null);
            }
            if (!fieldsList.contains("retweeted")) {
                tweet.setRetweeted(null);
            }
            if (!fieldsList.contains("coordinates")) {
                tweet.setCoordinates(null);
            }
            if (!fieldsList.contains("hashtags") && !fieldsList.contains("user mentions")) {
                tweet.setEntities(null);
            } else if (!fieldsList.contains("hashtags")) {
                tweet.getEntities().setHashtags(null);
            } else if (!fieldsList.contains("user mentions")) {
                tweet.getEntities().setUser_mentions(null);
            }
        }
        try {
            System.out.println(JsonUtil.toPrettyJson(tweet));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot convert tweet to json string.", e);
        }
    }

    /**
     * Post a Tweet along with a geo location.
     * Print Tweet JSON which is returned by the Twitter API
     *
     * @param text      tweet text
     * @param latitude  geo latitude
     * @param longitude geo longitude
     * @return TWeet object which is returned by the Twitter API
     * @throws IllegalArgumentException if text exceeds max number ofallowed characters of lat/long is out of range
     */
    @Override
    public Tweet postTweet(String text, Double latitude, Double longitude) {
        validateTweetText(text);
        validateLatitude(latitude);
        validateLongitude(longitude);
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{longitude, latitude});
        tweet.setCoordinates(coordinates);
        return crdDao.create(tweet);
    }

    /**
     * Checks whether the text of the tweet is a valid one
     *
     * @param text text of the tweet
     */
    private void validateTweetText(String text) {
        if (text == "") {
            throw new IllegalArgumentException("A tweet must contain at least one character.");
        }
        String textWithoutSpaces = text.replaceAll("\\s", "");
        if (textWithoutSpaces.length() > MAX_TWEET_LENGTH) {
            throw new IllegalArgumentException("A tweet can only contain 140 characters.");
        }
    }

    /**
     * Checks whether the input is a valid latitude
     *
     * @param latitude latitude to check
     */
    private void validateLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("The latitude must be between -90 and 90.");
        }
    }

    /**
     * Checks whetherthe input is valid longitude
     *
     * @param longitude longitude to check
     */
    private void validateLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("The longitude must be between -180 and 180.");
        }
    }

    /**
     * Delete Tweet(s) by id(s).
     * Print Tweet object(s) which returned by the Twitter API
     *
     * @param ids tweet IDs which will be deleted
     * @return Tweet objects that were deleted through the Twitter API
     * @throws IllegalArgumentException if one of the IDS is invalid
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> deletedTweets = new LinkedList<Tweet>();
        for (int i = 0; i < ids.length; i++) {
            validateId(ids[i]);
            Tweet tweet = crdDao.deleteById(ids[i]);
            deletedTweets.add(tweet);
        }
        return deletedTweets;
    }
}
