package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdRepository;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TwitterServiceImp implements TwitterService {

    private static final int MAX_TWEET_LENGTH = 140;

    private CrdRepository<Tweet, String> crdRepository;

    public TwitterServiceImp(CrdRepository<Tweet, String> crdRepository) {
        this.crdRepository = crdRepository;
    }

    /**
     * Prints a tweet
     *
     * @param id     id of the tweet
     * @param fields names of the fields to be shown
     */
    @Override
    public void showTweet(String id, String[] fields) {
        validateId(id);
        Tweet tweet = crdRepository.findById(id);
        printTweet(tweet, fields);
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
            if (!(fieldsList.contains("hashtags") && fieldsList.contains("user mention"))) {
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
     * Posts a new tweet with a location on twitter
     *
     * @param text      the text of the tweet
     * @param latitude  latitude of the location
     * @param longitude longitude of the location
     */
    @Override
    public void postTweet(String text, Double latitude, Double longitude) {
        validateTweetText(text);
        validateLatitude(latitude);
        validateLongitude(longitude);
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{longitude, latitude});
        tweet.setCoordinates(coordinates);
        crdRepository.create(tweet);
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
     * Deletes a tweet on twitter
     *
     * @param ids id of the tweet
     */
    @Override
    public void deleteTweets(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            validateId(ids[i]);
            crdRepository.deleteById(ids[i]);
        }
    }
}
