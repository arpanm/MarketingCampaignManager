import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FilterMetadata from './filter-metadata';
import FilterMetadataDetail from './filter-metadata-detail';
import FilterMetadataUpdate from './filter-metadata-update';
import FilterMetadataDeleteDialog from './filter-metadata-delete-dialog';

const FilterMetadataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FilterMetadata />} />
    <Route path="new" element={<FilterMetadataUpdate />} />
    <Route path=":id">
      <Route index element={<FilterMetadataDetail />} />
      <Route path="edit" element={<FilterMetadataUpdate />} />
      <Route path="delete" element={<FilterMetadataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FilterMetadataRoutes;
