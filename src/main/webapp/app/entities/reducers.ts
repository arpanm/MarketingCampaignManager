import campaign from 'app/entities/campaign/campaign.reducer';
import segment from 'app/entities/segment/segment.reducer';
import template from 'app/entities/template/template.reducer';
import templateParam from 'app/entities/template-param/template-param.reducer';
import events from 'app/entities/events/events.reducer';
import filterMetadata from 'app/entities/filter-metadata/filter-metadata.reducer';
import filterSourceMapping from 'app/entities/filter-source-mapping/filter-source-mapping.reducer';
import filterPossibleValue from 'app/entities/filter-possible-value/filter-possible-value.reducer';
import segmentFilter from 'app/entities/segment-filter/segment-filter.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  campaign,
  segment,
  template,
  templateParam,
  events,
  filterMetadata,
  filterSourceMapping,
  filterPossibleValue,
  segmentFilter,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
