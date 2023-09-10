import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Segment from './segment';
import SegmentDetail from './segment-detail';
import SegmentUpdate from './segment-update';
import SegmentDeleteDialog from './segment-delete-dialog';

const SegmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Segment />} />
    <Route path="new" element={<SegmentUpdate />} />
    <Route path=":id">
      <Route index element={<SegmentDetail />} />
      <Route path="edit" element={<SegmentUpdate />} />
      <Route path="delete" element={<SegmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SegmentRoutes;
