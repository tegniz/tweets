package com.tweet.services;

import com.tweet.commands.TweetForm;
import com.tweet.domain.Tweet;

import java.util.List;

public interface TweetService {

    List<Tweet> listAll();

    Tweet getById(String id);

    Tweet saveOrUpdate(Tweet tweet);

    void delete(String id);

    Tweet saveOrUpdateTweetForm(TweetForm tweetForm);
    
}
