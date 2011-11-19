var ab3 =  (function() {
	var api = {
        debug : true,
        isMac : navigator.userAgent.indexOf('Mac') != -1
	};

    var store = new Persist.Store('com_kerebus_notepad');

    // Our application data
    var model = { noteKeys : [], activeIndex: -1 };

	api.log = function(str) {
		if (ab3.debug && window.console)
			console.log(str);
	};

	api.preview = function(text, escape) {
		if (!text || text.length < 1) {
			return "Empty";
		}

        var lines = text.split("\n");
        var firstNonEmptyLineIndex = 0;
        for (i in lines) {
            if ($.trim(lines[i]).length > 0) {
                firstNonEmptyLineIndex = i;
                break;
            }
        }
        var title = $.trim(lines[firstNonEmptyLineIndex]);
        // Html escaping hack
		if (escape) {
            title = $('<span></span>').text(title).html();
        }

        // White space truncation
        title = title.replace(/\s+/g," ");

		if (title.length > 12) {
		  return title.substring(0,11) + '..';
		} else if (title.length < 1) {
            return "Empty";
        }

		return title;
	};

	api.notify = function(msg) {
		$.jGrowl(msg);
	};

	api.notifyError = function(msg) {
		this.notify(msg);
	};

    api.getActiveIndex = function() {
        return model.activeIndex;
    }

    api.getActiveKey = function() {
        return model.noteKeys[ model.activeIndex ];
    }
    
    api.getIndex = function (key) {
        return model.noteKeys.indexOf(key);
    };

    api.getNoteKeys = function() {
        return model.noteKeys;
    };

    api.getNoteKeyByIndex = function(index) {
        return model.noteKeys[index];
    }

    api.addNote = function(content) {
        var key = this.generateNoteKey();

        model.noteKeys.push(key);
        store.set('model', JSON.stringify(model));
        this.saveNote(key, content);
        return key;
    };

    api.updateIndex = function(index) {
        model.activeIndex = index;
        store.set('model', JSON.stringify(model));
    }

    api.loadNote = function(key) {
        return store.get(key);
    }

    api.saveNote = function(key, content) {
        store.set(key, content);
    }

    api.removeNote = function(key) {
        var idx = model.noteKeys.indexOf(key);
        if (idx != -1) model.noteKeys.splice(idx, 1);

        store.remove(key);
        store.set('model', JSON.stringify(model));
    }

    api.generateNoteKey = function() {
        var s4 = function() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        };

        return "note_" + s4() + s4();
    }

    api.init = function() {
        var mementoStr = store.get('model');

        if (!mementoStr) {
            model = loadDefaultData();
            createDefaultNotes();
            return;
        }

        var memento = JSON.parse(mementoStr);

        if (!memento) return;

        model.noteKeys = memento.noteKeys || [];
        model.activeIndex = memento.activeIndex;
    };

    var loadDefaultData = function() {
        return {
            noteKeys: ['intro', 'shortcuts'],
            activeIndex: 0
        };
    };

    var createDefaultNotes = function() {
        api.saveNote('intro', 'Introduction\n\nThis is a plain text notepad.\nNotes are stored in your browser\'s local storage.\n\n\n\n\n\n\n\n\n\nSave a note by clicking the icon down left.\n\n                Share a note by clicking the icon down right.');
        api.saveNote('shortcuts', 'Shortcuts\n\ncmd/ctrl-s        =  Saves the current document\n\ncmd/ctrl-z        =  Undo\n\ncmd/ctrl-y        =  Redo\n\ncmd/ctrl-1,2,3..  =  Activates the document at given index\n\ncmd/ctrl-.        =  Cycles active document left-to-right\n\ncmd/ctrl-,        =  Cycles active document right-to-left\n\n\n\nUse your browser to do the rest like copy/paste and zooming.')
    }

	return api;
})();


/**
 * jQuery toggleVisibility plugin
 */
(function( $ ) {
  $.fn.toggleVisibility = function() {
    if (this.css('visibility') == 'hidden'){
       this.css('visibility', 'visible');
    } else {
       this.css('visibility', 'hidden');
    }

    return this;
  };
})( jQuery );

