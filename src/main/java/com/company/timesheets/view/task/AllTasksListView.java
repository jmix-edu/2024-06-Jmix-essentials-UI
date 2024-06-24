package com.company.timesheets.view.task;

import com.company.timesheets.entity.Task;
import com.company.timesheets.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "tasks-all", layout = MainView.class)
@ViewController("ts_Tasks.list")
@ViewDescriptor("all-tasks-list-view.xml")
@LookupComponent("tasksDataGrid")
@DialogMode(width = "64em")
public class AllTasksListView extends StandardListView<Task> {
}