/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client.mvc;

import java.util.List;

import com.extjs.gxt.samples.mail.client.AppEvents;
import com.extjs.gxt.samples.mail.client.model.Folder;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.data.TreeModelReader;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;

public class MailFolderView extends View {

	private TreeStore<Folder> store;
	private TreePanel<Folder> tree;
	private TreeLoader<Folder> loader;

	  private ContentPanel tasks;
	  private ContentPanel contacts;
	
	public MailFolderView(Controller controller) {
		super(controller);
	}

	protected void initialize() {

	}

	protected void initUI() {
		ContentPanel west = (ContentPanel) Registry.get(AppView.WEST_PANEL);
		ContentPanel mail = new ContentPanel();
		mail.setAnimCollapse(false);
		mail.setHeading("Mail");
		mail.addListener(Events.Expand, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				Dispatcher.get().dispatch(AppEvents.NavMail);
			}
		});
		

		loader = new BaseTreeLoader<Folder>(new TreeModelReader<List<Folder>>());
		store = new TreeStore<Folder>(loader);

		tree = new TreePanel<Folder>(store);
		tree.getStyle().setLeafIcon(IconHelper.createStyle("tree-folder"));
		tree.setDisplayProperty("name");
		tree.setAutoSelect(true);
		tree.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<Folder>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<Folder> se) {
				Folder f = (Folder) se.getSelection().get(0);
				AppEvent evt = new AppEvent(AppEvents.ViewMailItems, f);
				fireEvent(evt);
			}
		});

		mail.add(tree);
		west.add(mail);
		
		 initTaskUI();
	     initContactsUI();
	//	Dispatcher.get().dispatch(AppEvents.NavMail);
	}

	protected void handleEvent(AppEvent event) {
		if (event.getType() == AppEvents.Init) {
			initUI();
		} else if (event.getType() == AppEvents.NavMail) {
			Folder f = event.getData();
			if (f != null) {
				loader.addListener(Loader.Load, new LoadListener() {
					@Override
					public void loaderLoad(LoadEvent le) {
						loader.removeLoadListener(this);
					}
				});
				loader.load(f);
			}
		}
	}
	
	  protected void initTaskUI() {
		  tasks = new ContentPanel();
		  tasks.setAnimCollapse(false);
		  tasks.setHeading("Tasks");
		  tasks.addListener(Events.Expand, new Listener<ComponentEvent>() {
		    public void handleEvent(ComponentEvent be) {
		      Dispatcher.get().dispatch(AppEvents.NavTasks);
		      GWT.log("nav tasks requested");

		    }
		  });

		  ContentPanel west = (ContentPanel) Registry.get(AppView.WEST_PANEL);
		  west.add(tasks);
		}

		protected void initContactsUI() {
		contacts = new ContentPanel();
		contacts.setHeading("Contacts");
		contacts.setAnimCollapse(false);
		contacts.addListener(Events.Expand, new Listener<ComponentEvent>() {
		  public void handleEvent(ComponentEvent be) {
					Dispatcher.get().dispatch(AppEvents.NavContacts);
		    GWT.log("nav contacts requested");
		  }
		});

		ContentPanel west = (ContentPanel) Registry.get(AppView.WEST_PANEL);
		west.add(contacts);
		}
}
