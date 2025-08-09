package com.mortis.ainews.infra.persistence.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mortis.ainews.infra.persistence.po.users.UserSubScriptionRel;

@Repository
public interface UserSubScriptionRelRepository
                extends JpaRepository<UserSubScriptionRel, UserSubScriptionRel.UserSubScriptionRelPK> {

        List<UserSubScriptionRel> findByIdUserIdAndDeleted(Long userId, int deleted);
}
