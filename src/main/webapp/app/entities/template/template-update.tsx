import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITemplateParam } from 'app/shared/model/template-param.model';
import { getEntities as getTemplateParams } from 'app/entities/template-param/template-param.reducer';
import { ITemplate } from 'app/shared/model/template.model';
import { getEntity, updateEntity, createEntity, reset } from './template.reducer';

export const TemplateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templateParams = useAppSelector(state => state.templateParam.entities);
  const templateEntity = useAppSelector(state => state.template.entity);
  const loading = useAppSelector(state => state.template.loading);
  const updating = useAppSelector(state => state.template.updating);
  const updateSuccess = useAppSelector(state => state.template.updateSuccess);

  const handleClose = () => {
    navigate('/template');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTemplateParams({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...templateEntity,
      ...values,
      templateParams: mapIdList(values.templateParams),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...templateEntity,
          templateParams: templateEntity?.templateParams?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.template.home.createOrEditLabel" data-cy="TemplateCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.template.home.createOrEditLabel">Create or edit a Template</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="template-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.name')}
                id="template-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.title')}
                id="template-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.subTitle')}
                id="template-subTitle"
                name="subTitle"
                data-cy="subTitle"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.body')}
                id="template-body"
                name="body"
                data-cy="body"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.imageUrl')}
                id="template-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.isActive')}
                id="template-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.createdBy')}
                id="template-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.createdOn')}
                id="template-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.updatedBy')}
                id="template-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.updatedOn')}
                id="template-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.template.templateParam')}
                id="template-templateParam"
                data-cy="templateParam"
                type="select"
                multiple
                name="templateParams"
              >
                <option value="" key="0" />
                {templateParams
                  ? templateParams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/template" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TemplateUpdate;
