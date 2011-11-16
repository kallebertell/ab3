package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import models.Note;
import play.Logger;
import play.data.binding.As;
import play.data.binding.Binder;
import play.mvc.Controller;
import utils.NoteJsonBinder;
import utils.UidGenerator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class Application extends Controller {

    
	public static void index() {
		render("Application/editor.html");
    }
	
	public static void edit() {
		render("Application/editor.html");
	}

	public static void showNote(String uid) {
		Note note = Note.find("uid = :uid").bind("uid", uid).first();
		render(note);
	}
	
	public static void publishNote(String content) {
		Note note = new Note(content);
		note.uid = UidGenerator.newId();
		note.publish();
		note.save();
		redirect("Application.showNote", note.uid);
	}
}