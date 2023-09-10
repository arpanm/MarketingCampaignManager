import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './filter-metadata.reducer';

export const FilterMetadataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const filterMetadataEntity = useAppSelector(state => state.filterMetadata.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="filterMetadataDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.filterMetadata.detail.title">FilterMetadata</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.name">Name</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.name}</dd>
          <dt>
            <span id="filterType">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.filterType">Filter Type</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.filterType}</dd>
          <dt>
            <span id="uiType">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.uiType">Ui Type</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.uiType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {filterMetadataEntity.createdOn ? (
              <TextFormat value={filterMetadataEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{filterMetadataEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.filterMetadata.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {filterMetadataEntity.updatedOn ? (
              <TextFormat value={filterMetadataEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.filterMetadata.mapping">Mapping</Translate>
          </dt>
          <dd>{filterMetadataEntity.mapping ? filterMetadataEntity.mapping.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/filter-metadata" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filter-metadata/${filterMetadataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FilterMetadataDetail;
