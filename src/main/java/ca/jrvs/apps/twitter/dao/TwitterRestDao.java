package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwitterRestDao implements CrdRepository<Tweet, String> {
    //URIs
    private static final String BASE_URI = "https://api.twitter.com/1.1/statuses/";
    private static final String FIND_URI = "show.json";
    private static final String POST_URI = "update.json";
    private static final String DELETE_URI = "destroy/";
    //URI symbols
    private static final String QUERY_SYMBOL = "?";
    private static final String AND = "&";
    private static final String EQUAL = "=";
    //Statuscode
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    public TwitterRestDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    public static void main(String[] args) {
        TwitterRestDao t = new TwitterRestDao(new ApacheHttpHelper());
        Tweet tweet = t.findById("1147219416570040321");
        System.out.println(tweet.getText());
//        Tweet post = new Tweet();
//        post.setText("more testing #testing @Pranavi30317716");
//        Coordinates coordinates = new Coordinates();
//        coordinates.setCoordinates(new double[]{79.38, -44.1});
//        post.setCoordinates(coordinates);
//        Tweet tweet2 = t.save(post);
//        System.out.println(tweet2.getText());
//        //Tweet tweet3 = t.deleteById("1147228202462535681");
        StringBuilder s = new StringBuilder("abc");
        for (int i = 1; i <= 140; i++) {
            s.append(" t");
        }
        s.append("abc");
        Tweet post = new Tweet();
        post.setText(s.toString());
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(new double[]{79.38, -44.1});
        post.setCoordinates(coordinates);
        t.save(post);

    }

    @Override
    public Tweet findById(String s) {
        HttpResponse httpResponse = httpHelper.httpGet(generateFindUri(s));
        return extractTweetFromResponse(httpResponse);
    }

    @Override
    public Tweet save(Tweet entity) {
        URI uri = null;
        try {
            uri = generatePostUri(entity);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid tweet.", e);
        }
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        return extractTweetFromResponse(httpResponse);
    }

    private URI generateFindUri(String s) {
        try {
            StringBuilder uri = new StringBuilder(BASE_URI).append(FIND_URI);
            appendQuery(uri, "id", s, true);
            return new URI(uri.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to construct URI.", e);
        }
    }

    private void appendQuery(StringBuilder s, String key, String value, boolean firstParameter) {
        if (firstParameter) {
            s.append(QUERY_SYMBOL);
        } else {
            s.append(AND);
        }
        s.append(key).append(EQUAL).append(value);
    }

    private URI generatePostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {
        StringBuilder uri = new StringBuilder(BASE_URI).append(POST_URI);
        Double latitude = tweet.getCoordinates().getCoordinates()[0];
        Double longitude = tweet.getCoordinates().getCoordinates()[1];
        String text = URLEncoder.encode(tweet.getText(), StandardCharsets.UTF_8.name());
        appendQuery(uri, "status", text, true);
        appendQuery(uri, "lat", latitude.toString(), false);
        appendQuery(uri, "long", longitude.toString(), false);
        return new URI(uri.toString());
    }

    private Tweet extractTweetFromResponse(HttpResponse response) {
        Tweet tweet = null;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HTTP_OK) {
            throw new RuntimeException("Unexpected HTTP status " + statusCode);
        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }
        String responseInJson;
        try {
            responseInJson = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert response to string.", e);
        }
        try {
            tweet = JsonUtil.toObjectFromJson(responseInJson, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON string to object.", e);
        }
        return tweet;
    }

    @Override
    public Tweet deleteById(String s) {
        HttpResponse response = httpHelper.httpPost(generateDeleteUri(s));
        return extractTweetFromResponse(response);
    }

    private URI generateDeleteUri(String s) {
        try {
            StringBuilder uri = new StringBuilder(BASE_URI).append(DELETE_URI).append(s).append(".json");
            return new URI(uri.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to construct URI.", e);
        }
    }
}
