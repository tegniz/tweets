package com.tweet.converters;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tweet.commands.TweetForm;
import com.tweet.domain.Tweet;

@Component
public class TweetFormToTweet implements Converter<TweetForm, Tweet> {

    @Override
    public Tweet convert(TweetForm tweetForm) {
        Tweet tweet = new Tweet();
        if (tweetForm.getId() != null  && !StringUtils.isEmpty(tweetForm.getId())) {
            tweet.setId(new ObjectId(tweetForm.getId()));
        }
        tweet.setStartdate(tweetForm.getStartdate());
        tweet.setDescription(tweetForm.getDescription());
        return tweet;
    }
}