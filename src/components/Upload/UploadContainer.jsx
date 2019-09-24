import React, { useState } from 'react'
import RequestService from '../../util/RequestService';
import Upload from './components/Upload'
import BasicInput from '../BasicInput';
import './UploadContainer.css';

function UploadContainer() {
  const [file, setFile] = useState();
  const [email, setEmail] = useState('');
  const [customerFirstName, setCustomerFirstName] = useState('');
  const [customerLastName, setCustomerLastName] = useState('');
  const [color, setColor] = useState('');
  const [comments, setComments] = useState('');

  const [errors, setErrors] = useState({});

  function onFailure(error) {
    const validationErrors = RequestService.validationErrorGetter(error);
    setErrors(validationErrors);
  };


  return (
    <div className={"uploadContainer"}>
      <span>{errors.form ? errors.form : null}</span>
      <form onSubmit={e => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('file', file);
        formData.append('email', email);
        formData.append('customerFirstName', customerFirstName);
        formData.append('customerLastName', customerLastName);
        formData.append('color', color);
        formData.append('comments', comments);
        RequestService.newPrintJob(formData, (response) => response, onFailure);
      }}>
        <Upload className={"upload"} callback={(files => setFile(files[0]))}>
        </Upload>
        <p>{errors.file ? errors.file : null}</p>
        <BasicInput className={"upload"} placeHolder={"First Name"} changeHandler={setCustomerFirstName} error={errors.customerFirstName} />
        <BasicInput className={"upload"} placeHolder={"Last Name"} changeHandler={setCustomerLastName} error={errors.customerLastName} />
        <BasicInput className={"upload"} placeHolder={"Email"} changeHandler={setEmail} error={errors.email} />
        <BasicInput className={"upload"} placeHolder={"Color"} changeHandler={setColor} error={errors.color} />
        <BasicInput className={"upload"} placeHolder={"Comments"} changeHandler={setComments} error={errors.comments} />
        <button className={"shapedButton"} type='submit'>SUMBIT</button>
      </form>
    </div>
  );
}

export default UploadContainer;