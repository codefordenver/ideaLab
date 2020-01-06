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

  const goBackHandler = () => {
    setSearched(false);
    props.fetchQueueData();
  };

  let searchDiv, goBack;
  if (!searched) {
    searchDiv = (
      <div className="search-container">
        <input
          className="search-bar"
          name="input"
          value={search}
          onChange={searchQuery}
          type="text"
          placeholder="Search"
        />
        <button type="submit" />
      </div>
    );
  } else if (searched) {
    goBack = (
      <div className="go-back-container" onClick={goBackHandler}>
        <button onClick={goBackHandler} />
        <p>Go Back</p>
      </div>
    );
  }

  return (
    <form onSubmit={submit}>
      {searchDiv}
      {goBack}
    </form>
  );
};

export default SearchBar;
