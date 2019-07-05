package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdRepository;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;

public class TwitterServiceImp implements TwitterService {

    private CrdRepository<Tweet, String> crdRepository;

    public TwitterServiceImp(CrdRepository<Tweet, String> crdRepository) {
        this.crdRepository = crdRepository;
    }

    @Override
    public void postTweet(String text, Double latitude, Double longitude) {
        validateTextLength(text);
        Tweet tweet = new Tweet();
        tweet.setText(text);
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{latitude, longitude});
        tweet.setCoordinates(coordinates);
        crdRepository.save(tweet);
    }

    private void validateTextLength(String text) {
        String textWithoutSpaces = text.replaceAll("\\s", "");
        if (textWithoutSpaces.length() > 140) {
            throw new IllegalArgumentException("A tweet can only contain 140 characters.");
        }
    }

    @Override
    public void showTweet(String id, String[] fields) {
        boolean validated = validateId(id);
        if (validated) {

        }
    }

    private boolean validateId(String id) {
        char[] str = id.toCharArray();
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            if (c < '0' || c > '9') {
                throw new IllegalArgumentException("Twitter ids only contain digits.");
            }
        }
        return true;
    }

    @Override
    public void deleteTweets(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            boolean validated = validateId(ids[i]);
            if (validated) {
                crdRepository.deleteById(ids[i]);
            }
        }
    }
}
