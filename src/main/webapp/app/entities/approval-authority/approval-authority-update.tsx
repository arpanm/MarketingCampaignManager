import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApprovalAuthority } from 'app/shared/model/approval-authority.model';
import { VerticalType } from 'app/shared/model/enumerations/vertical-type.model';
import { getEntity, updateEntity, createEntity, reset } from './approval-authority.reducer';

export const ApprovalAuthorityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const approvalAuthorityEntity = useAppSelector(state => state.approvalAuthority.entity);
  const loading = useAppSelector(state => state.approvalAuthority.loading);
  const updating = useAppSelector(state => state.approvalAuthority.updating);
  const updateSuccess = useAppSelector(state => state.approvalAuthority.updateSuccess);
  const verticalTypeValues = Object.keys(VerticalType);

  const handleClose = () => {
    navigate('/approval-authority');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...approvalAuthorityEntity,
      ...values,
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
          vertial: 'JMD',
          ...approvalAuthorityEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.approvalAuthority.home.createOrEditLabel" data-cy="ApprovalAuthorityCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.home.createOrEditLabel">
              Create or edit a ApprovalAuthority
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
                  id="approval-authority-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.userId')}
                id="approval-authority-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.vertial')}
                id="approval-authority-vertial"
                name="vertial"
                data-cy="vertial"
                type="select"
              >
                {verticalTypeValues.map(verticalType => (
                  <option value={verticalType} key={verticalType}>
                    {translate('marketingCampaignManagerApp.VerticalType.' + verticalType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.isActive')}
                id="approval-authority-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.createdBy')}
                id="approval-authority-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.createdOn')}
                id="approval-authority-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.updatedBy')}
                id="approval-authority-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.approvalAuthority.updatedOn')}
                id="approval-authority-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/approval-authority" replace color="info">
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

export default ApprovalAuthorityUpdate;
