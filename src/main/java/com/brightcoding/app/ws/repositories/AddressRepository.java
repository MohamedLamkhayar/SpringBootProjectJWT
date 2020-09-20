package com.brightcoding.app.ws.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brightcoding.app.ws.entities.AddressEntity;
import com.brightcoding.app.ws.entities.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

	List<AddressEntity> findByUser(UserEntity currentUser);

	AddressEntity findByAddressId(String addressId);

	@Query(value = "delete from addresses u where u.ADDRESS_ID LIKE %:addressId%", nativeQuery = true)
	void deleteAddressByAddressId(@Param("addressId") String addressId);
}
