package com.devonfw.application.jtqj.accesscodemanagement.logic.impl.usecase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.devonfw.application.jtqj.accesscodemanagement.dataaccess.api.AccessCodeEntity;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.Accesscodemanagement;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeCto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeEto;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.to.AccessCodeSearchCriteriaTo;
import com.devonfw.application.jtqj.accesscodemanagement.logic.api.usecase.UcManageAccessCode;
import com.devonfw.application.jtqj.accesscodemanagement.logic.base.usecase.AbstractAccessCodeUc;
import com.devonfw.application.jtqj.general.common.api.Status;
import com.devonfw.application.jtqj.queuemanagement.logic.api.to.QueueEto;

/**
 * Use case implementation for modifying and deleting AccessCodes
 */
@Named
@Validated
@Transactional
public class UcManageAccessCodeImpl extends AbstractAccessCodeUc implements UcManageAccessCode {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UcManageAccessCodeImpl.class);

	@Override
	public boolean deleteAccessCode(long accessCodeId) {

		AccessCodeEntity accessCode = getAccessCodeRepository().find(accessCodeId);
		getAccessCodeRepository().delete(accessCode);
		LOG.debug("The accessCode with id '{}' has been deleted.", accessCodeId);
		return true;
	}

	@Override
	public AccessCodeEto saveAccessCode(AccessCodeEto accessCode) {

		Objects.requireNonNull(accessCode, "accessCode");

		AccessCodeEntity accessCodeEntity = getBeanMapper().map(accessCode, AccessCodeEntity.class);

		if (accessCodeEntity.getCreatedDate() == null) {
			accessCodeEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		}
		// initialize, validate accessCodeEntity here if necessary
		AccessCodeEntity resultEntity = getAccessCodeRepository().save(accessCodeEntity);
		LOG.debug("AccessCode with id '{}' has been created.", resultEntity.getId());
		return getBeanMapper().map(resultEntity, AccessCodeEto.class);
	}

	@Inject
	Accesscodemanagement accessCodeManagement;

	@Override
	public void updateCodesOnStartQueue(long queueId) {
		List<AccessCodeEto> accessCodes = accessCodeManagement.findByQueue(queueId);
		for (AccessCodeEto eto : accessCodes) {
			AccessCodeEntity entity = getBeanMapper().map(eto, AccessCodeEntity.class);
			entity.setStatus(Status.WAITING);
			getAccessCodeRepository().save(entity);
		}
	}

}
