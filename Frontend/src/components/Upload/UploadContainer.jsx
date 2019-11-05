import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import Loader from '../globalStyles/Loader';
import {createBrowserHistory} from 'history';
import Upload from './components/Upload';
import BasicInput from '../BasicInput';
import './UploadContainer.css';

const history = createBrowserHistory();
function UploadContainer() {
  useEffect(() => {
  const unblock = history.block('Are you sure you want to navigate away?');
    return () => {
      unblock();
    };
  });

  const [file, setFile] = useState();
  const [filename, setFilename] = useState('');
  const [email, setEmail] = useState('');
  const [customerFirstName, setCustomerFirstName] = useState('');
  const [customerLastName, setCustomerLastName] = useState('');
  const [color, setColor] = useState('');
  const [comments, setComments] = useState('');

  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState();

  function onFailure(error) {
    setLoading(false);
    const validationErrors = RequestService.validationErrorGetter(error);
    setErrors(validationErrors);
  }

  function onSuccess(response) {
    setLoading(false);
    if (response.data.message) {
      setSuccess(response.data.message);
      setFile();
      setFilename('');
      setEmail('');
      setCustomerFirstName('');
      setCustomerLastName('');
      setColor('');
      setComments('')
    }
  }

  return (
    <div className={'uploadContainer'}>
      {loading ? <div className={'loader-container'}>Uploading File...<Loader/></div> : false}
      <form
        onSubmit={e => {
          e.preventDefault();
          setLoading(true);
          setErrors({});
          setSuccess();
          const formData = new FormData();
          formData.append('file', file);
          formData.append('email', email);
          formData.append('customerFirstName', customerFirstName);
          formData.append('customerLastName', customerLastName);
          formData.append('color', color);
          formData.append('comments', comments);
          RequestService.newPrintJob(formData, onSuccess, onFailure);
        }}
      >
        <div className={"success"}>{success}</div>
        <Upload
          className={'upload'}
          filename={filename}
          setFilename={setFilename}
          callback={files => setFile(files[0])}
        ></Upload>
        <p>{errors.file ? errors.file : null}</p>
        <BasicInput
          className={'upload'}
          value={customerFirstName}
          placeHolder={'First Name'}
          changeHandler={setCustomerFirstName}
          error={errors.customerFirstName}
        />
        <BasicInput
          className={'upload'}
          value={customerLastName}
          placeHolder={'Last Name'}
          changeHandler={setCustomerLastName}
          error={errors.customerLastName}
        />
        <BasicInput
          className={'upload'}
          value={email}
          placeHolder={'Email'}
          changeHandler={setEmail}
          error={errors.email}
        />
        <BasicInput
          className={'upload'}
          value={color}
          placeHolder={'Color'}
          changeHandler={setColor}
          error={errors.color}
        />
        <BasicInput
          className={'upload'}
          value={comments}
          placeHolder={'Comments'}
          changeHandler={setComments}
          error={errors.comments}
        />
        <div>
          <div className={"error"}>{errors.form ? errors.form : null}</div>
        </div>
        <button className={'shapedButton'} type="submit">
          SUMBIT
        </button>
      </form>
    </div>
  );
}

export default UploadContainer;
