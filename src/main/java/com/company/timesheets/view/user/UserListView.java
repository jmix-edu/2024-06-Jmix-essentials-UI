package com.company.timesheets.view.user;

import com.company.timesheets.entity.User;
import com.company.timesheets.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.app.inputdialog.InputParameter;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundTaskHandler;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Route(value = "users", layout = MainView.class)
@ViewController("ts_User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
//@PrimaryLookupView(User.class)
//@PrimaryListView(User.class)
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private Dialogs dialogs;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private DataGrid<User> usersDataGrid;
    @ViewComponent
    private CollectionContainer<User> usersDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private BackgroundWorker backgroundWorker;

    @Subscribe("usersDataGrid.sendEmail")
    public void onUsersDataGridSendEmail(final ActionPerformedEvent event) {
        dialogs.createInputDialog(this)
                .withHeader("Send email(s)")
                .withLabelsPosition(Dialogs.InputDialogBuilder.LabelsPosition.TOP)
                .withParameters(
                        InputParameter.stringParameter("title")
                                .withLabel("Title")
                                .withRequired(true),
                        InputParameter.parameter("body")
                                .withLabel("Body")
                                .withField(() -> {
                                    JmixTextArea area = uiComponents.create(JmixTextArea.class);
                                    area.setRequired(true);
                                    area.setWidthFull();
                                    area.setHeight("9.5em");

                                    return area;
                                })
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String title = closeEvent.getValue("title");
                        String body = closeEvent.getValue("body");

                        Set<User> selected = usersDataGrid.getSelectedItems();
                        Collection<User> usersToSendMails = selected.isEmpty()
                                ? usersDc.getItems()
                                : selected;

                        doSendEmail(title, body, usersToSendMails);
                    }
                })
                .open();
    }

    private void doSendEmail(String title, String body, Collection<User> usersToSendMails) {

        BackgroundTask<Integer, Void> task = new EmailTask(title, body, usersToSendMails);

        BackgroundTaskHandler<Void> handler = backgroundWorker.handle(task);
        handler.execute();
//        Void result = handler.getResult();
//
//        dialogs.createBackgroundTaskDialog(task)
//                .withHeader("Sending emails...")
//                .withText("Please, wait")
//                .withTotal(usersToSendMails.size())
//                .withShowProgressInPercentage(true)
//                .withCancelAllowed(true)
//                .open();
    }

    @Subscribe("simpleTimer")
    public void onSimpleTimerTimerAction(final Timer.TimerActionEvent event) {
        notifications.show("Tick!!!");
    }

    private class EmailTask extends BackgroundTask<Integer, Void> {
        private final String title;
        private final String body;
        private final Collection<User> users;

        public EmailTask(String title, String body, Collection<User> users) {
            super(10, TimeUnit.MINUTES, UserListView.this);

            this.title = title;
            this.body = body;
            this.users = users;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            for (User user : users) {
                if (taskLifeCycle.isCancelled()
                        || taskLifeCycle.isInterrupted()) {
                    break;
                }
                // Imitate time-consuming work
                TimeUnit.SECONDS.sleep(2);

                i++;
                taskLifeCycle.publish(i);
            }

            return null;
        }

        @Override
        public void done(Void result) {
            notifications.create("Email has been sent")
                    .withType(Notifications.Type.SUCCESS)
                    .withPosition(Notification.Position.TOP_END)
                    .show();
        }
    }
}