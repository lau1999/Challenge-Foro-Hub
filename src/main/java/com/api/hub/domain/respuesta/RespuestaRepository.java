package com.api.hub.domain.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository  extends JpaRepository <Respuesta,Long>{
}
