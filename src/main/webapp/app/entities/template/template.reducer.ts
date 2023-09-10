import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ITemplate, defaultValue } from 'app/shared/model/template.model';

const initialState: EntityState<ITemplate> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/templates';

// Actions

export const getEntities = createAsyncThunk('template/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `page=${page}&size=${size}&sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<ITemplate[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'template/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ITemplate>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'template/create_entity',
  async (entity: ITemplate, thunkAPI) => {
    return axios.post<ITemplate>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'template/update_entity',
  async (entity: ITemplate, thunkAPI) => {
    return axios.put<ITemplate>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'template/partial_update_entity',
  async (entity: ITemplate, thunkAPI) => {
    return axios.patch<ITemplate>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'template/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<ITemplate>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const TemplateSlice = createEntitySlice({
  name: 'template',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = TemplateSlice.actions;

// Reducer
export default TemplateSlice.reducer;
