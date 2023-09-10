import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './segment-filter.reducer';

export const SegmentFilterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const segmentFilterEntity = useAppSelector(state => state.segmentFilter.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="segmentFilterDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.segmentFilter.detail.title">SegmentFilter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.name">Name</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.title">Title</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.title}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {segmentFilterEntity.createdOn ? (
              <TextFormat value={segmentFilterEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{segmentFilterEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.segmentFilter.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {segmentFilterEntity.updatedOn ? (
              <TextFormat value={segmentFilterEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.segmentFilter.filter">Filter</Translate>
          </dt>
          <dd>{segmentFilterEntity.filter ? segmentFilterEntity.filter.id : ''}</dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.segmentFilter.segment">Segment</Translate>
          </dt>
          <dd>{segmentFilterEntity.segment ? segmentFilterEntity.segment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/segment-filter" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/segment-filter/${segmentFilterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SegmentFilterDetail;
