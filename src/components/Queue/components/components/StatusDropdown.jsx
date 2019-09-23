import React from 'react'
import './StatusDropdown.css'

const StatusDropdown = ({currentStatus, statusChanged}) => {
    return (
        <div className='statusDropdown'>
            <select className='statusDropdownSelect' name='status' defaultValue={currentStatus} onChange={statusChanged}>
                <option value='QUEUEING'>QUEUEING</option>
                <option value='PRINTING'>PRINTING</option>
                <option value='SUCCESS'>SUCCESS</option>
                <option value='FAILED'>FAILED</option>
                <option value='FAILED_AGAIN'>FAILED AGAIN</option>
            </select>
        </div>
    )
}

export default StatusDropdown
