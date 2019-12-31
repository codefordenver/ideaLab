/**
 * Refer to Status.java for possible values of queue statuses
 */

class StatusEnum {
  StatusEnum = [
    {
      name: 'PENDING_REVIEW',
      label: 'Pending Review',
    },
    {
      name: 'FAILED',
      label: 'Failed',
    },
    {
      name: 'PRINTING',
      label: 'Printing',
    },
    {
      name: 'PENDING_CUSTOMER_RESPONSE',
      label: 'Pending Customer Response',
    },
    {
      name: 'REJECTED',
      label: 'Rejected',
    },
    {
      name: 'COMPLETED',
      label: 'Completed',
    },
    {
      name: 'ARCHIVED',
      label: 'Archived',
    },
  ];
}

const instance = new StatusEnum();
export { instance as StatusEnum };
export default StatusEnum;
