/**
 * Refer to Status.java for possible values of queue statuses
 */ class MenuTabs {
  QueueTabs = [
    {
      name: 'PENDING_REVIEW',
      label: 'Pending Review',
    },
    {
      name: 'PRINTING',
      label: 'In Progress',
    },
    {
      name: 'PENDING_CUSTOMER_REVIEW',
      label: 'Pending Customer Review',
    },
    {
      name: 'DONE',
      label: 'Recently Completed',
    },
  ];
  ManageAccountTabs = [
    {
      name: 'settings',
      label: 'Settings',
    },
    {
      name: 'users',
      label: 'User Profiles',
    },
  ];
}

const instance = new MenuTabs();
export { instance as MenuTabs };
export default MenuTabs;
