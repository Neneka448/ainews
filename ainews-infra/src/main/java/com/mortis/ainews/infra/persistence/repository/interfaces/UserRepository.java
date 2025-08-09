package com.mortis.ainews.infra.persistence.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
