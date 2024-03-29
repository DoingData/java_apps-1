package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIRunner;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.service.TwitterServiceImp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

//@Configuration
public class TwitterCLIBean {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIRunner runner = applicationContext.getBean(TwitterCLIRunner.class);
        runner.run(args);
    }

    @Bean
    public TwitterService twitterService(CrdDao crdDao) {
        return new TwitterServiceImp(crdDao);
    }

    @Bean
    public CrdDao crdDao(HttpHelper helper) {
        return new TwitterRestDao(helper);
    }

    @Bean
    public HttpHelper httpHelper() {
        return new ApacheHttpHelper();
    }

    @Bean
    public TwitterCLIRunner twitterCLIRunner(TwitterService twitterService) {
        return new TwitterCLIRunner(twitterService);
    }
}
