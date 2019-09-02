import React, { useState } from 'react';
import './PrintCardContainer.css';
import StatusDropdown from './components/StatusDropdown';
import PrintDateAdded from './components/PrintDateAdded';

const PrintCardContainer = ({data}) => {
    const colorCircleStyle = {
        backgroundColor: `${data.color}`
    };

    return (
        <div className='printCardContainer'>
            <img src='#' alt='hamLogo' className='col10'/>
            <div className='printFileName col20'>
                <p>{data.fileName}</p>
            </div>
            <div className='col20'>
                <div className='colorCircle' style={colorCircleStyle}></div>
            </div>
            <div className='submitDate col20'>
                <PrintDateAdded data={data}/>
            </div>
            <div>
                <StatusDropdown data={data}/>
            </div>
            <div className='printAdditionalInfo col20'>
                <img alt='arrLogo'/>
            </div>
        </div>
  );
};

export default PrintCardContainer;
