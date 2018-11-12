package org.elegant.repository;

import org.elegant.model.jooq.tables.daos.PropertyDao;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyRepository extends PropertyDao {

    @Autowired
    public PropertyRepository(Configuration configuration) {
        super(configuration);
    }
}
