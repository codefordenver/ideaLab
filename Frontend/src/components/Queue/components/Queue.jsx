import React from 'react';
import MenuBar from '../../globalStyles/MenuBar';
import { MenuTabs } from '../../globalStyles/MenuTabs';
import Loader from '../../globalStyles/Loader';
import PrintCardContainer from '../components/PrintCardContainer';
import SearchBar from './SearchBar';
import './Queue.css';

const Queue = props => {
  const renderPrintCards = props.filteredData.map(card => (
    <PrintCardContainer
      data={card}
      key={card.id}
      employeeId={props.employeeId}
      colors={props.colors}
      saveCard={props.saveCard}
    />
  ));
  return (
    <div className="queueContainer">
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
        <SearchBar
          fetchQueueData={props.fetchQueueData}
          submit={props.searchBarSubmit}
        />
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
        {renderPrintCards.length > 0 ? (
          renderPrintCards
        ) : (
          <tbody>
            <tr>
              <td>No cards fit this criteria</td>
            </tr>
          </tbody>
        )}
      </table>
    </div>
  );
};

export default Queue;
