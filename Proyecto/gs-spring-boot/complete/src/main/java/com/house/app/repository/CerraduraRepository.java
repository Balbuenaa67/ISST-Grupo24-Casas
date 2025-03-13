package com.house.app.repository;

import com.house.app.model.Cerradura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CerraduraRepository extends JpaRepository<Cerradura, String> {
}
