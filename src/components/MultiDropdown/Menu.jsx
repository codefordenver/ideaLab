import React from 'react';
const Menu = (props) => {
    return (
        <ul className="dropdown">
            {props.children}
        </ul>
    );
 }

export default Menu;