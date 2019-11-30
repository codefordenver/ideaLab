import React, { useState, Fragment } from 'react';
import './PrintCardContainer.css';
import StatusDropdown from './StatusDropdown';
import PrintDateAdded from './PrintDateAdded';
import { CirclePicker } from 'react-color';
import { IoIosArrowDown, IoIosArrowBack } from 'react-icons/io';
import { FiSave, FiMail } from 'react-icons/fi';

const PrintCardContainer = props => {
  const color = props.data.colorType.color
    ? props.data.colorType.color
    : props.data.colorType;
  const [isToggled, setIsToggled] = useState(false);
  const [card] = useState(props.data);
  const [updatedData, updateData] = useState({
    comments: card.comments,
    colorType: color,
    status: card.status,
  });
  const [hoverState, setHoverState] = useState(false);
  const [colors] = useState(props.colors);
  const [isSaveIconShowing, setSaveIconShowing] = useState(false);
  const { saveCard } = props;

  const colorCircleStyle = {
    backgroundColor: `${updatedData.colorType}`,
  };

  const handleColorChange = hue => {
    updateData(prevState => ({
      ...prevState,
      colorType: hue.hex,
    }));
    setSaveIconShowing(true);
  };
  const handleMouseEnter = () => {
    setHoverState(true);
  };

  const handleMouseLeave = () => {
    setHoverState(false);
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
    let updatedSavedCard = { id: card.id, employeeId: props.employeeId };
    for (var key in updatedData) {
      if (
        key === 'colorType' &&
        card.colorType.color !== updatedData.colorType.color
      ) {
        updatedSavedCard[key] = updatedData[key];
      } else if (
        key !== 'colorType' &&
        (card[key] && card[key] !== updatedData[key])
      ) {
        updatedSavedCard[key] = updatedData[key];
      }
    }
    saveCard(updatedSavedCard);
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
        <td
          className="colorContainer"
          colSpan="3"
          onMouseLeave={handleMouseLeave}
        >
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
                colors={colors}
                width="250px"
                circleSize={18}
                circleSpacing={8}
              />
            </div>
          ) : (
            <Fragment />
          )}
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
