import { IApprovalAuthority } from 'app/shared/model/approval-authority.model';
import { ICampaign } from 'app/shared/model/campaign.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';

export interface IApprovalStatus {
  id?: number;
  statusType?: keyof typeof StatusType | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  approvedBy?: IApprovalAuthority | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<IApprovalStatus> = {
  isActive: false,
};
