import { IEvents } from 'app/shared/model/events.model';
import { ISegment } from 'app/shared/model/segment.model';
import { ITemplate } from 'app/shared/model/template.model';
import { ChannelType } from 'app/shared/model/enumerations/channel-type.model';
import { ScheduleType } from 'app/shared/model/enumerations/schedule-type.model';

export interface ICampaign {
  id?: number;
  name?: string | null;
  channel?: keyof typeof ChannelType | null;
  schedule?: keyof typeof ScheduleType | null;
  startDate?: string | null;
  endDate?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  events?: IEvents[] | null;
  segment?: ISegment | null;
  template?: ITemplate | null;
}

export const defaultValue: Readonly<ICampaign> = {
  isActive: false,
};
