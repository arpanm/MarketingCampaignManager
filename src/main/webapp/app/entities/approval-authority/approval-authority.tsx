import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './approval-authority.reducer';

export const ApprovalAuthority = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(location, ITEMS_PER_PAGE, 'id'), location.search),
  );
  const [sorting, setSorting] = useState(false);

  const approvalAuthorityList = useAppSelector(state => state.approvalAuthority.entities);
  const loading = useAppSelector(state => state.approvalAuthority.loading);
  const links = useAppSelector(state => state.approvalAuthority.links);
  const updateSuccess = useAppSelector(state => state.approvalAuthority.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="approval-authority-heading" data-cy="ApprovalAuthorityHeading">
        <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.home.title">Approval Authorities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/approval-authority/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.home.createLabel">Create new Approval Authority</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={approvalAuthorityList ? approvalAuthorityList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {approvalAuthorityList && approvalAuthorityList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('userId')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.userId">User Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('userId')} />
                  </th>
                  <th className="hand" onClick={sort('vertial')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.vertial">Vertial</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vertial')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.createdOn">Created On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdOn')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.updatedOn">Updated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedOn')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {approvalAuthorityList.map((approvalAuthority, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/approval-authority/${approvalAuthority.id}`} color="link" size="sm">
                        {approvalAuthority.id}
                      </Button>
                    </td>
                    <td>{approvalAuthority.userId}</td>
                    <td>
                      <Translate contentKey={`marketingCampaignManagerApp.VerticalType.${approvalAuthority.vertial}`} />
                    </td>
                    <td>{approvalAuthority.isActive ? 'true' : 'false'}</td>
                    <td>{approvalAuthority.createdBy}</td>
                    <td>
                      {approvalAuthority.createdOn ? (
                        <TextFormat type="date" value={approvalAuthority.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{approvalAuthority.updatedBy}</td>
                    <td>
                      {approvalAuthority.updatedOn ? (
                        <TextFormat type="date" value={approvalAuthority.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/approval-authority/${approvalAuthority.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/approval-authority/${approvalAuthority.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/approval-authority/${approvalAuthority.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="marketingCampaignManagerApp.approvalAuthority.home.notFound">
                  No Approval Authorities found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ApprovalAuthority;
