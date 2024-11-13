package com.es.segurosinseguros.Repository;

import com.es.segurosinseguros.Model.AsistenciaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaMedicaRepository extends JpaRepository<AsistenciaMedica, Long> {}
