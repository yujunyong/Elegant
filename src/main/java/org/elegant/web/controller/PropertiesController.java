package org.elegant.web.controller;

import org.elegant.model.jooq.tables.pojos.Property;
import org.elegant.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/properties")
public class PropertiesController {

    private PropertyService propertyService;

    @Autowired
    public PropertiesController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping()
    public Flux<Property> allProperties() {
        return propertyService.getProperties();
    }

    @PostMapping
    public void addProperty(@RequestBody Property property) {
        propertyService.addProperty(property);
    }

    @RestController
    @RequestMapping("/properties/{propName}")
    public class PropertyController {

        @GetMapping()
        public Mono<Property> property(@PathVariable("propName") String propName) {
            return propertyService.getProperty(propName);
        }

        @PatchMapping()
        public void updateProperty(@PathVariable("propName") String propName, @RequestBody Property property) {
            property.setPropName(propName);
            propertyService.updateProperty(property);
        }

        @DeleteMapping()
        public void deleteProperty(@PathVariable("propName") String propName) {
            propertyService.deleteProperty(propName);
        }
    }
}
