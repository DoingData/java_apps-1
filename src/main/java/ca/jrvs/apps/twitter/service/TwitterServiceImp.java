package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdRepository;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;

import java.util.Arrays;

public class TwitterServiceImp implements TwitterService {

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
        if (fields == null || fields.length == 0) {
            fields = new String[]{"created at", "id", "text", "retweet count", "favorite count", "favorited", "retweeted", "coordinates", "user mentions"};
        }
        for (String s : fields) {
            switch (s.toLowerCase()) {
                case "created at":
                case "date":
                    System.out.println("Created at: " + tweet.getCreated_at());
                    break;
                case "id":
                    System.out.println("Id: " + tweet.getId_str());
                    break;
                case "text":
                    System.out.println("Text: " + tweet.getText());
                    break;
                case "retweet count":
                    System.out.println("Retweet count: " + tweet.getRetweet_count());
                    break;
                case "favorite count":
                    System.out.println("Favorite count: " + tweet.getFavorite_count());
                    break;
                case "favorited":
                case "liked":
                    System.out.println("Favorited: " + tweet.isFavorited());
                    break;
                case "retweeted":
                    System.out.println("Retweeted: " + tweet.isRetweeted());
                    break;
                case "coordinates":
                case "location":
                    StringBuilder coordinates = new StringBuilder("Coordinates: ");
                    Coordinates coord = tweet.getCoordinates();
                    if (coord == null) {
                        System.out.println(coordinates.toString());
                    } else {
                        coordinates.append(coord.getCoordinates()[0]).append(", ").append(coord.getCoordinates()[1]);
                        System.out.println(coordinates.toString());
                    }
                    break;
                case "hashtags":
                    System.out.print("Hashtags: ");
                    Arrays.stream(tweet.getEntities().getHashtags()).map(h -> h.getText()).forEach(x -> System.out.print("#" + x + " "));
                    System.out.print("\n");
                    break;
                case "user mentions":
                    System.out.print("User mentions: ");
                    Arrays.stream(tweet.getEntities().getUser_mentions()).map(m -> m.getScreen_name()).forEach(x -> System.out.print("@" + x + " "));
                    System.out.print("\n");
                    break;
                default:
                    System.out.println("This attribute does not exist: " + s);
            }
        }
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
     * Posts a new tweet with a location on twitter
     *
     * @param text      the text of the tweet
     * @param latitude  latitude of the location
     * @param longitude longitude of the location
     */
    @Override
    public void postTweet(String text, Double latitude, Double longitude) {
        validateTweetLength(text);
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
     * Checks that a tweet is not too long
     *
     * @param text text of the tweet
     */
    private void validateTweetLength(String text) {
        String textWithoutSpaces = text.replaceAll("\\s", "");
        if (textWithoutSpaces.length() > 140) {
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
