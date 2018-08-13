package com.ef.repo;

import com.ef.entities.IPBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPBlockRepo extends JpaRepository<IPBlockEntity, Long> {
}
