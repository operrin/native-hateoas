package org.miage.intervenantservice.boundary;

import java.util.UUID;
import org.miage.intervenantservice.entity.Intervenant;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IntervenantRepository extends ReactiveCrudRepository<Intervenant, UUID> {

}