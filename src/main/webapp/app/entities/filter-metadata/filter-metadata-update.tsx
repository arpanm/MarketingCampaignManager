import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFilterSourceMapping } from 'app/shared/model/filter-source-mapping.model';
import { getEntities as getFilterSourceMappings } from 'app/entities/filter-source-mapping/filter-source-mapping.reducer';
import { ISegmentFilter } from 'app/shared/model/segment-filter.model';
import { getEntities as getSegmentFilters } from 'app/entities/segment-filter/segment-filter.reducer';
import { IFilterMetadata } from 'app/shared/model/filter-metadata.model';
import { FilterType } from 'app/shared/model/enumerations/filter-type.model';
import { FilterUiType } from 'app/shared/model/enumerations/filter-ui-type.model';
import { getEntity, updateEntity, createEntity, reset } from './filter-metadata.reducer';

export const FilterMetadataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const filterSourceMappings = useAppSelector(state => state.filterSourceMapping.entities);
  const segmentFilters = useAppSelector(state => state.segmentFilter.entities);
  const filterMetadataEntity = useAppSelector(state => state.filterMetadata.entity);
  const loading = useAppSelector(state => state.filterMetadata.loading);
  const updating = useAppSelector(state => state.filterMetadata.updating);
  const updateSuccess = useAppSelector(state => state.filterMetadata.updateSuccess);
  const filterTypeValues = Object.keys(FilterType);
  const filterUiTypeValues = Object.keys(FilterUiType);

  const handleClose = () => {
    navigate('/filter-metadata');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFilterSourceMappings({}));
    dispatch(getSegmentFilters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...filterMetadataEntity,
      ...values,
      mapping: filterSourceMappings.find(it => it.id.toString() === values.mapping.toString()),
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
          filterType: 'SingleSelect',
          uiType: 'Checkbox',
          ...filterMetadataEntity,
          mapping: filterMetadataEntity?.mapping?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.filterMetadata.home.createOrEditLabel" data-cy="FilterMetadataCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.filterMetadata.home.createOrEditLabel">
              Create or edit a FilterMetadata
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
                  id="filter-metadata-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.name')}
                id="filter-metadata-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.desc')}
                id="filter-metadata-desc"
                name="desc"
                data-cy="desc"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.filterType')}
                id="filter-metadata-filterType"
                name="filterType"
                data-cy="filterType"
                type="select"
              >
                {filterTypeValues.map(filterType => (
                  <option value={filterType} key={filterType}>
                    {translate('marketingCampaignManagerApp.FilterType.' + filterType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.uiType')}
                id="filter-metadata-uiType"
                name="uiType"
                data-cy="uiType"
                type="select"
              >
                {filterUiTypeValues.map(filterUiType => (
                  <option value={filterUiType} key={filterUiType}>
                    {translate('marketingCampaignManagerApp.FilterUiType.' + filterUiType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.isActive')}
                id="filter-metadata-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.createdBy')}
                id="filter-metadata-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.createdOn')}
                id="filter-metadata-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.updatedBy')}
                id="filter-metadata-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.filterMetadata.updatedOn')}
                id="filter-metadata-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="filter-metadata-mapping"
                name="mapping"
                data-cy="mapping"
                label={translate('marketingCampaignManagerApp.filterMetadata.mapping')}
                type="select"
              >
                <option value="" key="0" />
                {filterSourceMappings
                  ? filterSourceMappings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/filter-metadata" replace color="info">
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

export default FilterMetadataUpdate;
