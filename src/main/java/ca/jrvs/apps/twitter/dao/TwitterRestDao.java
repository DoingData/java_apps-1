package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
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

    @Autowired
    public TwitterRestDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Finds a tweet by id
     *
     * @param id id of tweet
     * @return corresponding tweet
     */
    @Override
    public Tweet findById(String id) {
        HttpResponse httpResponse = httpHelper.httpGet(generateFindUri(id));
        return extractTweetFromResponse(httpResponse);
    }

    /**
     * Creates the URI to find a tweet
     *
     * @param id id of tweet
     * @return uri
     */
    private URI generateFindUri(String id) {
        try {
            StringBuilder uri = new StringBuilder(BASE_URI).append(FIND_URI);
            appendQuery(uri, "id", id, true);
            return new URI(uri.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to construct URI.", e);
        }
    }

    /**
     * Converts http response to a tweet
     *
     * @param response http response
     * @return tweet
     */
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

    /**
     * Appends a parameter pair to a uri
     * @param s uri where parameter pair is to be added
     * @param key key of the parameter pair
     * @param value values of parameter pair
     * @param firstParameter true if it is the first parameter pair in the uri
     */
    private void appendQuery(StringBuilder s, String key, String value, boolean firstParameter) {
        if (firstParameter) {
            s.append(QUERY_SYMBOL);
        } else {
            s.append(AND);
        }
        s.append(key).append(EQUAL).append(value);
    }

    /**
     * Posts a tweet on twitter
     *
     * @param tweet tweet to be posted
     * @return posted tweet
     */
    @Override
    public Tweet create(Tweet tweet) {
        URI uri = null;
        try {
            uri = generatePostUri(tweet);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid tweet.", e);
        }
        HttpResponse httpResponse = httpHelper.httpPost(uri);
        return extractTweetFromResponse(httpResponse);
    }

    /**
     * Creates URI to post a tweet
     * @param tweet tweet to be posted
     * @return uri
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    private URI generatePostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {
        StringBuilder uri = new StringBuilder(BASE_URI).append(POST_URI);
        Double latitude = tweet.getCoordinates().getCoordinates()[1];
        Double longitude = tweet.getCoordinates().getCoordinates()[0];
        String text = URLEncoder.encode(tweet.getText(), StandardCharsets.UTF_8.name());
        appendQuery(uri, "status", text, true);
        appendQuery(uri, "lat", latitude.toString(), false);
        appendQuery(uri, "long", longitude.toString(), false);
        return new URI(uri.toString());
    }

    /**
     * Deletes a tweet on twitter
     * @param id id of tweet
     * @return deleted tweet
     */
    @Override
    public Tweet deleteById(String id) {
        HttpResponse response = httpHelper.httpPost(generateDeleteUri(id));
        return extractTweetFromResponse(response);
    }

    /**
     * Creates URI to delete a tweet
     *
     * @param id id of tweet
     * @return uri
     */
    private URI generateDeleteUri(String id) {
        try {
            StringBuilder uri = new StringBuilder(BASE_URI).append(DELETE_URI).append(id).append(".json");
            return new URI(uri.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to construct URI.", e);
        }
    }
}
