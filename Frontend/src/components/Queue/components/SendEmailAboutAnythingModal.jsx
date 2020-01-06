import React, { useState } from 'react';
import RequestService from '../../../util/RequestService';
import { useToasts } from 'react-toast-notifications';

import './SendEmailAboutAnythingModal.css';

const SendEmailAboutAnythingModal = props => {
  const { setShowSendEmailModal, email } = props;
  const [subject, setSubject] = useState('');
  const [message, setMessage] = useState('');
  const { addToast } = useToasts();

  const closeModal = e => {
    e.preventDefault();
    setSubject('');
    setMessage('');
    setShowSendEmailModal();
  };

  const onFailureEmail = error => {
    addToast('The email did not send.', {
      appearance: 'warning',
      autoDismiss: true,
    });
  };

  const onSuccessSendingEmail = data => {
    addToast('The email successfully sent.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const sendEmail = e => {
    e.preventDefault();
    const body = {
      email: email,
      message: message,
      subject: subject,
    };
    RequestService.sendEmailAboutAnything(
      body,
      onSuccessSendingEmail,
      onFailureEmail,
    );
    closeModal(e);
  };

  return (
    <div
      className="form__container"
      style={{
        opacity: props.showModal ? '1' : '0',
        transform: props.showModal ? 'translateY(0)' : 'translateY(-100vh)',
      }}
    >
      <h4>Send Email</h4>
      <div className="form__item">
        <label htmlFor="subject">Subject:</label>
        <input
          className="input"
          autoComplete="off"
          name="subjectBox"
          onChange={e => setSubject(e.target.value)}
          required
        />
      </div>
      <div className="form__item">
        <label htmlFor="subject">Message:</label>
        <textarea
          className="input"
          onChange={e => setMessage(e.target.value)}
          rows={props.rows}
          value={props.value}
          placeholder={props.placeholder}
        />
      </div>
      <div className="form__action">
        <button className="modalButton button" onClick={sendEmail}>
          SEND EMAIL
        </button>
        <button className="modalButton button" onClick={closeModal}>
          CANCEL
        </button>
      </div>
    </div>
  );
};

export default SendEmailAboutAnythingModal;
