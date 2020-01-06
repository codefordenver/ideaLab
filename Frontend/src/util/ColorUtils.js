import RequestService from './RequestService';

const processActiveColors = () => {
  let activeColorList = [];

  RequestService.getActiveColors(
    response => {
      const data = response.data.data;
      data.map(color => {
        return activeColorList.push(color.color);
      });
    },
    error => console.error(error),
  );

  return activeColorList;
};

export { processActiveColors };
