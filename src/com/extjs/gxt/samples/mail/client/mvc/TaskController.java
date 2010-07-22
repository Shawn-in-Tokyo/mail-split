/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007-2009, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client.mvc;

import com.extjs.gxt.samples.mail.client.AppEvents;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;

public class TaskController extends Controller {

 // private TaskFolderView folderView;
  private TaskView taskView;

  public TaskController() {
   // registerEventTypes(AppEvents.Init);
    registerEventTypes(AppEvents.NavTasks);
  }

  @Override
  public void initialize() {
    super.initialize();
    //folderView = new TaskFolderView(this);
		taskView = new TaskView(this);
  }

  public void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.Init) {
     // forwardToView(folderView, event);
    } else if (event.getType() == AppEvents.NavTasks) {
    	GWT.log("TaskController says forwardToView taskView");
      forwardToView(taskView, event);
    }
  }

}
