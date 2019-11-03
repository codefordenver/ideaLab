import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import './QueueContainer.css';
import Queue from './components/Queue';

const QueueContainer = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filteredData, setFilteredData] = useState(data);
  const [stringedValues, setStringedValues] = useState([]);
  const [statusView, setStatusView] = useState('PENDING_REVIEW');

  useEffect(() => {
    setLoading(true);
    RequestService.getPrintJobs(
      response => {
        const data = response.data.data;
        setLoading(false);
        const formattedData = data.map(printjob => {
          return {
            id: printjob.id,
            color: printjob.colorTypeId.color,
            customerFirstName: printjob.customerInfo.firstName,
            submitted: printjob.createdAt,
            comments: printjob.comments,
            status: printjob.status,
            filePath: printjob.filePath,
            fileSharableLink: printjob.fileSharableLink,
          };
        });
        setData(formattedData);
      },
      error => console.error(error),
    );
  }, []);

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
      loading={loading}
      statusView={statusView}
      setStatus={setStatus}
      filterByTerm={filterByTerm}
      filteredData={filteredData}
      data={data}
    />
  );
};

export default QueueContainer;
