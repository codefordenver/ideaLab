import React, { useState, useEffect } from 'react';
import { FiSave } from 'react-icons/fi';
import { useToasts } from 'react-toast-notifications';
import RequestService from '../../../util/RequestService';
import './EmailMessage.css';

/**
 * This holds the text box + save button + title that allows the user to change the message
 * that is sent via email. It requires the status.
 * @param {status} props
 */
const EmailMessage = props => {
  const [message, setMessage] = useState('');
  const [updatedMessage, setUpdatedMessage] = useState('');
  const [errors, setErrors] = useState({});

  const { addToast } = useToasts();

  useEffect(() => {
    fetchData();
  }, []);

  const getEmailMessageSuccess = data => {
    setMessage(data.data.data.emailMessage);
    setUpdatedMessage(data.data.data.emailMessage);
  };

  const failure = error => {
    const newErrorState = RequestService.validationErrorGetter(error);
    setErrors(newErrorState);
  };

  const changeEmailMessageSuccess = success => {
    addToast('Successfully changed the email message.', {
      appearance: 'success',
      autoDismiss: true,
    });
  };

  const changeEmailMessageFailure = error => {
    failure(error);
    addToast('Fail', { appearance: 'warning', autoDismiss: true });
  };

  const onSave = () => {
    if (message !== updatedMessage) {
      const body = {
        status: props.status,
        emailMessage: updatedMessage,
      };
      RequestService.changeEmailMessage(
        body,
        changeEmailMessageSuccess,
        changeEmailMessageFailure,
      );
    }
  };

  const fetchData = () => {
    RequestService.getEmailMessage(
      props.status,
      getEmailMessageSuccess,
      failure,
    );
  };

  const rendorError = () => {
    if (errors) {
      return <p>{errors}</p>;
    } else {
      return null;
    }
  };

  return (
    <div>
      <div className="emailStatusTitle">Message sent when status is changed to: {props.status}</div>
      <div className="emailMessageIndividualContainer">
        <textarea
          className="messageText"
          name="message"
          onChange={e => setUpdatedMessage(e.target.value)}
          value={updatedMessage}
        />
        <div className="emailMessageSaveIcon" onClick={onSave}>
          <FiSave />
        </div>
        {rendorError}
      </div>
    </div>
  );
};

export default EmailMessage;
