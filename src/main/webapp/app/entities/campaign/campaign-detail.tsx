import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './campaign.reducer';

export const CampaignDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const campaignEntity = useAppSelector(state => state.campaign.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="campaignDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.campaign.detail.title">Campaign</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="marketingCampaignManagerApp.campaign.name">Name</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.name}</dd>
          <dt>
            <span id="vertial">
              <Translate contentKey="marketingCampaignManagerApp.campaign.vertial">Vertial</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.vertial}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="marketingCampaignManagerApp.campaign.channel">Channel</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.channel}</dd>
          <dt>
            <span id="schedule">
              <Translate contentKey="marketingCampaignManagerApp.campaign.schedule">Schedule</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.schedule}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="marketingCampaignManagerApp.campaign.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.startDate ? <TextFormat value={campaignEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="marketingCampaignManagerApp.campaign.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.endDate ? <TextFormat value={campaignEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.campaign.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.campaign.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.campaign.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.createdOn ? <TextFormat value={campaignEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.campaign.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.campaign.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.updatedOn ? <TextFormat value={campaignEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.campaign.approvalStatus">Approval Status</Translate>
          </dt>
          <dd>{campaignEntity.approvalStatus ? campaignEntity.approvalStatus.id : ''}</dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.campaign.segment">Segment</Translate>
          </dt>
          <dd>{campaignEntity.segment ? campaignEntity.segment.id : ''}</dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.campaign.template">Template</Translate>
          </dt>
          <dd>{campaignEntity.template ? campaignEntity.template.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/campaign" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campaign/${campaignEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CampaignDetail;
