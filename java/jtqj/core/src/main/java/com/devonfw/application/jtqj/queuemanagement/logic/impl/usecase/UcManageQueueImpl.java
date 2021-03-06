package com.devonfw.application.jtqj.queuemanagement.logic.impl.usecase;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.devonfw.application.jtqj.accesscodemanagement.logic.api.Accesscodemanagement;
import com.devonfw.application.jtqj.accesscodemanagement.service.impl.rest.ServerSse;
import com.devonfw.application.jtqj.queuemanagement.dataaccess.api.QueueEntity;
import com.devonfw.application.jtqj.queuemanagement.logic.api.to.QueueEto;
import com.devonfw.application.jtqj.queuemanagement.logic.api.usecase.UcManageQueue;
import com.devonfw.application.jtqj.queuemanagement.logic.base.usecase.AbstractQueueUc;

/**
 * Use case implementation for modifying and deleting Queues
 */
@Named
@Validated
@Transactional
public class UcManageQueueImpl extends AbstractQueueUc implements UcManageQueue {

	private static final int DEFAULT_MIN_ATTENTION_TIME = 60;
	private static final boolean QUEUE_NOT_STARTED = false;
	/** Logger instance. */
	private static final Logger LOG = LoggerFactory.getLogger(UcManageQueueImpl.class);

	@Override
	public boolean deleteQueue(long queueId) {

		QueueEntity queue = getQueueRepository().find(queueId);
		getQueueRepository().delete(queue);
		LOG.debug("The queue with id '{}' has been deleted.", queueId);
		return true;
	}

	@Override
	public QueueEto saveQueue(QueueEto queue) {

		Objects.requireNonNull(queue, "queue");

		QueueEntity queueEntity = getBeanMapper().map(queue, QueueEntity.class);

		// initialize, validate queueEntity here if necessary
		if (queueEntity.getCreatedDate() == null) {
			queueEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			queueEntity.setMinAttentionTime(DEFAULT_MIN_ATTENTION_TIME);
			queueEntity.setStarted(QUEUE_NOT_STARTED);
		}

		QueueEntity resultEntity = getQueueRepository().save(queueEntity);
		LOG.debug("Queue with id '{}' has been created.", resultEntity.getId());
		return getBeanMapper().map(resultEntity, QueueEto.class);
	}

	@Inject
	Accesscodemanagement accessCodeManagement;

	@Override
	public QueueEto startQueue(QueueEto queue) {
		QueueEntity queueEntity = getBeanMapper().map(queue, QueueEntity.class);

		if (queueEntity.getStarted()) {
			LOG.debug("Queue with id '{}' is already started.", queueEntity.getId());
		} else {
			queueEntity.setStarted(true);
			QueueEntity resultEntity = getQueueRepository().save(queueEntity);

			// Update all codes related to such queue
			accessCodeManagement.updateCodesOnStartQueue(resultEntity.getId());
			LOG.debug("Queue with id '{}' has been started.", resultEntity.getId());
			queue = getBeanMapper().map(resultEntity, QueueEto.class);

			// SSE
	        List<SseEmitter> sseEmitterListToRemove = new ArrayList<>();
	        ServerSse.emitters.forEach((SseEmitter emitter) -> {
	            try {
	            	emitter.send(SseEmitter.event().data(getBeanMapper().map(resultEntity, QueueEto.class), MediaType.APPLICATION_JSON).name("QUEUE_STARTED"));
	            } catch (IOException e) {
	                emitter.complete();
	                sseEmitterListToRemove.add(emitter);
	                LOG.error(e.toString());
	            }
	        });
	        ServerSse.emitters.removeAll(sseEmitterListToRemove);

		}
		return queue;
	}
}
