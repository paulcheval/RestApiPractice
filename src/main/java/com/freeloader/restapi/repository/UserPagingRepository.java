package com.freeloader.restapi.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.restapi.entities.UserEntity;

@Repository
public interface UserPagingRepository extends ListPagingAndSortingRepository<UserEntity, Long> {

}
