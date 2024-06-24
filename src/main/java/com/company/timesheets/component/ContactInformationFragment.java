package com.company.timesheets.component;

import com.company.timesheets.entity.ContactInformation;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.core.MessageTools;
import io.jmix.core.Metadata;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.JmixEmailField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.data.ValueSource;
import io.jmix.flowui.data.value.ContainerValueSource;
import io.jmix.flowui.model.InstanceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class ContactInformationFragment extends Composite<FormLayout> implements HasSize, HasEnabled {

    private Metadata metadata;
    private MessageTools messageTools;
    private UiComponents uiComponents;

    private JmixEmailField emailField;
    private TypedTextField<String> phoneField;
    private TypedTextField<String> urlField;
    private JmixTextArea addressField;

    public ContactInformationFragment() {
    }

    @Autowired
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Autowired
    public void setMessageTools(MessageTools messageTools) {
        this.messageTools = messageTools;
    }

    @Autowired
    public void setUiComponents(UiComponents uiComponents) {
        this.uiComponents = uiComponents;
    }

    @Override
    protected FormLayout initContent() {
        FormLayout formLayout = super.initContent();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("60em", 3)
        );

        emailField = uiComponents.create(JmixEmailField.class);
        emailField.setLabel(messageTools.getPropertyCaption(metadata.getClass(ContactInformation.class), "email"));
        emailField.setPrefixComponent(VaadinIcon.ENVELOPE.create());
        formLayout.add(emailField);

        phoneField = uiComponents.create(TypedTextField.class);
        phoneField.setLabel(messageTools.getPropertyCaption(metadata.getClass(ContactInformation.class), "phone"));
        phoneField.setPrefixComponent(VaadinIcon.PHONE.create());
        formLayout.add(phoneField);

        urlField = uiComponents.create(TypedTextField.class);
        urlField.setLabel(messageTools.getPropertyCaption(metadata.getClass(ContactInformation.class), "url"));
        urlField.setPrefixComponent(VaadinIcon.LINK.create());
        formLayout.add(urlField);

        addressField = uiComponents.create(JmixTextArea.class);
        addressField.setLabel(messageTools.getPropertyCaption(metadata.getClass(ContactInformation.class), "address"));
        addressField.setPrefixComponent(VaadinIcon.MAP_MARKER.create());
        addressField.setHeight("9.5em");
        formLayout.add(addressField, 3);

        return formLayout;
    }

    public void setDataContainer(@Nullable InstanceContainer<ContactInformation> dataContainer) {
        emailField.setValueSource(createValueSource(dataContainer, "email"));
        phoneField.setValueSource(createValueSource(dataContainer, "phone"));
        urlField.setValueSource(createValueSource(dataContainer, "url"));
        addressField.setValueSource(createValueSource(dataContainer, "address"));
    }

    @Nullable
    private ValueSource<String> createValueSource(InstanceContainer<ContactInformation> dataContainer, String property) {
        return dataContainer != null ? new ContainerValueSource<>(dataContainer, property) : null;
    }
}
