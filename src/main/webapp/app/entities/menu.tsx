import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/campaign">
        <Translate contentKey="global.menu.entities.campaign" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/segment">
        <Translate contentKey="global.menu.entities.segment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/template">
        <Translate contentKey="global.menu.entities.template" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/template-param">
        <Translate contentKey="global.menu.entities.templateParam" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/events">
        <Translate contentKey="global.menu.entities.events" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/filter-metadata">
        <Translate contentKey="global.menu.entities.filterMetadata" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/filter-source-mapping">
        <Translate contentKey="global.menu.entities.filterSourceMapping" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/filter-possible-value">
        <Translate contentKey="global.menu.entities.filterPossibleValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/segment-filter">
        <Translate contentKey="global.menu.entities.segmentFilter" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/approval-authority">
        <Translate contentKey="global.menu.entities.approvalAuthority" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/approval-status">
        <Translate contentKey="global.menu.entities.approvalStatus" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
