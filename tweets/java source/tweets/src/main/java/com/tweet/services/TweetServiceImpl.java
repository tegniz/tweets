package com.tweet.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweet.commands.TweetForm;
import com.tweet.converters.TweetFormToTweet;
import com.tweet.domain.Tweet;
import com.tweet.repositories.TweetRepository;

@Service
public class TweetServiceImpl implements TweetService {

    private TweetRepository tweetRepository;
    private TweetFormToTweet tweetFormToTweet;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, TweetFormToTweet tweetFormToTweet) {
        this.tweetRepository = tweetRepository;
        this.tweetFormToTweet = tweetFormToTweet;
    }


    @Override
    public List<Tweet> listAll() {
        List<Tweet> tweets = new ArrayList<>();
        tweetRepository.findAll().forEach(tweets::add); 
        return tweets;
    }

    @Override
    public Tweet getById(String id) {
        return tweetRepository.findById(id).orElse(null);
    }

    @Override
    public Tweet saveOrUpdate(Tweet tweet) {
        tweetRepository.save(tweet);
        return tweet;
    }

    @Override
    public void delete(String id) {
        tweetRepository.deleteById(id);
    }

    @Override
    public Tweet saveOrUpdateTweetForm(TweetForm tweetForm) {
        Tweet savedTweet = saveOrUpdate(tweetFormToTweet.convert(tweetForm));

        System.out.println("Saved Tweet Id: " + savedTweet.getId());
        return savedTweet;
    }

}