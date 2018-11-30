package com.tweet.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.tweet.commands.TweetForm;
import com.tweet.domain.Tweet;

@Component
public class TweetToTweetForm implements Converter<Tweet, TweetForm> {
    @Override
    public TweetForm convert(Tweet tweet) {
        TweetForm tweetForm = new TweetForm();
        tweetForm.setId(tweet.getId().toHexString());
        tweetForm.setStartdate(tweet.getStartdate());
        tweetForm.setDescription(tweet.getDescription());
        return tweetForm;
    }
}
