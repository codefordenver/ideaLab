import React, { useState } from 'react';

const MenuBar = ({ selectedTab, tabOptions, setView }) => {

    const tabDisplay = tabOptions.map((tab, index) => {
        return (<li className={tab.name === selectedTab ? 'selectedTab' : ''}>
            <button onClick={() => setView(tab.name)}>{tab.label}</button>
        </li>)
    });
	return (
        <div>
            <ul className='statusMenu'>
                {tabDisplay}
            </ul>
        </div>
	);
};

export default MenuBar;


