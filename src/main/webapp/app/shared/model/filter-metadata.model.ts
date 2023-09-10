import { IFilterSourceMapping } from 'app/shared/model/filter-source-mapping.model';
import { IFilterPossibleValue } from 'app/shared/model/filter-possible-value.model';
import { ISegmentFilter } from 'app/shared/model/segment-filter.model';
import { FilterType } from 'app/shared/model/enumerations/filter-type.model';
import { FilterUiType } from 'app/shared/model/enumerations/filter-ui-type.model';

export interface IFilterMetadata {
  id?: number;
  name?: string | null;
  desc?: string | null;
  filterType?: keyof typeof FilterType | null;
  uiType?: keyof typeof FilterUiType | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  mapping?: IFilterSourceMapping | null;
  filterPossibleValues?: IFilterPossibleValue[] | null;
  segmentFilter?: ISegmentFilter | null;
}

export const defaultValue: Readonly<IFilterMetadata> = {
  isActive: false,
};
