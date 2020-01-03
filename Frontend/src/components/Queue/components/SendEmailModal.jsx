import React from 'react';
import RequestService from '../../../util/RequestService';
import { useToasts } from 'react-toast-notifications';

const SendEmailModal = props => {
  const { setShowSendEmailModal, updateStatus, updatedCard } = props;
  const { addToast } = useToasts();

  const closeModal = e => {
    e.preventDefault();
    setShowSendEmailModal();
  };

  const onFailureEmail = error => {
    addToast('The email did not send.', {
      appearance: 'warning',
      autoDismiss: true,
    });
  };

  const onSuccessGettingMessage = data => {
    const body = {
      email: updatedCard.email,
      message: data.data.data.emailMessage,
    };
    RequestService.sendStatusChangeEmail(
      body,
      onSuccessSendingEmail,
      onFailureEmail,
    );
  };

  const onSuccessSendingEmail = data => {
    addToast('The email successfully sent.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const sendEmail = card => {
    RequestService.getEmailMessage(
      card.status,
      onSuccessGettingMessage,
      onFailureEmail,
    );
  };

  const sendEmailAndStatus = e => {
    e.preventDefault();
    sendEmail(updatedCard);
    updateStatus(updatedCard);
    closeModal(e);
  };

  const changeStatus = e => {
    e.preventDefault();
    updateStatus(updatedCard);
    closeModal(e);
  };

  console.log('pprops: ', props);

  return (
    <div
      className="form__container"
      style={{
        opacity: props.showModal ? '1' : '0',
        transform: props.showModal ? 'translateY(0)' : 'translateY(-100vh)',
      }}
    >
      <h4>Confirm Email and Status Change</h4>
      <div className="modalText">
        You are changing the status to {props.updatedCard.status}. Please
        confirm which of the following you want to do:
      </div>
      <button className="modalButton button" onClick={sendEmailAndStatus}>
        SEND EMAIL + CHANGE STATUS
      </button>
      <button className="modalButton button" onClick={changeStatus}>
        CHANGE STATUS ONLY
      </button>
      <button className="modalButton button" onClick={closeModal}>
        CANCEL
      </button>
    </div>
  );
};

export default SendEmailModal;
