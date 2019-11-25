import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import AuthContext from '../../AuthContext';
import './QueueContainer.css';
import Queue from './components/Queue';

const QueueContainer = () => {
  const [loading, setLoading] = useState(false);
  const [filteredData, setFilteredData] = useState([]);
  const [statusView, setStatusView] = useState('PENDING_REVIEW');
  const [colors, setColors] = useState();

  useEffect(() => {
    setLoading(true);
    RequestService.getActiveColors(
      response => {
        const data = response.data.data;
        setLoading(false);
        var colorList = [];
        data.map(color => {
          colorList.push(color.color);
        });
        setColors(colorList);
      },
      error => console.error(error),
    );
  }, []);

  // useEffect(() => {
  //   setLoading(true);
  //   RequestService.getPrintJobs(
  //     response => {
  //       const data = response.data.data;
  //       setData(data);
  //       setLoading(false);
  //     },
  //     error => console.error(error),
  //   );
  // }, []);

  const saveCard = updatedCard => {
    RequestService.saveCard(updatedCard);
  };

  useEffect(() => {
    setLoading(true);
    const failedStatuses = ['FAILED', 'REJECTED', 'COMPLETED', 'ARCHIVED'];
    const waitingStatuses = ['PENDING_CUSTOMER_RESPONSE', 'PENDING_REVIEW'];
    //TO DO: GET PRINT JOBS BASED ON STATUS
    RequestService.getPrintJobs(
      response => {
        const activeCards = response.data.data.filter(card => {
          if (
            failedStatuses.indexOf(card.status) !== -1 &&
            statusView === 'DONE'
          ) {
            return card;
          } else if (
            waitingStatuses.indexOf(card.status) !== -1 &&
            statusView === 'PENDING_REVIEW'
          ) {
            return card;
          } else if (statusView === 'PRINTING' && card.status === 'PRINTING') {
            return card;
          }
        });
        setFilteredData(activeCards);
        setLoading(false);
      },
      error => console.error(error),
    );
  }, [statusView]);

  const setStatus = view => {
    setStatusView(view);
  };

  return (
    <AuthContext.Consumer>
      {context => {
        return (
          <Queue
            loading={loading}
            statusView={statusView}
            setStatus={setStatus}
            filteredData={filteredData}
            colors={colors}
            saveCard={saveCard}
            employeeId={context.employeeId}
          />
        );
      }}
    </AuthContext.Consumer>
  );
};

export default QueueContainer;
