import React, {useState} from 'react'
import './StatusDropdown.css'

const StatusDropdown = ({data}) => {
    const [printStatus,setPrintStatus] = useState(data.status);
    const badMessage = 'Not functioning correctly...'

    return (
        <div className='statusDropdown'>
            <select className='statusDropdownSelect' name='printStatus' defaultValue={printStatus}>
                <option>PRINTING</option>
                <option>QUEUEING</option>
            </select>
        </div>
    )
}

export default StatusDropdown
