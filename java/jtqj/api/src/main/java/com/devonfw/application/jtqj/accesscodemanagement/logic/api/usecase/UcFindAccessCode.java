package com.devonfw.application.jtqj.accesscodemanagement.logic.api.usecase;

import java.util.List;

import org.springframework.data.domain.Page;

import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeCto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeEto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeSearchCriteriaTo;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.NextCodeCto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.RemainingCodes;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.Uuid;

public interface UcFindAccessCode {

	/**
	 * Returns a composite AccessCode by its id 'id'.
	 *
	 * @param id The id 'id' of the AccessCode.
	 * @return The {@link AccessCodeCto} with id 'id'
	 */
	AccessCodeCto findAccessCodeCto(long id);

	/**
	 * Returns a paginated list of composite AccessCodes matching the search
	 * criteria.
	 *
	 * @param criteria the {@link AccessCodeSearchCriteriaTo}.
	 * @return the {@link List} of matching {@link AccessCodeCto}s.
	 */
	Page<AccessCodeCto> findAccessCodeCtos(AccessCodeSearchCriteriaTo criteria);

	/**
	 * Returns visitors code given by uuid
	 *
	 * @param uuid the uuid 'uuid' of the AccessCode.
	 * @return The {@link AccessCodeEto} with such uuid 'uuid'
	 */
	AccessCodeCto findUuidAccessCode(Uuid uuid);

	/**
	 * Returns a AccessCode by its id 'id'.
	 *
	 * @param id The id 'id' of the AccessCode.
	 * @return The {@link AccessCodeEto} with id 'id'
	 */
	AccessCodeEto findAccessCode(long id);

	/**
	 * Returns a paginated list of AccessCodes matching the search criteria.
	 *
	 * @param criteria the {@link AccessCodeSearchCriteriaTo}.
	 * @return the {@link List} of matching {@link AccessCodeEto}s.
	 */
	Page<AccessCodeEto> findAccessCodes(AccessCodeSearchCriteriaTo criteria);

	/**
	 * Returns a list of Etos associated with a queue.
	 *
	 * @param queueId.
	 * @return the {@link List} of matching {@link AccessCodeEto}s.
	 */
	List<AccessCodeEto> findByQueue(long queueId);

	/**
	 * Returns a current AccessCode of dailyQueue.
	 *
	 * @return The {@link AccessCodeEto} with status Attending
	 */
	AccessCodeEto findCurrentCode();

	/**
	 * Returns a next AccessCode of dailyQueue.
	 *
	 * @param id The id 'id' of the dailyQueue.
	 * @return The {@link AccessCodeEto} with status waiting and farthest in createdTime
	 */
	NextCodeCto findNextCode(long queueId);

	/**
	 * Returns a next AccessCode of dailyQueue.
	 *
	 * @param id The id 'id' of the dailyQueue.
	 * @return The {@link AccessCodeEto} with status waiting and farthest in createdTime
	 */
	RemainingCodes findRemainingCodes();
}
