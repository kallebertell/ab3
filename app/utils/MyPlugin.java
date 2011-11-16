package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;

import models.Note;

import play.Logger;
import play.PlayPlugin;

public class MyPlugin extends PlayPlugin { 
    @Override 
    public Object bind(String name, Class clazz, Type type, Annotation[] annotations, Map<String, String[]> params) { 
            if (clazz.getName().equals(Note.class.getName())) {
    	        Logger.info("This totally got called with "+clazz+" and "+name+" and shit");
            	return new Gson().fromJson(params.get("body")[0], Note.class);
            } 
            return null; 
    } 
} 

