import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { IEvents } from 'app/shared/model/events.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';
import { getEntity, updateEntity, createEntity, reset } from './events.reducer';

export const EventsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const campaigns = useAppSelector(state => state.campaign.entities);
  const eventsEntity = useAppSelector(state => state.events.entity);
  const loading = useAppSelector(state => state.events.loading);
  const updating = useAppSelector(state => state.events.updating);
  const updateSuccess = useAppSelector(state => state.events.updateSuccess);
  const eventTypeValues = Object.keys(EventType);

  const handleClose = () => {
    navigate('/events');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...eventsEntity,
      ...values,
      campaign: campaigns.find(it => it.id.toString() === values.campaign.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          eventType: 'Send',
          ...eventsEntity,
          campaign: eventsEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.events.home.createOrEditLabel" data-cy="EventsCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.events.home.createOrEditLabel">Create or edit a Events</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="events-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.count')}
                id="events-count"
                name="count"
                data-cy="count"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.eventType')}
                id="events-eventType"
                name="eventType"
                data-cy="eventType"
                type="select"
              >
                {eventTypeValues.map(eventType => (
                  <option value={eventType} key={eventType}>
                    {translate('marketingCampaignManagerApp.EventType.' + eventType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.isActive')}
                id="events-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.createdBy')}
                id="events-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.createdOn')}
                id="events-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.updatedBy')}
                id="events-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.events.updatedOn')}
                id="events-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="events-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('marketingCampaignManagerApp.events.campaign')}
                type="select"
              >
                <option value="" key="0" />
                {campaigns
                  ? campaigns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/events" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EventsUpdate;
