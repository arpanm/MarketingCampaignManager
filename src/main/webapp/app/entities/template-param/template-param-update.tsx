import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITemplate } from 'app/shared/model/template.model';
import { getEntities as getTemplates } from 'app/entities/template/template.reducer';
import { ITemplateParam } from 'app/shared/model/template-param.model';
import { ParamType } from 'app/shared/model/enumerations/param-type.model';
import { getEntity, updateEntity, createEntity, reset } from './template-param.reducer';

export const TemplateParamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templates = useAppSelector(state => state.template.entities);
  const templateParamEntity = useAppSelector(state => state.templateParam.entity);
  const loading = useAppSelector(state => state.templateParam.loading);
  const updating = useAppSelector(state => state.templateParam.updating);
  const updateSuccess = useAppSelector(state => state.templateParam.updateSuccess);
  const paramTypeValues = Object.keys(ParamType);

  const handleClose = () => {
    navigate('/template-param');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getTemplates({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...templateParamEntity,
      ...values,
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
          paramType: 'Attribute',
          ...templateParamEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marketingCampaignManagerApp.templateParam.home.createOrEditLabel" data-cy="TemplateParamCreateUpdateHeading">
            <Translate contentKey="marketingCampaignManagerApp.templateParam.home.createOrEditLabel">
              Create or edit a TemplateParam
            </Translate>
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
                  id="template-param-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.tag')}
                id="template-param-tag"
                name="tag"
                data-cy="tag"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.paramType')}
                id="template-param-paramType"
                name="paramType"
                data-cy="paramType"
                type="select"
              >
                {paramTypeValues.map(paramType => (
                  <option value={paramType} key={paramType}>
                    {translate('marketingCampaignManagerApp.ParamType.' + paramType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.replacedByAtrribute')}
                id="template-param-replacedByAtrribute"
                name="replacedByAtrribute"
                data-cy="replacedByAtrribute"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.isActive')}
                id="template-param-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.createdBy')}
                id="template-param-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.createdOn')}
                id="template-param-createdOn"
                name="createdOn"
                data-cy="createdOn"
                type="date"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.updatedBy')}
                id="template-param-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('marketingCampaignManagerApp.templateParam.updatedOn')}
                id="template-param-updatedOn"
                name="updatedOn"
                data-cy="updatedOn"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/template-param" replace color="info">
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

export default TemplateParamUpdate;
