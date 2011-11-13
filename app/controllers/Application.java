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
		redirect("Application.showNotes");
    }
	
	public static void showNotes() {
		render("Application/editor.html");
	}
 
	public static void editNote(Long noteId) {
		Note note = Note.findById(noteId);

		if (note.isPublished()) {
			redirect("Application.showNote", noteId);
		}
		
		render("Application/index.html", note);
	}
	
	public static void createNote() {
		Note note = new Note("");
		note.uid = UidGenerator.newId();
		note.save();
		redirect("Application.editNote", note.id);
	}
	
	public static void showNote(Long noteId) {
		Note note = Note.findById(noteId);
		render(note);
	}
	

	public static Long createNoteJson(String content) {
		Note note = new Note(content);
		note.uid = UidGenerator.newId();
		note.save();
		return note.id;
	} 
	
	public static void publishNote(String content) {
		Note note = new Note(content);
		note.uid = UidGenerator.newId();
		note.publish();
		note.save();
		redirect("Application.showNote", note.id);
	}
}