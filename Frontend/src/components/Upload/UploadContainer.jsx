import React from 'react';

function UploadContainer(){
  return (
    <div>
      <form>
        <input
          type='text'
          id='name'
          placeholder='Name'/>
        <input
          type='text'
          id='color'
          placeholder='Color'/>
        <textarea
          id='comments'
          placeholder='Comments'/>
        <button type='submit'>SUMBIT</button>
      </form>
    </div>
  );
}

export default UploadContainer;