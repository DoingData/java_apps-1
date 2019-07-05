package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Tweet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterRestDao implements CrdRepository<Tweet, String> {

    private HttpHelper httpHelper;
    private final String STATUSLINE_OK = "HTTP/1.1 200 OK";

    public TwitterRestDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    public static void main(String[] args) {
        TwitterRestDao t = new TwitterRestDao(new ApacheHttpHelper());
        Tweet tweet = t.findById("1146478188878532608");
        System.out.println(tweet.getText());
    }

    @Override
    public Tweet findById(String s) {
        validateId(s);
        HttpResponse httpResponse = httpHelper.httpGet(createFindURI(s));
        String statusLine = httpResponse.getStatusLine().toString();
        if (statusLine.equals(STATUSLINE_OK)) {
            String responseInJson = getResponseInJson(httpResponse);
            return jsonToTweet(responseInJson);
        } else {
            throw new RuntimeException("Could not find tweet. " + statusLine);
        }
    }

    private void validateId(String s) {
        for (char c : s.toCharArray()) {
            if (c > '9' || c < '0') {
                throw new IllegalArgumentException("Input is not a valid id.");
            }
        }
    }

    private URI createFindURI(String s) {
        try {
            return new URI("https://api.twitter.com/1.1/statuses/show.json?id=" + s);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getResponseInJson(HttpResponse httpResponse) {
        try {
            return EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Tweet jsonToTweet(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, Tweet.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tweet create(Tweet entity) {
        return null;
    }

    @Override
    public Tweet deleteById(String s) {
        return null;
    }
}
