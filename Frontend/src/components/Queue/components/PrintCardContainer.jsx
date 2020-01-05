import React, { Fragment, useState } from 'react';
import { FiMail, FiSave } from 'react-icons/fi';
import { IoIosArrowBack, IoIosArrowDown } from 'react-icons/io';
import ColorPickerContainer from './ColorPickerContainer';
import './PrintCardContainer.css';
import PrintDateAdded from './PrintDateAdded';
import StatusDropdown from './StatusDropdown';

const PrintCardContainer = props => {
  const [isToggled, setIsToggled] = useState(false);
  const [card] = useState(props.data);
  const [updatedData, updateData] = useState({
    comments: card.comments,
    colorType: props.data.colorType.color,
    status: card.status,
  });
  const [colors] = useState(props.colors);
  const [isSaveIconShowing, setSaveIconShowing] = useState(false);

  const { saveCard } = props;

  const colorCircleStyle = {
    backgroundColor: `${updatedData.colorType}`,
  };

  const handleColorChange = hue => {
    updateData(prevState => ({
      ...prevState,
      colorType: hue.hex.toUpperCase(),
    }));
    setSaveIconShowing(true);
  };

  const updateComment = event => {
    event.persist();
    updateData(prevState => ({
      ...prevState,
      comments: event.target.value,
    }));
    setSaveIconShowing(true);
  };

  const updatePrintingStatus = event => {
    event.persist();
    updateData(prevState => ({
      ...prevState,
      status: event.target.value,
    }));
    setSaveIconShowing(true);
  };

  const dropItDown = () => {
    setIsToggled(!isToggled);
  };

  const saveChanges = () => {
    let updatedSavedCard = {
      id: card.id,
      employeeId: props.employeeId,
      email: props.data.customerInfo.email,
    };
    for (var key in updatedData) {
      if (
        key === 'colorType' &&
        card.colorType.color !== updatedData.colorType.color
      ) {
        updatedSavedCard[key] = updatedData[key];
      } else if (key === 'status') {
        updatedSavedCard.status = updatedData.status;
      } else if (
        key === 'comments' &&
        card.comments &&
        card.comments !== updatedData.comments
      ) {
        updatedSavedCard.comments = updatedData.comments;
      }
    }
    saveCard(updatedSavedCard);
    // props.fetchQueueData();
    setSaveIconShowing(false);
  };

  const toggleArrow = isToggled ? <IoIosArrowDown /> : <IoIosArrowBack />;

  const saveButton = isSaveIconShowing ? (
    <div className="saveIcon" onClick={saveChanges}>
      <FiSave />
    </div>
  ) : (
    <Fragment />
  );

  const secondRowContent = isToggled ? (
    <td id="secondRowContent">
      <span className="uniqueId">
        Unique ID: <b>{card.id}</b>
      </span>
      <span className="emailRecipient">
        Contact {card.customerInfo.firstName} <FiMail />
      </span>
      <textarea
        onChange={updateComment}
        name="comments"
        value={updatedData.comments}
        className="commentSection"
      />
    </td>
  ) : (
    <Fragment />
  );

  const updateFileUrlParams = fileSharableLink => {
    let url = new URL(fileSharableLink);
    let queryString = url.search;
    let searchParams = new URLSearchParams(queryString);

    // default dl=0, if set to 1 the file will auto download
    searchParams.set('dl', '1');
    url.search = searchParams.toString();

    return url.toString();
  };

  return (
    <tbody className="printCardContainer">
      <tr>
        <td className="printFileName">
          <a href={updateFileUrlParams(card.fileSharableLink)}>
            {card.filePath}
          </a>
        </td>
        <td className="colorContainer" colSpan="3">
          <ColorPickerContainer
            handleColorChange={handleColorChange}
            color={card.colorType.color}
            colors={colors}
            colorCircleStyle={colorCircleStyle}
          />
        </td>
        <td className="submitDate">
          <PrintDateAdded submitted={card.createdAt} />
        </td>
        <td>
          <StatusDropdown
            currentStatus={card.status}
            statusChanged={updatePrintingStatus}
            id={card.id}
          />
        </td>
        <td className="printAdditionalInfo">
          <div className="toggleArrow" onClick={dropItDown}>
            {toggleArrow}
          </div>
          {saveButton}
        </td>
      </tr>
      <tr>{secondRowContent}</tr>
    </tbody>
  );
};

export default PrintCardContainer;
