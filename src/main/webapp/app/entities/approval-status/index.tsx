import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ApprovalStatus from './approval-status';
import ApprovalStatusDetail from './approval-status-detail';
import ApprovalStatusUpdate from './approval-status-update';
import ApprovalStatusDeleteDialog from './approval-status-delete-dialog';

const ApprovalStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ApprovalStatus />} />
    <Route path="new" element={<ApprovalStatusUpdate />} />
    <Route path=":id">
      <Route index element={<ApprovalStatusDetail />} />
      <Route path="edit" element={<ApprovalStatusUpdate />} />
      <Route path="delete" element={<ApprovalStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApprovalStatusRoutes;
