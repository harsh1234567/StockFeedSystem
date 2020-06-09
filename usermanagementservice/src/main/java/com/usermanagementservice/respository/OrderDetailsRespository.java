
package com.usermanagementservice.respository;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.google.gson.Gson;
import com.usermanagementservice.constants.Constants;
import com.usermanagementservice.model.OrderInfo;
import com.usermanagementservice.model.User;
import com.usermanagementservice.model.UserPortfolio;
import com.usermanagementservice.model.UserPortfolioResponse;
import com.usermanagementservice.utils.CommonUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface StockLatestFeedDetailRepository
 * 
 * @author harsh.jain
 *
 */
@Repository
public interface OrderDetailsRespository extends ReactiveCouchbaseRepository<UserPortfolio, String> {

	/**
	 * The findByIdAllPortfolio
	 * 
	 * @param portfolioId
	 * 
	 * @return Flux.
	 */
	@Query("select userName,change,earning,income,orderInfo,META().id AS _ID, META().cas AS _CAS from ums where META().id LIKE $1")
	Flux<UserPortfolio> findByIdAllPortfolio(String portfolioId);

	/**
	 * The findByIdPortfolio
	 * 
	 * @param String portfolioId
	 * 
	 * @return Mono.
	 */
	@Query("select orderInfo,userName,change,earning,income,investment,overAllProfit,totalProfit,totalLoss,accountBalance,META().id AS _ID, META().cas AS _CAS from ums where META().id LIKE $1")
	Mono<UserPortfolio> findByIdPortfolio(String portfolioId);

	/**
	 * The findByIdPortfolio
	 * 
	 * @param String portfolioId
	 * 
	 * @return Mono.
	 */
	@Query("select orderInfo ,earning,investment,income,totalProfit,totalLoss,overAllProfit, META().id AS _ID, META().cas AS _CAS from ums where META().id  like 'PORTFOLIO::%'AND ANY orderIn IN orderInfo SATISFIES orderIn.orderId like $1 END;")
	Flux<UserPortfolio> findUserStockInPortofolio(String portfolioId);

	/**
	 * The saveOrder
	 * 
	 * @param UserPortfolio userPortfolio
	 * 
	 * @param String        userName.
	 * @return
	 * 
	 * @return Mono.
	 */
	default DocumentFragment<Mutation> saveOrder(UserPortfolio userPortfolio, String userName) {
		String investment = Constants.ZERO_DOUBLE_VALUE;
		if (userPortfolio.getInvestment() != null) {
			investment = userPortfolio.getInvestment();
		}
		DocumentFragment<Mutation> execute = getCouchbaseOperations().getCouchbaseBucket()
				.mutateIn(userPortfolio.getId()).upsertDocument(true).upsert(Constants.USER_NAME, userName)
				.upsert(Constants.ACCOUNT_BALANCE, userPortfolio.getAccountBalance())
				.upsert(Constants.INVESTMENT, investment).upsert(Constants.EARNING, userPortfolio.getEarning())
				.upsert(Constants.OVER_ALL_PROFIT, userPortfolio.getOverAllProfit())
				.upsert(Constants.TOTAL_PROFIT, userPortfolio.getTotalProfit())
				.upsert(Constants.TOTAL_LOSS, userPortfolio.getTotalLoss())
				.upsert(Constants.INCOME, userPortfolio.getIncome())
				.arrayAppend(Constants.ORDER_INFO, userPortfolio.getOrderInf()).execute();
		return execute;
	}

	/**
	 * The saveOrder
	 * 
	 * @param User   user
	 * 
	 * @param list   of orderInfo listOrderInfo.
	 * 
	 * @param String investment.
	 * 
	 * @param String earning.
	 * 
	 * @param String userName.
	 * 
	 * @return Mono.
	 */
	default void updatePortfolioOrder(User user, List<OrderInfo> listOrderInfo, String investment, String earning,
			String income, String profit, String loss, String overAllProfit, String accountBalance) {
		getCouchbaseOperations().getCouchbaseBucket().mutateIn(CommonUtils.portfolioUserId(user)).upsertDocument(true)
				.upsert(Constants.ACCOUNT_BALANCE, accountBalance).upsert(Constants.OVER_ALL_PROFIT, overAllProfit)
				.upsert(Constants.TOTAL_PROFIT, profit).upsert(Constants.TOTAL_LOSS, loss)
				.upsert(Constants.USER_NAME, user.getUsername()).upsert(Constants.INVESTMENT, investment)
				.upsert(Constants.INCOME, income).upsert(Constants.EARNING, earning)
				.upsert(Constants.ORDER_INFO, listOrderInfo).execute();
	}

	/**
	 * The getUserPortfolio
	 * 
	 * @param User userPortFolioId
	 * 
	 * 
	 * @return UserPortfolioResponse.
	 */
	default UserPortfolioResponse getUserPortfolio(String userPortFolioId) {
		final Gson gson = new Gson();
		final JsonObject getJson = getCouchbaseOperations().getCouchbaseBucket().get(userPortFolioId).content();
		UserPortfolioResponse userPortfolioResponse = gson.fromJson(getJson.toString(), UserPortfolioResponse.class);
		return userPortfolioResponse;
	}

	/**
	 * The updatePortfolioLiveFeeds
	 * 
	 * @param UserPortfolio userPortfolio
	 * 
	 * @param OrderInfo     orderInfoDetails
	 * 
	 * @param totalEarning
	 * 
	 * @param income.       .
	 */
	default void updatePortfolioLiveFeeds(UserPortfolio userPortfolio, List<OrderInfo> orderInfoDetails,
			Double totalEarning, Double income, Double totalProfit, Double totalLoss) {
		getCouchbaseOperations().getCouchbaseBucket().mutateIn(userPortfolio.getId())
				.upsert(Constants.TOTAL_PROFIT, totalProfit.toString())
				.upsert(Constants.TOTAL_LOSS, totalLoss.toString()).upsert(Constants.EARNING, totalEarning.toString())
				.upsert(Constants.ORDER_INFO, orderInfoDetails).upsert(Constants.INCOME, income.toString()).execute();
	}
}
