import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FilterSourceMapping from './filter-source-mapping';
import FilterSourceMappingDetail from './filter-source-mapping-detail';
import FilterSourceMappingUpdate from './filter-source-mapping-update';
import FilterSourceMappingDeleteDialog from './filter-source-mapping-delete-dialog';

const FilterSourceMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FilterSourceMapping />} />
    <Route path="new" element={<FilterSourceMappingUpdate />} />
    <Route path=":id">
      <Route index element={<FilterSourceMappingDetail />} />
      <Route path="edit" element={<FilterSourceMappingUpdate />} />
      <Route path="delete" element={<FilterSourceMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FilterSourceMappingRoutes;
