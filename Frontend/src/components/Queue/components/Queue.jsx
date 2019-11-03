import React from 'react';
import MenuBar from '../../globalStyles/MenuBar';
import { MenuTabs } from '../../globalStyles/MenuTabs';
import Loader from '../../globalStyles/Loader';
import SearchBar from './SearchBar';
import PrintCardContainer from '../components/PrintCardContainer';

const Queue = props => {
  const renderPrintCards = props.data.map((el, i) => (
    <PrintCardContainer data={el} key={i} />
  ));
  return (
    <div>
      <div className="queueFilterInfo">
        <MenuBar
          selectedTab={props.statusView}
          tabOptions={MenuTabs.QueueTabs}
          setView={props.setStatus}
        />
        {props.loading ? (
          <div className={'loader-container'}>
            Loading Fresh Data...
            <Loader />
          </div>
        ) : (
          false
        )}

        <SearchBar filterByTerm={props.filterByTerm} />
      </div>
      <table id="queueTable">
        <thead>
          <tr>
            <th className="fileNameHeader">File Name</th>
            <th className="colorHeader" colSpan="4">
              Color
            </th>
            <th className="submittedHeader">Submitted</th>
            <th className="statusHeader">Status</th>
            <th></th>
            {/* <--- dropdown arrow column */}
          </tr>
        </thead>
        <tbody>
          {renderPrintCards.length > 0 ? (
            renderPrintCards
          ) : (
            <tr>
              <td>
                `No items are currently ${props.statusView.toLowerCase()}`
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Queue;
