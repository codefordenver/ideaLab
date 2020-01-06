import React, { useEffect, useState } from 'react';
import AuthContext from '../../AuthContext';
import { processActiveColors } from '../../util/ColorUtils';
import RequestService from '../../util/RequestService';
import Queue from './components/Queue';
import Backdrop from '../globalStyles/Backdrop/Backdrop';
import SendEmailModal from './components/SendEmailModal';
import { ToastProvider, useToasts } from 'react-toast-notifications';
import './QueueContainer.css';

const QueueContainer = () => {
  const [loading, setLoading] = useState(false);
  const [filteredData, setFilteredData] = useState([]);
  const [statusView, setStatusView] = useState('PENDING_REVIEW');
  const [colors, setColors] = useState();
  const [showSendEmailModal, setShowSendEmailModal] = useState(false);
  const [updatedCard, setUpdatedCard] = useState('');

  const { addToast } = useToasts();

  useEffect(() => {
    setLoading(true);
    const colorList = processActiveColors();
    setColors(colorList);
    setLoading(false);
  }, []);

  useEffect(() => {
    //Load initial data and set the loading only on first load
    setLoading(true);
    fetchQueueData();
    setLoading(false);

    //Sets an interval to re-fetch data on an interval.
    const interval = setInterval(() => {
      fetchQueueData();

      //Time is in milliseconds (1000*60*5 = 5 mins)
    }, 1000 * 60 * 5);

    //Clean up function should remove the interval on the component unmount
    return () => clearInterval(interval);
  }, [statusView]);

  const returnCardStatus = cardStatus => {
    const failedStatuses = ['REJECTED', 'COMPLETED', 'ARCHIVED'];
    const waitingStatuses = ['PENDING_CUSTOMER_RESPONSE', 'PENDING_REVIEW'];
    if (cardStatus === 'FAILED') {
      return 'FAILED';
    } else if (failedStatuses.indexOf(cardStatus) !== -1) {
      return 'DONE';
    } else if (waitingStatuses.indexOf(cardStatus) !== -1) {
      return 'PENDING_REVIEW';
    } else {
      return 'PRINTING';
    }
  };

  const onSaveCardSuccess = response => {
    const cardStatus = response.data.data[0].status;

    if (cardStatus !== statusView) {
      setStatusView(returnCardStatus(cardStatus));
    }

    addToast('The card updated successfully.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const onFailure = error => {
    console.log('ERROR SAVING CARD:', error);
    addToast('The card did not update successfully.', {
      appearance: 'warning',
      autoDismiss: true,
    });
  };

  const triggerShowSendEmailModal = () => {
    setShowSendEmailModal(!showSendEmailModal);
  };

  const updateCardData = updatedCard => {
    RequestService.saveCard(updatedCard, onSaveCardSuccess, onFailure);
  };

  const saveCard = updatedCard => {
    setUpdatedCard(updatedCard);
    if (updatedCard.status === 'FAILED' || updatedCard.status === 'COMPLETED') {
      setShowSendEmailModal(true);
    } else {
      updateCardData(updatedCard);
    }
  };

  const sendEmailModal = (
    <ToastProvider>
      <Backdrop showModal={showSendEmailModal}>
        <SendEmailModal
          updatedCard={updatedCard}
          setShowSendEmailModal={triggerShowSendEmailModal}
          showModal={showSendEmailModal}
          updateStatus={updateCardData}
        />
      </Backdrop>
    </ToastProvider>
  );

  const fetchQueueData = () => {
    RequestService.getPrintJobs(
      response => {
        const activeCards = response.data.data.filter(card => {
          if (
            statusView === 'DONE' &&
            returnCardStatus(card.status) === 'DONE'
          ) {
            return card;
          } else if (
            statusView === 'PENDING_REVIEW' &&
            returnCardStatus(card.status) === 'PENDING_REVIEW'
          ) {
            return card;
          } else if (
            statusView === 'PRINTING' &&
            returnCardStatus(card.status) === 'PRINTING'
          ) {
            return card;
          } else if (
            statusView === 'FAILED' &&
            returnCardStatus(card.status) === 'FAILED'
          ) {
            return card;
          }
        });
        setFilteredData(activeCards);
      },
      error => console.error(error),
    );
  };

  const setStatus = view => {
    setStatusView(view);
  };

  const searchBarSubmitHandler = event => {
    const input = event.target.input.value
      .toLowerCase()
      .split(' ')
      .join('');

    const activeCards = filteredData.filter(printJob => {
      const comments = printJob.comments.toLowerCase().split(' ');
      const commentsJoin = comments.join('');
      const filePath = printJob.filePath;
      const firstName = printJob.customerInfo.firstName;
      const lastName = printJob.customerInfo.lastName;
      const fullName = firstName + lastName;
      const nameFull = lastName + firstName;

      console.log(comments);

      if (input === commentsJoin) return printJob;
      if (input === firstName) return printJob;
      if (input === lastName) return printJob;
      if (input === fullName) return printJob;
      if (input === nameFull) return printJob;
      if (input === filePath) return printJob;
      if (comments.includes(input)) return printJob;
    });

    setFilteredData(activeCards);
    event.target.input.value = '';
    event.preventDefault();
  };

  return (
    <div>
      <AuthContext.Consumer>
        {context => {
          return (
            <Queue
              loading={loading}
              statusView={statusView}
              searchBarSubmit={searchBarSubmitHandler}
              setStatus={setStatus}
              fetchQueueData={fetchQueueData}
              filteredData={filteredData}
              colors={colors}
              saveCard={saveCard}
              employeeId={context.employeeId}
            />
          );
        }}
      </AuthContext.Consumer>
      {sendEmailModal}
    </div>
  );
};

export default QueueContainer;
