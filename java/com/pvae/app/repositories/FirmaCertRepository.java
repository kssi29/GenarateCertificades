package com.pvae.app.repositories;

import com.pvae.app.models.FirmaCertModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmaCertRepository extends CrudRepository<FirmaCertModel, Long> {
}
