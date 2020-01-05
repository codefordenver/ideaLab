import React, { useState, useEffect } from 'react';
import RequestService from '../../util/RequestService';
import { processActiveColors } from '../../util/ColorUtils';
import Loader from '../globalStyles/Loader';
import { createBrowserHistory } from 'history';
import Upload from './components/Upload';
import BasicInput from '../globalStyles/BasicInput';
import './UploadContainer.css';
import ColorPickerContainer from '../Queue/components/ColorPickerContainer';

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
      setComments('');
    }
  }

  /*
  This section provides all the logic for the color picker.
  */
  const [colors, setColors] = useState([]);

  const colorCircleStyle = {
    backgroundColor: `${color}`,
  };

  useEffect(() => {
    setLoading(true);
    const colorList = processActiveColors();
    setColors(colorList);
    setLoading(false);
  }, []);

  const handleColorChange = hue => {
    setColor(hue.hex.toUpperCase());
  };

  return (
    <div className={'uploadContainer'}>
      {loading ? (
        <div className={'loader-container'}>
          Uploading File...
          <Loader />
        </div>
      ) : (
        false
      )}

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
        <div className={'success'}>{success}</div>
        <Upload
          className={'upload'}
          filename={filename}
          setFilename={setFilename}
          callback={files => setFile(files[0])}
        ></Upload>
        <p>{errors.file ? errors.file : null}</p>
        <div className={'input-container'}>
          <div>
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

            <div className="colorContainerUpload">
              <p className="colorTitle">Color:</p>
              <ColorPickerContainer
                handleColorChange={handleColorChange}
                color={color}
                colors={colors}
                colorCircleStyle={colorCircleStyle}
              />
            </div>
            <div>
              <textarea
                onChange={e => setComments(e.target.value)}
                name="comments"
                rows="3"
                value={comments}
                placeholder={'Comments'}
              />
              <span>{errors.comments ? errors.comments : null}</span>
            </div>
          </div>
        </div>
        {errors.form ? (
          <div>
            <div className={'error'}>{errors.form ? errors.form : null}</div>
          </div>
        ) : null}
        <button className={'shapedButton'} type="submit">
          SUBMIT
        </button>
      </form>
    </div>
  );
}

export default UploadContainer;
