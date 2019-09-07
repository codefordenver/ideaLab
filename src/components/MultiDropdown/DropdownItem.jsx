import React from 'react';
import { useState } from 'react';
const DropdownItem = (props) => {
    const [open, setOpen] = useState(false);
    return (<li onClick={() => setOpen(!open)} className={open ? 'open' : 'visually-hidden'}>{props.children}</li>)
}

export default DropdownItem;