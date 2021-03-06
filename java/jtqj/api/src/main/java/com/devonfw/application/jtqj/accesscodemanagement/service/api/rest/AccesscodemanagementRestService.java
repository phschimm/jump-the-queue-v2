package com.devonfw.application.jtqj.accesscodemanagement.service.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;

import com.devonfw.application.jtqj.accesscodemanagement.logic.api.Accesscodemanagement;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeCto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeEto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeSearchCriteriaTo;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.EstimatedTime;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.NextCodeCto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.RemainingCodes;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.Uuid;
import com.devonfw.application.jtqj.queuemanagement.logic.api.to.QueueEto;

/**
 * The service interface for REST calls in order to execute the logic of
 * component {@link Accesscodemanagement}.
 */
@Path("/accesscodemanagement/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AccesscodemanagementRestService {

	/**
	 * Delegates to {@link Accesscodemanagement#findAccessCodeCto}.
	 *
	 * @param id the ID of the {@link AccessCodeCto}
	 * @return the {@link AccessCodeCto}
	 */
	@GET
	@Path("/accesscode/cto/{id}/")
	public AccessCodeCto getAccessCodeCto(@PathParam("id") long id);

	/**
	 * Delegates to {@link Accesscodemanagement#findAccessCodeCtos}.
	 *
	 * @param searchCriteriaTo the pagination and search criteria to be used for
	 *                         finding accesscodes.
	 * @return the {@link Page list} of matching {@link AccessCodeCto}s.
	 */
	@Path("/accesscode/cto/search")
	@POST
	public Page<AccessCodeCto> findAccessCodeCtos(AccessCodeSearchCriteriaTo searchCriteriaTo);

	/**
	 * Delegates to {@link Accesscodemanagement#findAccessCode}.
	 *
	 * @param id the ID of the {@link AccessCodeEto}
	 * @return the {@link AccessCodeEto}
	 */
	@GET
	@Path("/accesscode/{id}/")
	public AccessCodeEto getAccessCode(@PathParam("id") long id);

	/**
	 * Delegates to {@link Accesscodemanagement#saveAccessCode}.
	 *
	 * @param accesscode the {@link AccessCodeEto} to be saved
	 * @return the recently created {@link AccessCodeEto}
	 */
	@POST
	@Path("/accesscode/")
	public AccessCodeEto saveAccessCode(AccessCodeEto accesscode);

	/**
	 * Delegates to {@link Accesscodemanagement#deleteAccessCode}.
	 *
	 * @param id ID of the {@link AccessCodeEto} to be deleted
	 */
	@DELETE
	@Path("/accesscode/{id}/")
	public void deleteAccessCode(@PathParam("id") long id);

	/**
	 * Delegates to {@link Accesscodemanagement#findAccessCodeEtos}.
	 *
	 * @param searchCriteriaTo the pagination and search criteria to be used for
	 *                         finding accesscodes.
	 * @return the {@link Page list} of matching {@link AccessCodeEto}s.
	 */
	@Path("/accesscode/search")
	@POST
	public Page<AccessCodeEto> findAccessCodes(AccessCodeSearchCriteriaTo searchCriteriaTo);

	/**
	 * Delegates to {@link Accesscodemanagement#findUuidAccessCode}.
	 *
	 * @param uuid the uuid related to the accessCode in DB, if not found will create it
	 * @return the {@link AccessCodeCto}.
	 */
	@Path("/accesscode/uuid")
	@POST
	public AccessCodeCto findUuidAccessCode(Uuid uuid);

	/**
	 * Delegates to {@link Accesscodemanagement#callNextCode}.
	 *
	 * @return the next accessCode if available{@link AccessCodeCto}.
	 */
	@Path("/accesscode/next")
	@POST
	public NextCodeCto callNextCode();

	/**
	 * Delegates to {@link Accesscodemanagement#findCurrentCode}.
	 *
	 * @return the current accessCode if available{@link AccessCodeEto}.
	 */
	@Path("/accesscode/current")
	@POST
	public AccessCodeEto findCurrentCode();

	/**
	 * Delegates to {@link Accesscodemanagement#calculateEstimatedTime}.
	 *
	 * @param code
	 * @return the the estimated time {@link EstimatedTime}.
	 */
	@Path("/accesscode/estimated")
	@POST
	public EstimatedTime getEstimatedTime(AccessCodeEto code);

	/**
	 * Delegates to {@link Accesscodemanagement#remainingCodes}.
	 *
	 * @return how many codes with status == waiting {@link RemainingCode}.
	 */
	@Path("/accesscode/remaining")
	@POST
	public RemainingCodes getRemaningCodes();
}
