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

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public ApacheHttpHelper() {
        consumer = new CommonsHttpOAuthConsumer(System.getenv("CONSUMER_KEY"), System.getenv("CONSUMER_SECRET"));
        consumer.setTokenWithSecret(System.getenv("ACCESS_TOKEN"), System.getenv("TOKEN_SECRET"));
        httpClient = new DefaultHttpClient();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        return httpRequest(new HttpPost(uri));
    }

    private HttpResponse httpRequest(HttpUriRequest request) {
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
