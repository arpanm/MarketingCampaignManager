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
import { ISegment } from 'app/shared/model/segment.model';
import { getEntities as getSegments } from 'app/entities/segment/segment.reducer';
import { ISegmentFilter } from 'app/shared/model/segment-filter.model';
import { getEntity, updateEntity, createEntity, reset } from './segment-filter.reducer';

export const SegmentFilterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const filterMetadata = useAppSelector(state => state.filterMetadata.entities);
  const segments = useAppSelector(state => state.segment.entities);
  const segmentFilterEntity = useAppSelector(state => state.segmentFilter.entity);
  const loading = useAppSelector(state => state.segmentFilter.loading);
  const updating = useAppSelector(state => state.segmentFilter.updating);
  const updateSuccess = useAppSelector(state => state.segmentFilter.updateSuccess);

  const handleClose = () => {
    navigate('/segment-filter');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFilterMetadata({}));
    dispatch(getSegments({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...segmentFilterEntity,
      ...values,
      filter: filterMetadata.find(it => it.id.toString() === values.filter.toString()),
      segment: segments.find(it => it.id.toString() === values.segment.toString()),
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
          ...segmentFilterEntity,
          filter: segmentFilterEntity?.filter?.id,
          segment: segmentFilterEntity?.segment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.segmentFilter.home.createOrEditLabel" data-cy="SegmentFilterCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.segmentFilter.home.createOrEditLabel">
              Create or edit a SegmentFilter
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
                  id="segment-filter-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.name')}
                id="segment-filter-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.title')}
                id="segment-filter-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.isActive')}
                id="segment-filter-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.createdBy')}
                id="segment-filter-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.createdOn')}
                id="segment-filter-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.updatedBy')}
                id="segment-filter-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.segmentFilter.updatedOn')}
                id="segment-filter-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                id="segment-filter-filter"
                name="filter"
                data-cy="filter"
                label={translate('marketingCampaignManagerApp.segmentFilter.filter')}
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
                id="segment-filter-segment"
                name="segment"
                data-cy="segment"
                label={translate('marketingCampaignManagerApp.segmentFilter.segment')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/segment-filter" replace color="info">
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

export default SegmentFilterUpdate;
