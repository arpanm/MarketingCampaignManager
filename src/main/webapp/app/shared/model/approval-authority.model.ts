import { VerticalType } from 'app/shared/model/enumerations/vertical-type.model';

export interface IApprovalAuthority {
  id?: number;
  userId?: number | null;
  vertial?: keyof typeof VerticalType | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
}

export const defaultValue: Readonly<IApprovalAuthority> = {
  isActive: false,
};
