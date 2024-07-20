package com.company.makepub.view.markup;

import com.company.makepub.entity.Markup;
import com.company.makepub.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;


@Route(value = "markups", layout = MainView.class)
@ViewController("Markup.list")
@ViewDescriptor("markup-list-view.xml")
@LookupComponent("markupsDataGrid")
@DialogMode(width = "64em")
public class MarkupListView extends StandardListView<Markup> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Markup> markupsDc;

    @ViewComponent
    private InstanceContainer<Markup> markupDc;

    @ViewComponent
    private InstanceLoader<Markup> markupDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("markupsDataGrid.create")
    public void onMarkupsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Markup entity = dataContext.create(Markup.class);
        markupDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("markupsDataGrid.edit")
    public void onMarkupsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Markup item = markupDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        markupsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        markupDl.load();
        updateControls(false);
    }

    @Subscribe(id = "markupsDc", target = Target.DATA_CONTAINER)
    public void onMarkupsDcItemChange(final InstanceContainer.ItemChangeEvent<Markup> event) {
        Markup entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            markupDl.setEntityId(entity.getId());
            markupDl.load();
        } else {
            markupDl.setEntityId(null);
            markupDc.setItem(null);
        }
    }

    protected ValidationErrors validateView(Markup entity) {
        ViewValidation viewValidation = getViewValidation();
        ValidationErrors validationErrors = viewValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }
        validationErrors.addAll(viewValidation.validateBeanGroup(UiCrossFieldChecks.class, entity));
        return validationErrors;
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}