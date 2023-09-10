import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISegment } from 'app/shared/model/segment.model';
import { getEntities as getSegments } from 'app/entities/segment/segment.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { getEntities as getTemplates } from 'app/entities/template/template.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { ChannelType } from 'app/shared/model/enumerations/channel-type.model';
import { ScheduleType } from 'app/shared/model/enumerations/schedule-type.model';
import { getEntity, updateEntity, createEntity, reset } from './campaign.reducer';

export const CampaignUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const segments = useAppSelector(state => state.segment.entities);
  const templates = useAppSelector(state => state.template.entities);
  const campaignEntity = useAppSelector(state => state.campaign.entity);
  const loading = useAppSelector(state => state.campaign.loading);
  const updating = useAppSelector(state => state.campaign.updating);
  const updateSuccess = useAppSelector(state => state.campaign.updateSuccess);
  const channelTypeValues = Object.keys(ChannelType);
  const scheduleTypeValues = Object.keys(ScheduleType);

  const handleClose = () => {
    navigate('/campaign');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getSegments({}));
    dispatch(getTemplates({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...campaignEntity,
      ...values,
      segment: segments.find(it => it.id.toString() === values.segment.toString()),
      template: templates.find(it => it.id.toString() === values.template.toString()),
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
          channel: 'Email',
          schedule: 'Hourly',
          ...campaignEntity,
          segment: campaignEntity?.segment?.id,
          template: campaignEntity?.template?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.campaign.home.createOrEditLabel" data-cy="CampaignCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
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
                  id="campaign-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.name')}
                id="campaign-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.channel')}
                id="campaign-channel"
                name="channel"
                data-cy="channel"
                type="select"
              >
                {channelTypeValues.map(channelType => (
                  <option value={channelType} key={channelType}>
                    {translate('marketingCampaignManagerApp.ChannelType.' + channelType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.schedule')}
                id="campaign-schedule"
                name="schedule"
                data-cy="schedule"
                type="select"
              >
                {scheduleTypeValues.map(scheduleType => (
                  <option value={scheduleType} key={scheduleType}>
                    {translate('marketingCampaignManagerApp.ScheduleType.' + scheduleType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.startDate')}
                id="campaign-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.endDate')}
                id="campaign-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.isActive')}
                id="campaign-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.createdBy')}
                id="campaign-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.createdOn')}
                id="campaign-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.updatedBy')}
                id="campaign-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.campaign.updatedOn')}
                id="campaign-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="campaign-segment"
                name="segment"
                data-cy="segment"
                label={translate('marketingCampaignManagerApp.campaign.segment')}
                type="select"
              >
                <option value="" key="0" />
                {segments
                  ? segments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="campaign-template"
                name="template"
                data-cy="template"
                label={translate('marketingCampaignManagerApp.campaign.template')}
                type="select"
              >
                <option value="" key="0" />
                {templates
                  ? templates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/campaign" replace color="info">
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

export default CampaignUpdate;
