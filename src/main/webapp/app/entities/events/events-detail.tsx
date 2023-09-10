import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './events.reducer';

export const EventsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventsEntity = useAppSelector(state => state.events.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventsDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.events.detail.title">Events</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.id}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="marketingCampaignManagerApp.events.count">Count</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.count}</dd>
          <dt>
            <span id="eventType">
              <Translate contentKey="marketingCampaignManagerApp.events.eventType">Event Type</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.eventType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.events.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.events.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.events.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {eventsEntity.createdOn ? <TextFormat value={eventsEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.events.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{eventsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.events.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {eventsEntity.updatedOn ? <TextFormat value={eventsEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.events.campaign">Campaign</Translate>
          </dt>
          <dd>{eventsEntity.campaign ? eventsEntity.campaign.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/events" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/events/${eventsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventsDetail;
