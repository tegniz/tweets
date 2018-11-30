package com.tweet.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tweet.commands.TweetForm;
import com.tweet.converters.TweetToTweetForm;
import com.tweet.domain.Tweet;
import com.tweet.services.TweetService;

@CrossOrigin
@RestController
public class TweetController {

	private TweetService tweetService;

    private TweetToTweetForm tweetToTweetForm;
    
    @Autowired
    public void setTweetToTweetForm(TweetToTweetForm tweetToTweetForm) {
        this.tweetToTweetForm = tweetToTweetForm;
    }

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RequestMapping("/")
    public String redirToList(){
        return "redirect:/tweet/list";
    }

    @RequestMapping(value={"/tweet/list", "/tweet"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map> listTweets(Model model){
    	Map<String, Object> timeline = new HashMap<String, Object>();
    	
    	timeline.put("size_importance", "true");
    	timeline.put("initial_zoom", "20");
    	timeline.put("color", "#82530d");
    	timeline.put("focus_date", "2017-04-01 12:00");
    	timeline.put("id", "tweets");
    	timeline.put("title", "");
    	timeline.put("image_lane_height", "50");
    	
    	List<Map<String, String>> tweets = new ArrayList<Map<String, String>>();
        Iterator<Tweet> iterator = tweetService.listAll().iterator();
        
        List<String> icons = this.getIcons();
        
        while(iterator.hasNext()) {
        	Tweet tweet = iterator.next();
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("image", "https://pbs.twimg.com/profile_images/1024366710223249410/lTs0NDiM_400x400.jpg");
        	Random r = new java.util.Random();
        	map.put("importance", "" + r.nextInt(10)*10);
			//map.put("icon", iconList.get(r.nextInt(iconList.size())));
			map.put("description", tweet.getDescription());
			map.put("title", tweet.getDescription().substring(0, 10) + "...");
			map.put("startdate", tweet.getStartdate());
			map.put("high_threshold", "" + r.nextInt(10)*10);
			map.put("enddate", "");
			map.put("id", tweet.getId().toString());
			map.put("ypix", "0");
			map.put("slug", "0");
			map.put("low_threshold", "1");
			tweets.add(map);
        }
        
        timeline.put("events", tweets);
    	
        List<Map> data = new ArrayList<Map>();
    	data.add( timeline );
    	return data;
    }

    @RequestMapping("/tweet/show/{id}")
    public String getTweet(@PathVariable String id, Model model){
        model.addAttribute("tweet", tweetService.getById(id));
        return "tweet/show";
    }

    @RequestMapping("tweet/edit/{id}")
    public String edit(@PathVariable String id, Model model){
        Tweet tweet = tweetService.getById(id);
        TweetForm tweetForm = tweetToTweetForm.convert(tweet);

        model.addAttribute("tweetForm", tweetForm);
        return "tweet/tweetform";
    }

    @RequestMapping("/tweet/new")
    public String newTweet(Model model){
        model.addAttribute("tweetForm", new TweetForm());
        return "tweet/tweetform";
    }

    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
    public String saveOrUpdateTweet(@Valid TweetForm tweetForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "tweet/tweetform";
        }

        Tweet savedTweet = tweetService.saveOrUpdateTweetForm(tweetForm);

        return "redirect:/tweet/show/" + savedTweet.getId();
    }

    @RequestMapping("/tweet/delete/{id}")
    public String delete(@PathVariable String id){
        tweetService.delete(id);
        return "redirect:/tweet/list";
    }
    
    //@Value("#{'${icons}'.split(',')}")
    //cannot get icons defined in application.properties. it seems something wrong
    //with @Value as properties of mongodb connection cannot be accessed by property
    //name too, though the db connection works.
    private List<String> iconList;
    
    private List<String> getIcons(){
    	List<String> list = Arrays.asList(new String[]{"circle_black.png","circle_blue.png","circle_gray.png","circle_green.png","circle_orange.png","circle_purple.png","circle_red.png","circle_white.png","circle_yellow.png","flag_black.png","flag_blue.png","flag_gray.png","flag_green.png","flag_orange.png","flag_purple.png","flag_red.png","flag_white.png","flag_yellow.png","halfcircle_black.png","halfcircle_blue.png","halfcircle_gray.png","halfcircle_green.png","halfcircle_orange.png","halfcircle_purple.png","halfcircle_red.png","halfcircle_white.png","halfcircle_yellow.png","inf_black.png","inf_blue.png","inf_gray.png","inf_green.png","inf_orange.png","inf_purple.png","inf_red.png","inf_white.png","inf_yellow.png","music_black.png","music_blue.png","music_gray.png","music_green.png","music_orange.png","music_purple.png","music_red.png","music_white.png","music_yellow.png","plus_black.png","plus_blue.png","plus_gray.png","plus_green.png","plus_orange.png","plus_purple.png","plus_red.png","plus_white.png","plus_yellow.png","square_black.png","square_blue.png","square_gray.png","square_green.png","square_orange.png","square_purple.png","square_red.png","square_white.png","square_yellow.png","star_black.png","star_blue.png","star_gray.png","star_green.png","star_orange.png","star_purple.png","star_red.png","star_white.png","star_yellow.png","sym_card_club.png","sym_card_diamond.png","sym_card_heart.png","sym_card_spade.png","sym_file.png","sym_goldstar.png","sym_location.png","sym_question.png","sym_warning.png","triangle_black.png","triangle_blue.png","triangle_gray.png","triangle_green.png","triangle_orange.png","triangle_purple.png","triangle_red.png","triangle_white.png","triangle_yellow.png","video.png"});
    	return list;
    }
}
