package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ApacheHttpHelper implements HttpHelper {

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public ApacheHttpHelper() {
        String consumerKey = System.getenv("CONSUMER_KEY");
        String consumerSecret = System.getenv("CONSUMER_SECRET");
        String accessToken = System.getenv("ACCESS_TOKEN");
        String tokenSecret = System.getenv("TOKEN_SECRET");
        if (consumerKey == null || consumerSecret == null || accessToken == null || tokenSecret == null) {
            throw new RuntimeException("Cannot find complete authentication information for Twitter. " + "Please set your environment variables.");
        }
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = new DefaultHttpClient();
    }

    /**
     * Gets a http response for a http post request
     *
     * @param uri uri for the post request
     * @return http response
     */
    @Override
    public HttpResponse httpPost(URI uri) {
        return httpRequest(new HttpPost(uri));
    }

    /**
     * Gets a http response for a http request
     * @param request http request
     * @return http response
     */
    private HttpResponse httpRequest(HttpUriRequest request) {
        try {
            consumer.sign(request);
            return httpClient.execute(request);
        } catch (IOException | OAuthException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets http response for a http get request
     * @param uri uri for the get request
     * @return http response
     */
    @Override
    public HttpResponse httpGet(URI uri) {
        return httpRequest(new HttpGet(uri));
    }
}
