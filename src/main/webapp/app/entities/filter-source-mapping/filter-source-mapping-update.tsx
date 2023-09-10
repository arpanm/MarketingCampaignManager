import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFilterMetadata } from 'app/shared/model/filter-metadata.model';
import { getEntities as getFilterMetadata } from 'app/entities/filter-metadata/filter-metadata.reducer';
import { IFilterSourceMapping } from 'app/shared/model/filter-source-mapping.model';
import { getEntity, updateEntity, createEntity, reset } from './filter-source-mapping.reducer';

export const FilterSourceMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const filterMetadata = useAppSelector(state => state.filterMetadata.entities);
  const filterSourceMappingEntity = useAppSelector(state => state.filterSourceMapping.entity);
  const loading = useAppSelector(state => state.filterSourceMapping.loading);
  const updating = useAppSelector(state => state.filterSourceMapping.updating);
  const updateSuccess = useAppSelector(state => state.filterSourceMapping.updateSuccess);

  const handleClose = () => {
    navigate('/filter-source-mapping');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFilterMetadata({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...filterSourceMappingEntity,
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
          ...filterSourceMappingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.filterSourceMapping.home.createOrEditLabel" data-cy="FilterSourceMappingCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.home.createOrEditLabel">
              Create or edit a FilterSourceMapping
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
                  id="filter-source-mapping-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.sourceTable')}
                id="filter-source-mapping-sourceTable"
                name="sourceTable"
                data-cy="sourceTable"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.atrributeMapping')}
                id="filter-source-mapping-atrributeMapping"
                name="atrributeMapping"
                data-cy="atrributeMapping"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.isActive')}
                id="filter-source-mapping-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.createdBy')}
                id="filter-source-mapping-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.createdOn')}
                id="filter-source-mapping-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.updatedBy')}
                id="filter-source-mapping-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterSourceMapping.updatedOn')}
                id="filter-source-mapping-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/filter-source-mapping" replace color="info">
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

export default FilterSourceMappingUpdate;
