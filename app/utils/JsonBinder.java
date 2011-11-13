package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;

import play.Logger;
import play.data.binding.Global;
import play.data.binding.TypeBinder;

public class JsonBinder implements TypeBinder<Map<String, String>>{

	@Override
	public Object bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws Exception {
		Logger.info("Name=%s Value=%s Class=%s genericType=%s", name, value, actualClass.getSimpleName(), genericType.toString());
		return new Gson().fromJson(value, Map.class);
	}

}
