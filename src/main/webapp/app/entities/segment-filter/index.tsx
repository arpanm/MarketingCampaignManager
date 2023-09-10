import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SegmentFilter from './segment-filter';
import SegmentFilterDetail from './segment-filter-detail';
import SegmentFilterUpdate from './segment-filter-update';
import SegmentFilterDeleteDialog from './segment-filter-delete-dialog';

const SegmentFilterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SegmentFilter />} />
    <Route path="new" element={<SegmentFilterUpdate />} />
    <Route path=":id">
      <Route index element={<SegmentFilterDetail />} />
      <Route path="edit" element={<SegmentFilterUpdate />} />
      <Route path="delete" element={<SegmentFilterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SegmentFilterRoutes;
