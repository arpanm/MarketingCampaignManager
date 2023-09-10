import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ApprovalAuthority from './approval-authority';
import ApprovalAuthorityDetail from './approval-authority-detail';
import ApprovalAuthorityUpdate from './approval-authority-update';
import ApprovalAuthorityDeleteDialog from './approval-authority-delete-dialog';

const ApprovalAuthorityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ApprovalAuthority />} />
    <Route path="new" element={<ApprovalAuthorityUpdate />} />
    <Route path=":id">
      <Route index element={<ApprovalAuthorityDetail />} />
      <Route path="edit" element={<ApprovalAuthorityUpdate />} />
      <Route path="delete" element={<ApprovalAuthorityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ApprovalAuthorityRoutes;
