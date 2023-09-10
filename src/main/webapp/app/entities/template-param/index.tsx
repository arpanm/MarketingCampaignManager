import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TemplateParam from './template-param';
import TemplateParamDetail from './template-param-detail';
import TemplateParamUpdate from './template-param-update';
import TemplateParamDeleteDialog from './template-param-delete-dialog';

const TemplateParamRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TemplateParam />} />
    <Route path="new" element={<TemplateParamUpdate />} />
    <Route path=":id">
      <Route index element={<TemplateParamDetail />} />
      <Route path="edit" element={<TemplateParamUpdate />} />
      <Route path="delete" element={<TemplateParamDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TemplateParamRoutes;
