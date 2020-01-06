import React, { useState } from 'react';

import './SearchBar.css';

const SearchBar = props => {
  const [search, setSearch] = useState('');
  const [searched, setSearched] = useState(false);

  const searchQuery = event => {
    setSearch(event.target.value);
  };

  const submit = event => {
    props.submit(event);
    setSearch('');
    setSearched(true);
    event.preventDefault();
  };

  const goBack = () => {
    setSearched(false);
    props.fetchQueueData();
  };

  let submitBtn, goBackBtn;
  if (!searched) {
    submitBtn = <button type="submit" />;
  } else if (searched) {
    goBackBtn = (
      <button style={{ background: 'red' }} onClick={goBack}>
        Go Back
      </button>
    );
  }

  return (
    <form className="search-container" onSubmit={submit}>
      <input
        className="search-bar"
        name="input"
        value={search}
        onChange={searchQuery}
        type="text"
        placeholder="Search"
      />
      {submitBtn}
      {goBackBtn}
    </form>
  );
};

export default SearchBar;
