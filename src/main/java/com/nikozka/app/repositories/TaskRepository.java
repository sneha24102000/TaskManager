package com.nikozka.app.repositories;

import com.nikozka.app.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findByUserId(Long userId, Pageable pageable);
}
