package com.jacinthocaio.user_service.repository;

import com.jacinthocaio.user_service.dominio.Profile;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
