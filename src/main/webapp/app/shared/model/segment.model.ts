import { ISegmentFilter } from 'app/shared/model/segment-filter.model';

export interface ISegment {
  id?: number;
  name?: string | null;
  sourceTable?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  segmentFilters?: ISegmentFilter[] | null;
}

export const defaultValue: Readonly<ISegment> = {
  isActive: false,
};
