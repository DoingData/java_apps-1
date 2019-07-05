package ca.jrvs.apps.twitter.dao.helper;

import org.apache.http.HttpResponse;

import java.net.URI;

public interface HttpHelper {
    HttpResponse httpPost(URI uir);
    //HttpResponse httpPost(URI uri, StringEntity stringEntity);
    HttpResponse httpGet(URI uri);
}
