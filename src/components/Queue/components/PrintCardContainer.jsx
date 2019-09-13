import React, { useState, Fragment } from 'react';
import './PrintCardContainer.css';
import StatusDropdown from './components/StatusDropdown';
import PrintDateAdded from './components/PrintDateAdded';

import { CirclePicker } from 'react-color';

const PrintCardContainer = ({ data }) => {
	const [circleColor, setcircleColor] = useState(data.color);
	const [hoverState, setHoverState] = useState(false);

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

	const dummyColors = ['red', 'blue', 'green'];

	console.log(circleColor);

	return (
		<div className='printCardContainer'>
			<div className='printFileName'>
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
			<div className='submitDate'>
				<PrintDateAdded data={data} />
			</div>
			<div>
				<StatusDropdown data={data} />
			</div>
			<div className='printAdditionalInfo'>
				<img alt='arrLogo' />
			</div>
		</div>
	);
};

export default PrintCardContainer;
