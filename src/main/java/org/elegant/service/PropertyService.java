package org.elegant.service;

import org.elegant.event.property.AddPropertyEvent;
import org.elegant.event.property.UpdatePropertyEvent;
import org.elegant.model.jooq.tables.pojos.Property;
import org.elegant.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

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

//    @Cacheable("property")
    public Flux<Property> getProperties() {
        return Flux.fromIterable(propertyRepository.findAll());
    }

//    @Cacheable("property")
    public Mono<Property> getProperty(String propName) {
        return Mono.justOrEmpty(propertyRepository.fetchOneByPropName(propName));
    }

    public Mono<String> getString(String propName) {
        return getProperty(propName).map(Property::getPropValue);
    }

//    @CacheEvict(value = "property", key = "#property.propName")
    @Transactional
    public Mono<Void> updateProperty(Property property) {
        checkNotNull(property);

        property.setUpdateTime(LocalDateTime.now());
        propertyRepository.update(property);

        publisher.publishEvent(new AddPropertyEvent(property));
        return Mono.empty();
    }

    @Transactional
    public Mono<Void> addProperty(Property property) {
        checkNotNull(property);

        propertyRepository.insert(property);

        publisher.publishEvent(new UpdatePropertyEvent(property));
        return Mono.empty();
    }

//    @CacheEvict("property")
    @Transactional
    public Mono<Void> deleteProperty(String propName) {
        checkNotNull(propName);

        propertyRepository.deleteById(propName);
        return Mono.empty();
    }
}
