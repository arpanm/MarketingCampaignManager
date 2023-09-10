import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './filter-possible-value.reducer';

export const FilterPossibleValueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const filterPossibleValueEntity = useAppSelector(state => state.filterPossibleValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="filterPossibleValueDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.detail.title">FilterPossibleValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.id}</dd>
          <dt>
            <span id="uiName">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.uiName">Ui Name</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.uiName}</dd>
          <dt>
            <span id="attributeValue">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.attributeValue">Attribute Value</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.attributeValue}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {filterPossibleValueEntity.createdOn ? (
              <TextFormat value={filterPossibleValueEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{filterPossibleValueEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {filterPossibleValueEntity.updatedOn ? (
              <TextFormat value={filterPossibleValueEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.filter">Filter</Translate>
          </dt>
          <dd>{filterPossibleValueEntity.filter ? filterPossibleValueEntity.filter.id : ''}</dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.filterPossibleValue.segmentFilter">Segment Filter</Translate>
          </dt>
          <dd>{filterPossibleValueEntity.segmentFilter ? filterPossibleValueEntity.segmentFilter.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/filter-possible-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/filter-possible-value/${filterPossibleValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FilterPossibleValueDetail;
