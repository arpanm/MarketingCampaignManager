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
import { ISegmentFilter } from 'app/shared/model/segment-filter.model';
import { getEntities as getSegmentFilters } from 'app/entities/segment-filter/segment-filter.reducer';
import { IFilterPossibleValue } from 'app/shared/model/filter-possible-value.model';
import { getEntity, updateEntity, createEntity, reset } from './filter-possible-value.reducer';

export const FilterPossibleValueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const filterMetadata = useAppSelector(state => state.filterMetadata.entities);
  const segmentFilters = useAppSelector(state => state.segmentFilter.entities);
  const filterPossibleValueEntity = useAppSelector(state => state.filterPossibleValue.entity);
  const loading = useAppSelector(state => state.filterPossibleValue.loading);
  const updating = useAppSelector(state => state.filterPossibleValue.updating);
  const updateSuccess = useAppSelector(state => state.filterPossibleValue.updateSuccess);

  const handleClose = () => {
    navigate('/filter-possible-value');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFilterMetadata({}));
    dispatch(getSegmentFilters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...filterPossibleValueEntity,
      ...values,
      filter: filterMetadata.find(it => it.id.toString() === values.filter.toString()),
      segmentFilter: segmentFilters.find(it => it.id.toString() === values.segmentFilter.toString()),
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
          ...filterPossibleValueEntity,
          filter: filterPossibleValueEntity?.filter?.id,
          segmentFilter: filterPossibleValueEntity?.segmentFilter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.filterPossibleValue.home.createOrEditLabel" data-cy="FilterPossibleValueCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.home.createOrEditLabel">
              Create or edit a FilterPossibleValue
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
                  id="filter-possible-value-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.uiName')}
                id="filter-possible-value-uiName"
                name="uiName"
                data-cy="uiName"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.attributeValue')}
                id="filter-possible-value-attributeValue"
                name="attributeValue"
                data-cy="attributeValue"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.isActive')}
                id="filter-possible-value-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.createdBy')}
                id="filter-possible-value-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.createdOn')}
                id="filter-possible-value-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.updatedBy')}
                id="filter-possible-value-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterPossibleValue.updatedOn')}
                id="filter-possible-value-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="filter-possible-value-filter"
                name="filter"
                data-cy="filter"
                label={translate('marketingCampaignManagerApp.filterPossibleValue.filter')}
                type="select"
              >
                <option value="" key="0" />
                {filterMetadata
                  ? filterMetadata.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="filter-possible-value-segmentFilter"
                name="segmentFilter"
                data-cy="segmentFilter"
                label={translate('marketingCampaignManagerApp.filterPossibleValue.segmentFilter')}
                type="select"
              >
                <option value="" key="0" />
                {segmentFilters
                  ? segmentFilters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/filter-possible-value" replace color="info">
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

export default FilterPossibleValueUpdate;
