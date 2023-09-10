import { ITemplate } from 'app/shared/model/template.model';
import { ParamType } from 'app/shared/model/enumerations/param-type.model';

export interface ITemplateParam {
  id?: number;
  tag?: string | null;
  paramType?: keyof typeof ParamType | null;
  replacedByAtrribute?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  templates?: ITemplate[] | null;
}

export const defaultValue: Readonly<ITemplateParam> = {
  isActive: false,
};
