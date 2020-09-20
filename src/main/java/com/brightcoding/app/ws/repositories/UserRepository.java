package com.brightcoding.app.ws.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brightcoding.app.ws.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	
	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String UserId);
	
	@Query(value = "select * from users u where u.first_name LIKE 'ElmejorDevaaa'", nativeQuery = true)
	Page<UserEntity> finAllUserByFirstName(org.springframework.data.domain.Pageable pageableRequest );
	
	//@Query(value = "SELECT * FROM  users", nativeQuery = true)
	//Page<UserEntity> findAllUsers(org.springframework.data.domain.Pageable pageableRequest);
	
	@Query("SELECT user FROM users user")
	Page<UserEntity> findAllUsers(org.springframework.data.domain.Pageable pageableRequest);
	
	/*@Query(value = "SELECT * from users u WHERE (u.first_name = ?1 OR u.last_name = ?1) AND u.EMAIL_VERIFICATION_STATUS = ?2", nativeQuery = true)
	Page<UserEntity> finAllUserByCriteria(org.springframework.data.domain.Pageable pageableRequest, String search, int status );*/
	//@Query(value = "SELECT * from users u WHERE (u.first_name = :search OR u.last_name = :search) AND u.EMAIL_VERIFICATION_STATUS = :status", nativeQuery = true)
	//Page<UserEntity> finAllUserByCriteria(org.springframework.data.domain.Pageable pageableRequest, @Param("search")String search, @Param("status") int status );
	
	@Query(value = "SELECT * from users u WHERE (u.first_name LIKE %:search% OR u.last_name LIKE %:search%) AND u.EMAIL_VERIFICATION_STATUS = :status", nativeQuery = true)
	Page<UserEntity> finAllUserByCriteria(org.springframework.data.domain.Pageable pageableRequest, @Param("search")String search, @Param("status") int status );

}
