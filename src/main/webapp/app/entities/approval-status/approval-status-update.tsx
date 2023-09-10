import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApprovalAuthority } from 'app/shared/model/approval-authority.model';
import { getEntities as getApprovalAuthorities } from 'app/entities/approval-authority/approval-authority.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { IApprovalStatus } from 'app/shared/model/approval-status.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';
import { getEntity, updateEntity, createEntity, reset } from './approval-status.reducer';

export const ApprovalStatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const approvalAuthorities = useAppSelector(state => state.approvalAuthority.entities);
  const campaigns = useAppSelector(state => state.campaign.entities);
  const approvalStatusEntity = useAppSelector(state => state.approvalStatus.entity);
  const loading = useAppSelector(state => state.approvalStatus.loading);
  const updating = useAppSelector(state => state.approvalStatus.updating);
  const updateSuccess = useAppSelector(state => state.approvalStatus.updateSuccess);
  const statusTypeValues = Object.keys(StatusType);

  const handleClose = () => {
    navigate('/approval-status');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getApprovalAuthorities({}));
    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...approvalStatusEntity,
      ...values,
      approvedBy: approvalAuthorities.find(it => it.id.toString() === values.approvedBy.toString()),
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
          statusType: 'Draft',
          ...approvalStatusEntity,
          approvedBy: approvalStatusEntity?.approvedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.approvalStatus.home.createOrEditLabel" data-cy="ApprovalStatusCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.approvalStatus.home.createOrEditLabel">
              Create or edit a ApprovalStatus
            </Translate>
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
                  id="approval-status-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.statusType')}
                id="approval-status-statusType"
                name="statusType"
                data-cy="statusType"
                type="select"
              >
                {statusTypeValues.map(statusType => (
                  <option value={statusType} key={statusType}>
                    {translate('marketingCampaignManagerApp.StatusType.' + statusType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.isActive')}
                id="approval-status-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.createdBy')}
                id="approval-status-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.createdOn')}
                id="approval-status-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.updatedBy')}
                id="approval-status-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalStatus.updatedOn')}
                id="approval-status-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="approval-status-approvedBy"
                name="approvedBy"
                data-cy="approvedBy"
                label={translate('marketingCampaignManagerApp.approvalStatus.approvedBy')}
                type="select"
              >
                <option value="" key="0" />
                {approvalAuthorities
                  ? approvalAuthorities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approval-status" replace color="info">
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

export default ApprovalStatusUpdate;
