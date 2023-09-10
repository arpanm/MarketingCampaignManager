import { ITemplateParam } from 'app/shared/model/template-param.model';

export interface ITemplate {
  id?: number;
  name?: string | null;
  title?: string | null;
  subTitle?: string | null;
  body?: string | null;
  imageUrl?: string | null;
  isActive?: boolean | null;
  createdBy?: number | null;
  createdOn?: string | null;
  updatedBy?: number | null;
  updatedOn?: string | null;
  templateParams?: ITemplateParam[] | null;
}

export const defaultValue: Readonly<ITemplate> = {
  isActive: false,
};
