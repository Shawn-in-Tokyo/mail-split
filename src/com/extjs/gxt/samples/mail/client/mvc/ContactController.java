/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client.mvc;

import com.extjs.gxt.samples.mail.client.AppEvents;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;

public class ContactController extends Controller {

	// private ContactFolderView folderView;
	private ContactView contactView;

	public ContactController() {
		// registerEventTypes(AppEvents.Init);
		registerEventTypes(AppEvents.NavContacts);
	}

	@Override
	public void initialize() {
		super.initialize();
		// folderView = new ContactFolderView(this);
		contactView = new ContactView(this);
	}

	@Override
	public void handleEvent(AppEvent event) {
		EventType type = event.getType();
		if (type == AppEvents.Init) {
			// forwardToView(folderView, event);
		} else if (type == AppEvents.NavContacts) {
			GWT.log("ContactController says forwardToView contactView");
			forwardToView(contactView, event);
		}
	}

}
