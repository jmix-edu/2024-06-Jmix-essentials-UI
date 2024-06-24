package com.company.timesheets.component;

import com.company.timesheets.component.ContactInformationFragment;
import com.company.timesheets.entity.ContactInformation;
import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import org.dom4j.Element;

public class ContactInformationFragmentLoader extends AbstractComponentLoader<ContactInformationFragment> {

    @Override
    protected ContactInformationFragment createComponent() {
        return factory.create(ContactInformationFragment.class);
    }

    @Override
    public void loadComponent() {
        loadDataContainer(resultComponent, element);

        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadClassNames(resultComponent, element);
        componentLoader().loadSizeAttributes(resultComponent, element);
    }

    private void loadDataContainer(ContactInformationFragment resultComponent, Element element) {
        String containerId = loadString(element, "dataContainer")
                .orElseThrow(() ->
                        new GuiDevelopmentException("ContactInformationFragment doesn't have data binding. " +
                                "Set 'dataContainer' attribute.", context));

        InstanceContainer container = getComponentContext().getViewData().getContainer(containerId);
        if (!ContactInformation.class.isAssignableFrom(container.getEntityMetaClass().getJavaClass())) {
            throw new GuiDevelopmentException("ContactInformationFragment have improper data binding. " +
                    "The value for the 'dataContainer' attribute should be associated with the " +
                    "ContactInformation embeddable entity.", context);
        }

        resultComponent.setDataContainer(container);
    }
}