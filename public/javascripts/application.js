var app =  (function() {
	var app = {
        debug : true,
        isMac : navigator.userAgent.indexOf('Mac') != -1
	};

    var model = {
        "noteKeys" : [],
        "activeIndex" : -1
    };

	app.log = function(str) {
		if (app.debug && window.console)
			console.log(str);
	};

	app.preview = function(text, escape) {
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

	app.notify = function(msg) {
		$.jGrowl(msg);
	};

	app.notifyError = function(msg) {
		this.notify(msg);
	};

    app.getIndex = function (key) {
        return model.noteKeys.indexOf(key);
    };

    app.getNoteKeys = function() {
        return model.noteKeys;
    };

    app.getNoteKeyByIndex = function(index) {
        return model.noteKeys[index];
    }

    app.addNote = function(content) {
        var key = app.generateNoteKey();

        model.noteKeys.push(key);
        app.store.set('model', JSON.stringify(model));
        app.saveNote(key, content);
        return key;
    };

	app.store = new Persist.Store('com_kerebus_notepad');


    app.loadNote = function(key) {
        return app.store.get(key);
    }

    app.saveNote = function(key, content) {
        app.store.set(key, content);
    }

    app.removeNote = function(key) {
        var idx = model.noteKeys.indexOf(key);
        if (idx != -1) model.noteKeys.splice(idx, 1);

        app.store.remove(key);
        app.store.set('model', JSON.stringify(model));
    }

    app.generateNoteKey = function() {
        var s4 = function() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        };

        return "note_" + s4() + s4();
    }

    app.init = function() {
        var mementoStr = app.store.get('model');

        if (!mementoStr) return;
        var memento = JSON.parse(mementoStr);

        if (!memento) return;

        model.noteKeys = memento.noteKeys || [];
        model.activeIndex = memento.activeIndex || -1;
    };

	return app;
})();

app.init();


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

