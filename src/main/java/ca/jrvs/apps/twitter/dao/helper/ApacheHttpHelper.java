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

import java.io.IOException;
import java.net.URI;

public class ApacheHttpHelper implements HttpHelper {

    private static String CONSUMER_KEY = System.getenv("CONSUMER_KEY");
    private static String CONSUMER_SECRET = System.getenv("CONSUMER_SECRET");
    private static String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
    private static String TOKEN_SECRET = System.getenv("TOKEN_SECRET");

    @Override
    public HttpResponse httpPost(URI uri) {
        return httpRequest(new HttpPost(uri));
    }

    private HttpResponse httpRequest(HttpUriRequest request) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            consumer.sign(request);
            return httpClient.execute(request);
        } catch (IOException | OAuthException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        return httpRequest(new HttpGet(uri));
    }
}
