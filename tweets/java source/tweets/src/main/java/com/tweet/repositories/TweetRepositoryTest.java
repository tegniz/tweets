package com.tweet.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tweet.domain.Tweet;

public class TweetRepositoryTest {
	@Autowired
    private TweetRepository tweetRepository;
 
 
    @Before
    public void setUp() throws Exception {

    }
 
    @Test
    public void testFetchData(){
        /*Test data retrieval*/
    	Iterable<Tweet> tweets = tweetRepository.findAll();
    	
        int count = 0;
        for (Iterator<Tweet> iterator = tweets.iterator(); iterator.hasNext();) {
			if ( iterator.hasNext() )
				count++;
		}
        assertEquals(count, 50);
    }
 
    @Test
    public void testDataUpdate(){
        
    }
 
    @After
    public void tearDown() throws Exception {
      
    }
}
