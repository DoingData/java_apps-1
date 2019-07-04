package ca.jrvs.apps.twitter.dto;

public class Entities {
    private Hashtag[] hashtags;
    private UserMention[] user_mentions;

    public Hashtag[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(Hashtag[] hashtags) {
        this.hashtags = hashtags;
    }

    public UserMention[] getUser_mentions() {
        return user_mentions;
    }

    public void setUser_mentions(UserMention[] user_mentions) {
        this.user_mentions = user_mentions;
    }
}
