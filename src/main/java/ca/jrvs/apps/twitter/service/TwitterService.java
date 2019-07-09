package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dto.Tweet;

import java.util.List;

public interface TwitterService {
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
    Tweet postTweet(String text, Double latitude, Double longitude);

    /**
     * Search a Tweet by id and print Tweet Object which is returned by the Twitter API
     *
     * @param id     tweet id
     * @param fields print Tweet fields from this parameter. Print all fields if empty
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    Tweet showTweet(String id, String[] fields);

    /**
     * Delete Tweet(s) by id(s).
     * Print Tweet object(s) which returned by the Twitter API
     *
     * @param ids tweet IDs which will be deleted
     * @return Tweet objects that were deleted through the Twitter API
     * @throws IllegalArgumentException if one of the IDS is invalid
     */
    List<Tweet> deleteTweets(String[] ids);
}
