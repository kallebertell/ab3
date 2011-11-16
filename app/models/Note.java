package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Note extends Model {

    public Date createdAt;
    
    public boolean published = false;
    
    public String uid;
    
    @Lob
    public String content;

    public Note(String content) {
        this.content = content;
        this.createdAt = new Date();
    }
    
    public String getTweet() {
    	if (content == null) {
    		return "";
    	}
    	
    	String tweet = content.trim();
    	if (tweet.length() > 100) {
    		tweet = tweet.substring(0,100)+"..";
    	}
    	return tweet;
    }
    
    public String getTitle() {
    	if (content == null)
    		return "";
    	
    	String title = content.trim();
    	if (title.length() > 12) {
    		title = title.substring(0,12);
    	}
    	return title;
    }
    
    public String toString() {
    	return getClass().getSimpleName()+":"+content;
    }

	public void publish() {
		published = true;
	}

	public boolean isPublished() {
		return published;
	}
 
}
