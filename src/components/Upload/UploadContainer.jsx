import React, { useState } from 'react'
import Upload from './components/Upload'
import RequestService from '../../util/RequestService';
import './UploadContainer.css';

function UploadContainer() {
  const [file, setFile] = useState();
  const [email, setEmail] = useState();
  const [customerFirstName, setCustomerFirstName] = useState();
  const [customerLastName, setCustomerLastName] = useState();
  const [color, setColor] = useState();
  const [comments, setComments] = useState();

  return (
    <div className={"uploadContainer"}>
      <form onSubmit={e => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('file', file);
        formData.append('email', email);
        formData.append('customerFirstName', customerFirstName);
        formData.append('customerLastName', customerLastName);
        formData.append('color', color);
        formData.append('comments', comments);
        RequestService.newPrintJob(formData, (response) => console.log(response), (err) => console.log(err));
      }}>
        <Upload className={"upload"} callback={(files => setFile(files[0]))}>
        </Upload>
        <input
          onChange={e => setCustomerFirstName(e.target.value)}
          type='text'
          id='firstName'
          placeholder='First Name' />
        <input
          onChange={e => setCustomerLastName(e.target.value)}
          type='text'
          id='lastName'
          placeholder='Last Name' />
        <input
          onChange={e => setEmail(e.target.value)}
          type='text'
          id='email'
          placeholder='Email' />
        <input
          onChange={e => setColor(e.target.value)}
          type='text'
          id='color'
          placeholder='Color' />
        <input
          onChange={e => setComments(e.target.value)}
          id='comments'
          placeholder='Comments' />
        <button className={"shapedButton"} type='submit'>SUMBIT</button>
      </form>
    </div>
  );
}

export default UploadContainer;