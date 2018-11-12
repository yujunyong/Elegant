package org.elegant.event.property;

import org.elegant.model.jooq.tables.pojos.Property;
import org.springframework.context.ApplicationEvent;

public class UpdatePropertyEvent extends ApplicationEvent {
    public UpdatePropertyEvent(Property property) {
        super(property);
    }

    public Property getProperty() {
        return (Property) getSource();
    }
}
