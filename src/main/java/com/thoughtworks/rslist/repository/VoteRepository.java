package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface VoteRepository extends CrudRepository<VoteEntity, Integer> {
//    @Query("SELECT v FROM VoteEntity AS v WHERE v.num = :num")

    List<VoteEntity> findAll();

    List<VoteEntity> findByUserIdAndRsEventId(Integer userId, Integer rsEventId);
}
