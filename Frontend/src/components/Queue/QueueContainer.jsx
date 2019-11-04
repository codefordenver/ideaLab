import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import './QueueContainer.css';
import Queue from './components/Queue';

const QueueContainer = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filteredData, setFilteredData] = useState(data);
  const [stringedValues, setStringedValues] = useState([]);
  const [statusView, setStatusView] = useState();

  const setSearchValues = () => {
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
    const searchValues = data.map((printJob, index) => {
      let valueString = '';
      for (var key in printJob) {
        // formattedData filter here
        valueString = valueString + ' ' + printJob[key];
      }
      return (printJob[index] = valueString.toLowerCase());
    });
    setStringedValues(searchValues);
  };

  useEffect(() => {
    setLoading(true);
    RequestService.getPrintJobs(
      response => {
        const data = response.data.data;
        setData(data);
        setLoading(false);
        setStatus('PENDING_REVIEW');
        setSearchValues();
      },
      error => console.error(error),
    );
  }, []);

  const saveCard = updatedCard => {
    RequestService.saveCard(updatedCard);
    // update stringed values for id
  };

  useEffect(() => {
    console.log('status view updated', statusView);
    const activeCards = data.filter(card => {
      if (card.status === 'FAILED' && statusView === 'DONE') {
        return card;
      }
      if (card.status === statusView) {
        return card;
      }
    });
    setFilteredData(activeCards);
  }, [statusView]);

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
      data={filteredData}
      saveCard={saveCard}
    />
  );
};

export default QueueContainer;
