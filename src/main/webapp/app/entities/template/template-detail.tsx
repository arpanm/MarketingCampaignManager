import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template.reducer';

export const TemplateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateEntity = useAppSelector(state => state.template.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.template.detail.title">Template</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="marketingCampaignManagerApp.template.name">Name</Translate>
            </span>
          </dt>
          <dd>{templateEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="marketingCampaignManagerApp.template.title">Title</Translate>
            </span>
          </dt>
          <dd>{templateEntity.title}</dd>
          <dt>
            <span id="subTitle">
              <Translate contentKey="marketingCampaignManagerApp.template.subTitle">Sub Title</Translate>
            </span>
          </dt>
          <dd>{templateEntity.subTitle}</dd>
          <dt>
            <span id="body">
              <Translate contentKey="marketingCampaignManagerApp.template.body">Body</Translate>
            </span>
          </dt>
          <dd>{templateEntity.body}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="marketingCampaignManagerApp.template.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{templateEntity.imageUrl}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.template.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{templateEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.template.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{templateEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.template.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {templateEntity.createdOn ? <TextFormat value={templateEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.template.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{templateEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.template.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {templateEntity.updatedOn ? <TextFormat value={templateEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="marketingCampaignManagerApp.template.templateParam">Template Param</Translate>
          </dt>
          <dd>
            {templateEntity.templateParams
              ? templateEntity.templateParams.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {templateEntity.templateParams && i === templateEntity.templateParams.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/template" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template/${templateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateDetail;
