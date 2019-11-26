import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import './QueueContainer.css';
import Queue from './components/Queue';

const QueueContainer = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [filteredData, setFilteredData] = useState(data);
  const [stringedValues, setStringedValues] = useState({});
  const [statusView, setStatusView] = useState('');
  const [colors, setColors] = useState();

  const setSearchValues = printjob => {
    const formattedData = {
      id: printjob.id,
      submitted: printjob.createdAt,
      comments: printjob.comments,
      status: printjob.status,
      filePath: printjob.filePath,
      fileSharableLink: printjob.fileSharableLink,
    };

    let valueString = '';
    for (var key in printjob) {
      if (key === 'colorTypeId') {
        valueString = valueString + ' ' + printjob.colorTypeId.color;
      }
      if (key === 'customerInfo') {
        valueString = valueString + ' ' + printjob.customerInfo.firstName;
      }
      if (formattedData[key]) {
        valueString = valueString + ' ' + printjob[key];
      }
    }
    setStringedValues({ ...stringedValues, printjob: { id: valueString } });
  };

  useEffect(() => {
    setLoading(true);
    RequestService.getActiveColors(
      response => {
        const data = response.data.data;
        setLoading(false);
        var colorList = [];
        // eslint-disable-next-line array-callback-return
        data.map(color => {
          colorList.push(color.color);
        });
        setColors(colorList);
      },
      error => console.error(error),
    );
  }, []);

  useEffect(() => {
    setLoading(true);
    RequestService.getPrintJobs(
      response => {
        const data = response.data.data;
        setData(data);
        setStatus('PENDING_REVIEW');
        setLoading(false);
        const formattedData = data.map(printjob => {
          return {
            color: printjob.colorType.color,
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

  const saveCard = updatedCard => {
    RequestService.saveCard(updatedCard);
    setSearchValues(updatedCard);
  };

  useEffect(() => {
    const activeCards = data.filter(card => {
      if (card.status === 'FAILED' && statusView === 'DONE') {
        setSearchValues(card);
        return card;
      }
      if (card.status === statusView) {
        setSearchValues(card);
        return card;
      }
    });
    setFilteredData(activeCards);
  }, [statusView]);

  const filterByTerm = searchTerm => {
    setLoading(true);
    const filteredSearch = filteredData.filter((printJob, i) => {
      if (stringedValues.length > 0) {
        let stringSearch = stringedValues[i];
        return stringSearch.indexOf(searchTerm.toLowerCase()) !== -1;
      }
    });
    setFilteredData(filteredSearch);
    setLoading(false);
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
      colors={colors}
      saveCard={saveCard}
      data={data}
    />
  );
};

export default QueueContainer;
