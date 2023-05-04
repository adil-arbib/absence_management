package com.ensah.eservice.repositories;


import com.ensah.eservice.models.NotificationTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationTargetRepository extends JpaRepository<NotificationTarget, Long> {
}
