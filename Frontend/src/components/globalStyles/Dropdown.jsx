import React, { useState } from 'react';

import arrow from './img/Keyboard-arrow-left-01.svg';
import './Dropdown.css';

const Dropdown = props => {
    const [isDropped,toggleIsDropped] = useState(false);
    const [isSelected,setIsSelected] = useState(props.optionsName);

    const checkArrow = () => {
        const arrowImg = document.getElementById('arrow');
        let turn = isDropped ? '0deg' : '-90deg';
        arrowImg.setAttribute('style',`transform:rotate(${turn})`) 
    };

    const toggleDrop = () => {
        toggleIsDropped(!isDropped)
        checkArrow();
    };
    const selectItem = (item) => {
        setIsSelected(item);
        toggleIsDropped(!isDropped);
        checkArrow();
    };

    const options = props.options.map((e,i) => {
        return (<p key={i} onClick={selectItem.bind(null,e)}>{e}</p>)
    });

    return (
        <div className={'dd-container'}>
            <div onClick={toggleDrop} className='location'>
                <p>{isSelected}</p>
                <img src={arrow} alt='arrow' id='arrow'/>
            </div>
            {isDropped ? <div className='dd-options'>{options}</div>: null}
        </div>
    )
}

export default Dropdown