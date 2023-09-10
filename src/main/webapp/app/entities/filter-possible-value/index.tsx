import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FilterPossibleValue from './filter-possible-value';
import FilterPossibleValueDetail from './filter-possible-value-detail';
import FilterPossibleValueUpdate from './filter-possible-value-update';
import FilterPossibleValueDeleteDialog from './filter-possible-value-delete-dialog';

const FilterPossibleValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FilterPossibleValue />} />
    <Route path="new" element={<FilterPossibleValueUpdate />} />
    <Route path=":id">
      <Route index element={<FilterPossibleValueDetail />} />
      <Route path="edit" element={<FilterPossibleValueUpdate />} />
      <Route path="delete" element={<FilterPossibleValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FilterPossibleValueRoutes;
