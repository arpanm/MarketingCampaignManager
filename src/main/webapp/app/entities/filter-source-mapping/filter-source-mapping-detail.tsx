import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './filter-source-mapping.reducer';

export const FilterSourceMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const filterSourceMappingEntity = useAppSelector(state => state.filterSourceMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="filterSourceMappingDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.detail.title">FilterSourceMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.id}</dd>
          <dt>
            <span id="sourceTable">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.sourceTable">Source Table</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.sourceTable}</dd>
          <dt>
            <span id="atrributeMapping">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.atrributeMapping">Atrribute Mapping</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.atrributeMapping}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {filterSourceMappingEntity.createdOn ? (
              <TextFormat value={filterSourceMappingEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{filterSourceMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.filterSourceMapping.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {filterSourceMappingEntity.updatedOn ? (
              <TextFormat value={filterSourceMappingEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/filter-source-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filter-source-mapping/${filterSourceMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FilterSourceMappingDetail;
