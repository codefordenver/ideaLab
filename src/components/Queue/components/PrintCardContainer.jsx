import React, { useState, Fragment } from 'react';
import './PrintCardContainer.css';
import StatusDropdown from './components/StatusDropdown';
import PrintDateAdded from './components/PrintDateAdded';
import { CirclePicker } from 'react-color';

const PrintCardContainer = ({data}) => {
    const [isToggled,setIsToggled] = useState(false);
    const [circleColor, setcircleColor] = useState(data.color);
	const [hoverState, setHoverState] = useState(false);
	const dummyColors = ['red', 'blue', 'green'];

	const colorCircleStyle = {
		backgroundColor: `${circleColor}`
	};

	const handleChangeComplete = color => {
		console.log('New color will be', color);
		setcircleColor(color.hex);
	};

	const handleMouseEnter = () => {
		setHoverState(true);
	};

	const handleMouseLeave = () => {
		setHoverState(false);
	};

    const dropItDown = () => {
        setIsToggled(!isToggled);
	};
	
    const secondRowContent = isToggled ? (
        <div className='printCardContainerTop'>
            <p className='col20'>{data.name}</p>
            <p className='col20'>Stuff</p>
            <textarea className='col20'/>
        </div> 
        ) : null;

    return (
        <div className='printCardContainer'>
            <div className='printCardContainerTop'>
                {/* <img src='#' alt='hamLogo' className='col10'/> */}
                <div className='printFileName col20'>
                    <p>{data.fileName}</p>
                </div>
                <div className='colorContainer' onMouseLeave={handleMouseLeave}>
					<div
						className='colorCircle'
						style={colorCircleStyle}
						onMouseEnter={handleMouseEnter}
					></div>

					{hoverState ? (
						<div className='colorPickerContainer'>
							<CirclePicker
								onChangeComplete={handleChangeComplete}
								color={circleColor}
								colors={dummyColors}
							/>
						</div>
					) : (
						<Fragment />
					)}
				</div>
                <div className='submitDate col20'>
                    <PrintDateAdded data={data}/>
                </div>
                <div>
                    <StatusDropdown data={data}/>
                </div>
                <div className='printAdditionalInfo col20'>
                    <button onClick={dropItDown} className='dropButton'>Drop</button>
                    {/* <img alt='arrLogo'/> */}
                </div>
            </div>
            <div className='printCardContainerBottom'>
                    {secondRowContent}
            </div>         
        </div>
  );
};

export default PrintCardContainer;
