package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.service.TwitterServiceImp;

public class TwitterCLI {
    public static void main(String[] args) {
        HttpHelper httpHelper = new ApacheHttpHelper();
        CrdDao crdDao = new TwitterRestDao(httpHelper);
        TwitterService twitterService = new TwitterServiceImp(crdDao);
        TwitterCLIRunner twitterCLIRunner = new TwitterCLIRunner(twitterService);

        twitterCLIRunner.run(args);
    }
}
