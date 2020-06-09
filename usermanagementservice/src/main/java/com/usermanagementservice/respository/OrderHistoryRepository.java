package com.usermanagementservice.respository;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.model.OrderHistory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface OrderHistoryRepository.
 * 
 * @author harsh.jain
 *
 */
@Repository
public interface OrderHistoryRepository extends ReactiveCouchbaseRepository<OrderHistory, String> {

	/**
	 * The saveUserHistoryOrder.
	 * 
	 * @param OrderHistory orderHistory
	 */
	default void saveUserHistoryOrder(OrderHistory orderHistory) {
		getCouchbaseOperations().getCouchbaseBucket().mutateIn(orderHistory.getId()).upsertDocument(true)
				.upsert(Constants.USERNAME, orderHistory.getUserName())
				.upsert(Constants.USER_ID, orderHistory.getUserId()).arrayAppend(Constants.ORDER_HISTORY, orderHistory)
				.execute();

	}

	/**
	 * The findAllOrder.
	 * 
	 * @param String userOrderId.
	 * 
	 * 
	 */
	@Query("select ums.*,id,META().id AS _ID,META().cas AS _CAS from ums where META().id = $1")
	Mono<OrderHistory> findAllOrder(String userOrderId);


	/**
	 * The findAllUserOrder.
	 * 
	 * @param String userOrderId.
	 */
	@Query("select ums.*,META().id AS _ID,META().cas AS _CAS from ums where META().id like $1")
	Flux<OrderHistory> findAllUserOrder(String userOrderId);

}
