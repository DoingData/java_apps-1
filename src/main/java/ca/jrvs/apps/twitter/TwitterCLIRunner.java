package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.service.TwitterServiceImp;

public class TwitterCLIRunner {

    private TwitterService twitterService;

    public TwitterCLIRunner(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public static void main(String[] args) {
        TwitterCLIRunner runner = new TwitterCLIRunner(new TwitterServiceImp(new TwitterRestDao(new ApacheHttpHelper())));
        runner.showTweet("1147229192389574656", null);
    }

    /**
     * Shows the specified fields of a tweet
     *
     * @param id    id of tweet
     * @param field comma-separated list of fields from the tweet object (can be null)
     */
    private void showTweet(String id, String field) {
        if (field == null) {
            twitterService.showTweet(id, null);
        } else {
            String[] fields = field.split(",");
            twitterService.showTweet(id, fields);
        }
    }

    public void run(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("This app cannot run without input arguments.");
        }
        String command = args[0].toLowerCase();
        switch (command) {
            case "post":
                if (args.length != 3) {
                    throw new IllegalArgumentException("Usage: post \"tweet_text\" \"latitude:longitude\"");
                }
                postTweet(args[1], args[2]);
                break;
            case "show":
                if (args.length != 3 && args.length != 2) {
                    throw new IllegalArgumentException("Usage: show id [field1,field2,...].");
                }
                if (args.length == 3) {
                    showTweet(args[1], args[2]);
                } else {
                    showTweet(args[1], null);
                }
                break;
            case "delete":
                if (args.length != 2) {
                    throw new IllegalArgumentException("Usage: delete id1,id2,id3,...");
                }
                deleteTweets(args[1]);
                break;
            default:
                throw new IllegalArgumentException("The three possible commands are post, show, and delete.");
        }
    }

    /**
     * Posts a tweet with the given text and coordinates
     *
     * @param text        text of tweet
     * @param coordinates latitude:longitude
     */
    private void postTweet(String text, String coordinates) {
        String[] coord = coordinates.split(":");
        if (coord.length != 2) {
            throw new IllegalArgumentException("The format for the coordinates is latitude:longitude.");
        }
        double latitude = new Double(coord[0]);
        double longitude = new Double(coord[1]);
        twitterService.postTweet(text, latitude, longitude);
    }

    /**
     * Deletes tweets
     *
     * @param idString comma-separated list of ids of tweets
     */
    private void deleteTweets(String idString) {
        String[] ids = idString.split(",");
        twitterService.deleteTweets(ids);
    }
}