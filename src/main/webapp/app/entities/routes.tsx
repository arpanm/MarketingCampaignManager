import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Campaign from './campaign';
import Segment from './segment';
import Template from './template';
import TemplateParam from './template-param';
import Events from './events';
import FilterMetadata from './filter-metadata';
import FilterSourceMapping from './filter-source-mapping';
import FilterPossibleValue from './filter-possible-value';
import SegmentFilter from './segment-filter';
import ApprovalAuthority from './approval-authority';
import ApprovalStatus from './approval-status';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="campaign/*" element={<Campaign />} />
        <Route path="segment/*" element={<Segment />} />
        <Route path="template/*" element={<Template />} />
        <Route path="template-param/*" element={<TemplateParam />} />
        <Route path="events/*" element={<Events />} />
        <Route path="filter-metadata/*" element={<FilterMetadata />} />
        <Route path="filter-source-mapping/*" element={<FilterSourceMapping />} />
        <Route path="filter-possible-value/*" element={<FilterPossibleValue />} />
        <Route path="segment-filter/*" element={<SegmentFilter />} />
        <Route path="approval-authority/*" element={<ApprovalAuthority />} />
        <Route path="approval-status/*" element={<ApprovalStatus />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
