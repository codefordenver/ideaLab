import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import dummyData from '../dummyData';
import PrintCardContainer from './components/PrintCardContainer';
import './QueueContainer.css';
import Queue from './components/Queue';

const QueueContainer = () => {
  const [data, setData] = useState([]);
  const [stale, setStale] = useState(false);
  const [filteredData, setFilteredData] = useState(data);
  const [stringedValues, setStringedValues] = useState([]);
  const [statusView, setStatusView] = useState('QUEUEING');

  useEffect(() => {
    RequestService.getPrintJobs(
      response => {
        const data = response.data.data;
        setData(response.data.data);
      },
      error => console.log(error),
    );
  }, [stale]);

  useEffect(() => {
    const filteredKeys = [
      'name',
      'email',
      'color',
      'status',
      'fileName',
      'comments',
    ];
    const searchValues = data.map((printJob, index) => {
      let valueString = '';
      for (var key in printJob) {
        if (filteredKeys.indexOf(key) !== -1) {
          valueString = valueString + ' ' + printJob[key];
        }
      }
      return (printJob[index] = valueString.toLowerCase());
    });
    const queuedCards = data.filter(card => {
      var sameStatus = card.status === statusView;
      var doneAndFailed =
        statusView === 'DONE' &&
        (card.status === 'DONE' || card.status === 'FAILED');
      return sameStatus || doneAndFailed;
    });
    setFilteredData(queuedCards);
    setStringedValues(searchValues);
  }, [data, statusView]);

  const filterByTerm = searchTerm => {
    const filteredSearch = data.filter((printJob, i) => {
      return stringedValues[i].indexOf(searchTerm.toLowerCase()) !== -1;
    });
    setFilteredData(filteredSearch);
  };

  const setStatus = view => {
    setStatusView(view);
  };

  return (
    <Queue
      statusView={statusView}
      setStatus={setStatus}
      filterByTerm={filterByTerm}
      filteredData={filteredData}
    />
  );
};

export default QueueContainer;
