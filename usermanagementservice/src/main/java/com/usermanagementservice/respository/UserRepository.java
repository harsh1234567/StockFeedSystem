package com.usermanagementservice.respository;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.usermanagementservice.model.User;

import reactor.core.publisher.Mono;

/**
 * The interface StockLatestFeedDetailRepository
 * 
 * @author harsh.jain
 *
 */
@Repository
public interface UserRepository extends ReactiveCouchbaseRepository<User, String> {
	
	/**
	 * The findByuserId
	 * 
	 * @param userId.
	 * 
	 * @return Mono.
	 */
	@Query("select ums.*,META().id AS _ID ,META().cas AS _CAS from ums where META().id = $1")
	Mono<User> findByuserId(String userId);



	/**
	 * The findByuserId
	 * 
	 * @param userId.
	 * 
	 * @return Mono.
	 */
	@Query("select ums.*,META().id AS _ID ,META().cas AS _CAS from ums where META().id like 'USER::%' and userId=$1")
	Mono<User> findByuserName(String userId);
	
	/**
	 * The findByuserId
	 * 
	 * @param userId.
	 * 
	 * @return Mono.
	 */
	@Query("select *,META().id AS _ID, META().cas AS _CAS from ums where roles=$1 and  META().id LIKE $2")
	Mono<User> getUserRole(String roles, String userId);

}
