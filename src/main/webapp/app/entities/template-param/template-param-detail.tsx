import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template-param.reducer';

export const TemplateParamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateParamEntity = useAppSelector(state => state.templateParam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateParamDetailsHeading">
          <Translate contentKey="marketingCampaignManagerApp.templateParam.detail.title">TemplateParam</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.id}</dd>
          <dt>
            <span id="tag">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.tag}</dd>
          <dt>
            <span id="paramType">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.paramType">Param Type</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.paramType}</dd>
          <dt>
            <span id="replacedByAtrribute">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.replacedByAtrribute">Replaced By Atrribute</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.replacedByAtrribute}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.createdBy}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {templateParamEntity.createdOn ? (
              <TextFormat value={templateParamEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{templateParamEntity.updatedBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="marketingCampaignManagerApp.templateParam.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {templateParamEntity.updatedOn ? (
              <TextFormat value={templateParamEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/template-param" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template-param/${templateParamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateParamDetail;
