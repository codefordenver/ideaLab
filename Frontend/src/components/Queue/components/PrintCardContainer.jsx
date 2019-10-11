import React, { useState, Fragment } from 'react';
import './PrintCardContainer.css';
import StatusDropdown from './components/StatusDropdown';
import PrintDateAdded from './components/PrintDateAdded';
import { CirclePicker } from 'react-color';
import { IoIosArrowDown, IoIosArrowBack } from 'react-icons/io';
import { FiSave, FiMail } from 'react-icons/fi';

const PrintCardContainer = ({ data }) => {
  const [isToggled, setIsToggled] = useState(false);
  const [card, updateCard] = useState(data);
  const [hoverState, setHoverState] = useState(false);

  const dummyColors = ['red', 'blue', 'green', 'black'];

  const colorCircleStyle = {
    backgroundColor: `${card.color}`,
  };

  const handleColorChange = hue => {
    updateCard(prevState => ({ ...prevState, color: hue.hex }));
  };
  const handleMouseEnter = () => {
    setHoverState(true);
  };

  const handleMouseLeave = () => {
    setHoverState(false);
  };

  const updateComment = event => {
    event.persist();
    updateCard(prevState => ({ ...prevState, comments: event.target.value }));
  };

  const updatePrintingStatus = event => {
    event.persist();
    updateCard(prevState => ({ ...prevState, status: event.target.value }));
  };

  const dropItDown = () => {
    setIsToggled(!isToggled);
  };

  const saveChanges = () => {
    //POST request goes here! placeholder:
    alert('saving changes');
  };

  const toggleArrow = isToggled ? <IoIosArrowDown /> : <IoIosArrowBack />;

  const saveButton =
    data === card ? null : (
      <div className="saveIcon" onClick={saveChanges}>
        <FiSave />
      </div>
    );

  const secondRowContent = isToggled ? (
    <div className="printCardContainerBottom">
      <div className="emailRecipient col20">
        {data.name} <FiMail />
      </div>
      <textarea
        onChange={updateComment}
        name="comments"
        value={card.comments}
        className="commentSection"
      />
    </div>
  ) : null;

  const updateDropboxUrlParams = dropboxSharableLink => {
    let url = new URL(dropboxSharableLink);
    let queryString = url.search;
    let searchParams = new URLSearchParams(queryString);

    // default dl=0, if set to 1 the file will auto download
    searchParams.set('dl', '1');
    url.search = searchParams.toString();

    return url.toString();
  };

  return (
    <div className="printCardContainer">
      <div className="printCardContainerTop">
        {/* <img src='#' alt='hamLogo' className='col10'/> */}
        <div className="printFileName col20">
          <a href={updateDropboxUrlParams(data.dropboxSharableLink)}>
            {data.dropboxPath}
          </a>
        </div>
        <div className="colorContainer" onMouseLeave={handleMouseLeave}>
          <div
            className="colorCircle"
            style={colorCircleStyle}
            onMouseEnter={handleMouseEnter}
          ></div>

          {hoverState ? (
            <div className="colorPickerContainer">
              <CirclePicker
                onChangeComplete={handleColorChange}
                color={card.color}
                colors={dummyColors}
                width="100px"
              />
            </div>
          ) : (
            <Fragment />
          )}
        </div>
        <div className="submitDate col20">
          <PrintDateAdded submitted={data.submitted} />
        </div>
        <div>
          <StatusDropdown
            currentStatus={card.status}
            statusChanged={updatePrintingStatus}
          />
        </div>
        <div className="printAdditionalInfo col20">
          {saveButton}
          <div className="toggleArrow" onClick={dropItDown}>
            {toggleArrow}
          </div>
        </div>
      </div>
      {secondRowContent}
    </div>
  );
};

export default PrintCardContainer;
