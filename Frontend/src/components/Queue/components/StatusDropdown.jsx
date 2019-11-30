import React from 'react';

const StatusDropdown = ({ currentStatus, statusChanged, id }) => {
  return (
    <form onChange={statusChanged}>
      <select name="status" defaultValue={currentStatus}>
        <option value="PENDING_REVIEW">Queueing</option>
        <option value="PRINTING">Printing</option>
        <option value="FAILED">Failed</option>
        <option value="COMPLETED">Complete</option>
      </select>
    </form>
  );
};

export default StatusDropdown;
