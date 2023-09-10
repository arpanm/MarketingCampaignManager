import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approval-status.reducer';

export const ApprovalStatusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approvalStatusEntity = useAppSelector(state => state.approvalStatus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approvalStatusDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.approvalStatus.detail.title">ApprovalStatus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{approvalStatusEntity.id}</dd>
          <dt>
            <span id="statusType">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.statusType">Status Type</Translate>
            </span>
          </dt>
          <dd>{approvalStatusEntity.statusType}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{approvalStatusEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{approvalStatusEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {approvalStatusEntity.createdOn ? (
              <TextFormat value={approvalStatusEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{approvalStatusEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.approvalStatus.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {approvalStatusEntity.updatedOn ? (
              <TextFormat value={approvalStatusEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.approvalStatus.approvedBy">Approved By</Translate>
          </dt>
          <dd>{approvalStatusEntity.approvedBy ? approvalStatusEntity.approvedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/approval-status" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approval-status/${approvalStatusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApprovalStatusDetail;
