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
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkNotNull;
import static reactor.core.scheduler.Schedulers.elastic;
import static reactor.core.scheduler.Schedulers.resetFactory;

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
        return Mono.just(propName)
                .flatMap(id -> Mono.justOrEmpty(propertyRepository.fetchOneByPropName(propName)));
    }

    public Mono<String> getString(String propName, String defaultValue) {
        return getString(propName).defaultIfEmpty(defaultValue);
    }

    public Mono<String> getString(String propName) {
        return getProperty(propName).map(Property::getPropValue);
    }

    public Mono<Integer> getInteger(String propName, Integer defaultValue) {
        return getInteger(propName).defaultIfEmpty(defaultValue);
    }

    public Mono<Integer> getInteger(String propName) {
        return getString(propName).map(Integer::parseInt);
    }

    public Mono<Boolean> getBoolean(String propName, Boolean defaultValue) {
        return getBoolean(propName).defaultIfEmpty(defaultValue);
    }

    public Mono<Boolean> getBoolean(String propName) {
        return getString(propName).map(Boolean::parseBoolean);
    }

//    @CacheEvict(value = "property", key = "#property.propName")
    @Transactional
    public Mono<Void> updateProperty(Property property) {
        return Mono.just(property)
                .doOnNext(p -> p.setUpdateTime(LocalDateTime.now()))
                .doOnNext(propertyRepository::update)
                .doOnNext(p -> publisher.publishEvent(new UpdatePropertyEvent(property)))
                .then();
    }

    @Transactional
    public Mono<Void> addProperty(Property property) {
        return Mono.just(property)
                .doOnNext(propertyRepository::insert)
                .doOnNext(p -> publisher.publishEvent(new AddPropertyEvent(property)))
                .then();
    }

//    @CacheEvict("property")
    @Transactional
    public Mono<Void> deleteProperty(String propName) {
        return Mono.just(propName)
                .doOnNext(propertyRepository::deleteById)
                .then();
    }
}
