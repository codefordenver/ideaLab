/**
 * Refer to Status.java for possible values of queue statuses
 */ class MenuTabs {
  QueueTabs = [
    {
      name: 'PENDING_REVIEW',
      label: 'Waiting',
    },
    {
      name: 'PRINTING',
      label: 'In Progress',
    },
    {
      name: 'DONE',
      label: 'Recently Completed',
    },
    {
      name: 'FAILED',
      label: 'Print Failed',
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
