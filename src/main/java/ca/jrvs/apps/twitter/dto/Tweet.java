package ca.jrvs.apps.twitter.dto;

import java.math.BigInteger;

public class Tweet {
    private String created_at;
    private BigInteger id;
    private String id_str;
    private String text;
    private Entities entities;
    private Coordinates coordinates;
    private int retweet_count;
}
