import React from 'react';
import './UploadContainer.css';

function UploadContainer(){
  return (
    <div className={"uploadContainer"}>
      <form>
        <input
          type='text'
          id='name'
          placeholder='Name'/>
        <input
          type='text'
          id='color'
          placeholder='Color'/>
        <input
          id='comments'
          placeholder='Comments'/>
        <button className={"shapedButton"} type='submit'>SUMBIT</button>
      </form>
    </div>
  );
}

export default UploadContainer;