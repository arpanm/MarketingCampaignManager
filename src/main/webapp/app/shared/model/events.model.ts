import { ICampaign } from 'app/shared/model/campaign.model';
import { EventType } from 'app/shared/model/enumerations/event-type.model';

export interface IEvents {
  id?: number;
  count?: number | null;
  eventType?: keyof typeof EventType | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<IEvents> = {
  isActive: false,
};
