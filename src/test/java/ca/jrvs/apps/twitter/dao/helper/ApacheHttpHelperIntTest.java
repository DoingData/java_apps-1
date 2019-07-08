package ca.jrvs.apps.twitter.dao.helper;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.*;

public class ApacheHttpHelperIntTest {

    private ApacheHttpHelper httpHelper;

    @Before
    public void setup() {
        httpHelper = new ApacheHttpHelper();
    }

    @Test
    public void httpPost() {
        try {
            HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=hello" + System.currentTimeMillis()));
            assertHttpResponse(200, response);
        } catch (URISyntaxException e) {
        }
    }

    private void assertHttpResponse(int expectedStatusCode, HttpResponse response) {
        assertNotNull(response);
        assertNotNull(response.getStatusLine());
        assertNotNull(response.getStatusLine().getStatusCode());
        assertEquals(expectedStatusCode, response.getStatusLine().getStatusCode());
    }

    @Test
    public void httpGet() {
        HttpResponse response;
        try {
            response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1146478188878532608"));
            assertHttpResponse(200, response);
            EntityUtils.toString(response.getEntity());
        } catch (URISyntaxException | IOException e) {
            fail();
        }
        try {
            response = httpHelper.httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1148313584662921216"));
            assertHttpResponse(404, response);
        } catch (URISyntaxException e) {
            fail();
        }
    }
}