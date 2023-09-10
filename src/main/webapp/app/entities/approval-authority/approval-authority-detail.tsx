import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './approval-authority.reducer';

export const ApprovalAuthorityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const approvalAuthorityEntity = useAppSelector(state => state.approvalAuthority.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="approvalAuthorityDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.detail.title">ApprovalAuthority</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.id}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.userId}</dd>
          <dt>
            <span id="vertial">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.vertial">Vertial</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.vertial}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {approvalAuthorityEntity.createdOn ? (
              <TextFormat value={approvalAuthorityEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{approvalAuthorityEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {approvalAuthorityEntity.updatedOn ? (
              <TextFormat value={approvalAuthorityEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/approval-authority" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/approval-authority/${approvalAuthorityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApprovalAuthorityDetail;
