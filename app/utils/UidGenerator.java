package utils;

import com.google.gson.Gson;
import models.Note;
import play.Logger;
import play.PlayPlugin;
import play.data.binding.TypeBinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Random;

public class UidGenerator {
	public static final String chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int charsLength = chars.length();
	
	public static String newId() {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<6; i++) {
			sb.append(chars.charAt(rnd.nextInt(charsLength)));
		}
		return sb.toString();
	}

    public static class NoteJsonBinder implements TypeBinder<Note> {

        public NoteJsonBinder() {
            Logger.info("NoteJsonBinder instantiated");
        }

        @Override
        public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
            Logger.info("Name=%s Value=%s Class=%s genericType=%s", name, value, actualClass.getSimpleName(), genericType.toString());
            return new Gson().fromJson(value, Note.class);
        }

    }

    public static class JsonBinder implements TypeBinder<Map<String, String>>{

        @Override
        public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
            Logger.info("Name=%s Value=%s Class=%s genericType=%s", name, value, actualClass.getSimpleName(), genericType.toString());
            return new Gson().fromJson(value, Map.class);
        }

    }

    public static class MyPlugin extends PlayPlugin {
        @Override
        public Object bind(String name, Class clazz, Type type, Annotation[] annotations, Map<String, String[]> params) {
                if (clazz.getName().equals(Note.class.getName())) {
                    return new Gson().fromJson(params.get("body")[0], Note.class);
                }
                return null;
        }
    }
}
