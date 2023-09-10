import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './segment.reducer';

export const SegmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const segmentEntity = useAppSelector(state => state.segment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="segmentDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.segment.detail.title">Segment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="marketingCampaignManagerApp.segment.name">Name</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.name}</dd>
          <dt>
            <span id="sourceTable">
              <Translate contentKey="marketingCampaignManagerApp.segment.sourceTable">Source Table</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.sourceTable}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.segment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.segment.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.segment.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {segmentEntity.createdOn ? <TextFormat value={segmentEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.segment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{segmentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.segment.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {segmentEntity.updatedOn ? <TextFormat value={segmentEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/segment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/segment/${segmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SegmentDetail;
