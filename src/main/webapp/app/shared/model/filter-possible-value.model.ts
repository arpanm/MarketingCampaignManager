import { IFilterMetadata } from 'app/shared/model/filter-metadata.model';
import { ISegmentFilter } from 'app/shared/model/segment-filter.model';

export interface IFilterPossibleValue {
  id?: number;
  uiName?: string | null;
  attributeValue?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  filter?: IFilterMetadata | null;
  segmentFilter?: ISegmentFilter | null;
}

export const defaultValue: Readonly<IFilterPossibleValue> = {
  isActive: false,
};
