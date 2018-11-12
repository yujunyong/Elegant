package org.elegant.service;

import org.elegant.event.property.AddPropertyEvent;
import org.elegant.event.property.UpdatePropertyEvent;
import org.elegant.model.jooq.tables.pojos.Property;
import org.elegant.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PropertyService {
    public static final String ROOT_BOOK_DIR_KEY = "app.directory.root";

    private PropertyRepository propertyRepository;

    private ApplicationEventPublisher publisher;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, ApplicationEventPublisher publisher) {
        this.propertyRepository = propertyRepository;
        this.publisher = publisher;
    }

    public Flux<Property> getProperties() {
        return Flux.fromIterable(propertyRepository.findAll());
    }

    public Mono<Property> getProperty(String propName) {
        return Mono.justOrEmpty(propertyRepository.fetchOneByPropName(propName));
    }

    public Mono<String> getString(String propName) {
        return getProperty(propName).map(Property::getPropValue);
    }

    @Transactional
    public void updateProperty(Property property) {
        propertyRepository.update(property);
        publisher.publishEvent(new AddPropertyEvent(property));
    }

    @Transactional
    public void addProperty(Property property) {
        propertyRepository.insert(property);
        publisher.publishEvent(new UpdatePropertyEvent(property));
    }

    @Transactional
    public void deleteProperty(String propName) {
        propertyRepository.deleteById(propName);
    }
}
