/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client;

import java.util.HashMap;

import com.extjs.gxt.samples.mail.client.mvc.AppController;
import com.extjs.gxt.samples.mail.client.mvc.ContactController;
import com.extjs.gxt.samples.mail.client.mvc.MailController;
import com.extjs.gxt.samples.mail.client.mvc.TaskController;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.MvcEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.DispatcherListener;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Mail implements EntryPoint {

	public static final String SERVICE = "mailservice";
	TaskController taskController;
	ContactController contactController;
	MailController mailController;

	// private static Dispatcher dispatcher;

	public void onModuleLoad() {
		GXT.setDefaultTheme(Theme.GRAY, true);

		MailServiceAsync service = (MailServiceAsync) GWT.create(MailService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		String moduleRelativeURL = SERVICE;
		endpoint.setServiceEntryPoint(moduleRelativeURL);
		Registry.register(SERVICE, service);

		final Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addDispatcherListener(new DispatcherListener() {
			@Override
			public void beforeDispatch(final MvcEvent mvce) {

				switch (CONTROLLERS.getControllerByEvent(mvce.getAppEvent().getType())) {

				case CONTACT:

					if (contactController == null) {
						mvce.setCancelled(true);

						GWT.runAsync(new RunAsyncCallback() {

							@Override
							public void onFailure(Throwable reason) {

							}

							@Override
							public void onSuccess() {
								contactController = new ContactController();
								dispatcher.addController(contactController);
								GWT.log("fetched and added contactController");
								Dispatcher.get();

								Dispatcher.forwardEvent(mvce.getAppEvent().getType(), mvce.getAppEvent().getData());
							}

						});

					}
					break;

				case TASK:

					if (taskController == null) {
						mvce.setCancelled(true);
						GWT.runAsync(new RunAsyncCallback() {

							@Override
							public void onFailure(Throwable reason) {

							}

							@Override
							public void onSuccess() {
								taskController = new TaskController();
								dispatcher.addController(taskController);
								GWT.log("fetched and added  taskController");
								Dispatcher.forwardEvent(mvce.getAppEvent().getType(), mvce.getAppEvent().getData());

							}

						});
					}
					break;

				// case MAIL:
				// case MAIL2:
				// case MAIL3:
				// case MAIL4:
				// if (mailController == null) {
				// mvce.setCancelled(true);
				// GWT.runAsync(new RunAsyncCallback() {
				//
				// @Override
				// public void onFailure(Throwable reason) {
				//
				// }
				//
				// @Override
				// public void onSuccess() {
				// mailController = new MailController();
				// dispatcher.addController(mailController);
				// GWT.log("fetched and added  mailController");
				// Dispatcher.forwardEvent(mvce.getAppEvent().getType(),
				// mvce.getAppEvent().getData());
				//
				// }
				//
				// });
				// }
				//					
				// break;

				default:
					GWT.log("default Controller request");
					break;

				}

			}

		});
		dispatcher.addController(new AppController());
		dispatcher.addController(new MailController());
		// dispatcher.addController(new TaskController());
		// dispatcher.addController(new ContactController());

		dispatcher.dispatch(AppEvents.Login);

		GXT.hideLoadingPanel("loading");
	}

	public enum CONTROLLERS {

		 APP(AppEvents.Login),
		 APP2(AppEvents.Error),
		 APP3(AppEvents.Init),
		 //MAIL(AppEvents.Init),
		 MAIL2(AppEvents.NavMail),
		 MAIL3(AppEvents.ViewMailItem),
		 MAIL4(AppEvents.ViewMailItems), 
		 MAIL5(AppEvents.ViewMailFolders),
		TASK(AppEvents.NavTasks),
		CONTACT(AppEvents.NavContacts);

		private final EventType event;

		private static final HashMap<EventType, CONTROLLERS> lookupEvent = new HashMap<EventType, CONTROLLERS>();

		public static CONTROLLERS getControllerByEvent(EventType event) {
			return lookupEvent.get(event);
		}

		static {
			for (CONTROLLERS d : CONTROLLERS.values())
				lookupEvent.put(d.event, d);
		}

		private CONTROLLERS(EventType event) {

			this.event = event;

		}

	}
}
