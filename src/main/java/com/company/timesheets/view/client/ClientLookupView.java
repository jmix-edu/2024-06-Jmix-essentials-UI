package com.company.timesheets.view.client;

import com.company.timesheets.entity.Client;
import com.company.timesheets.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@ViewController("ts_Client.lookup")
@ViewDescriptor("client-lookup-view.xml")
@LookupComponent("clientsDataGrid")
@DialogMode(width = "64em")
public class ClientLookupView extends StandardListView<Client> {
}