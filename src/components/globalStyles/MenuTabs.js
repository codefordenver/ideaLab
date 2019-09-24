class MenuTabs {
  QueueTabs = [
    {
      name: 'QUEUEING',
      label: 'Queue',
    },
    {
      name: 'PRINTING',
      label: 'In Progress',
    },
    {
      name: 'DONE',
      label: 'Recently Completed',
    },
  ];
  ManageAccountTabs = [
    {
      name: 'components',
      label: 'Components',
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
