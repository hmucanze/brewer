package com.mucanze.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mucanze.brewer.model.Cerveja;
import com.mucanze.brewer.repository.helper.cerveja.CervejaRepositoryQuery;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long>, CervejaRepositoryQuery {

}
