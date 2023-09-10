import { IFilterMetadata } from 'app/shared/model/filter-metadata.model';
import { IFilterPossibleValue } from 'app/shared/model/filter-possible-value.model';
import { ISegment } from 'app/shared/model/segment.model';

export interface ISegmentFilter {
  id?: number;
  name?: string | null;
  title?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  filter?: IFilterMetadata | null;
  values?: IFilterPossibleValue[] | null;
  segment?: ISegment | null;
}

export const defaultValue: Readonly<ISegmentFilter> = {
  isActive: false,
};
