import React, { useState } from 'react';
import { FaSearch, FaBackspace } from 'react-icons/fa';

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
        <button type="submit">
          <FaSearch />
        </button>
      </div>
    );
  } else {
    goBack = (
      <div className="search-container back" onClick={goBackHandler}>
        <button onClick={goBackHandler}>
          <FaBackspace />
        </button>
        <p className="search-go-back-text">Back</p>
      </div>
    );
  }

  return (
    <form className="search-bar-form" onSubmit={submit}>
      {searchDiv}
      {goBack}
    </form>
  );
};

export default SearchBar;
