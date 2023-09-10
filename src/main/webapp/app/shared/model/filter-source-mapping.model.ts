import { IFilterMetadata } from 'app/shared/model/filter-metadata.model';

export interface IFilterSourceMapping {
  id?: number;
  sourceTable?: string | null;
  atrributeMapping?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  filterMetadata?: IFilterMetadata | null;
}

export const defaultValue: Readonly<IFilterSourceMapping> = {
  isActive: false,
};
