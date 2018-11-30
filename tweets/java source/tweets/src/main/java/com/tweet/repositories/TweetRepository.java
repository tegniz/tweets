package com.tweet.repositories;

import com.tweet.domain.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, String> {

}