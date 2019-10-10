import React from 'react';
import MenuBar from '../../globalStyles/MenuBar';
import { MenuTabs } from '../../globalStyles/MenuTabs';
import Loader from '../../globalStyles/Loader';
import SearchBar from './SearchBar';
import PrintCardContainer from '../components/PrintCardContainer';

const Queue = props => {
  const renderPrintCards = props.filteredData.map((el, i) => (
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
        {props.loading ? <div className={'loader-container'}>Loading Fresh Data...<Loader/></div> : false}

        <SearchBar filterByTerm={props.filterByTerm} />
      </div>
      <ul className="queueBanner">
        <li className="col10"></li>
        <li className="col20">File Name</li>
        <li className="col20">Color</li>
        <li className="col20">Submitted</li>
        <li className="col20">Status</li>
      </ul>
      {renderPrintCards.length > 0
        ? renderPrintCards
        : `No items are currently ${props.statusView.toLowerCase()}`}
    </div>
  );
};

export default Queue;
