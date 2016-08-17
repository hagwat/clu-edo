package ui;

import java.awt.event.ActionEvent;

public class ViewEvent extends ActionEvent {

	public ViewEvent(Object source, int id, String command) {
		super(source, id, command);
		// TODO Auto-generated constructor stub
	}

	public ViewEvent(Object source, int id, String command, int modifiers) {
		super(source, id, command, modifiers);
		// TODO Auto-generated constructor stub
	}

	public ViewEvent(Object source, int id, String command, long when, int modifiers) {
		super(source, id, command, when, modifiers);
		// TODO Auto-generated constructor stub
	}

}
