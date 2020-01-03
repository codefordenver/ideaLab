import React, { useState } from 'react';

import './SearchBar.css';

const SearchBar = ({ filterByTerm }) => {
  const [search, setSearch] = useState('');

  const searchQuery = event => {
    console.log(event.target.value);
    setSearch(event.target.value);
    // filterByTerm(event.target.value);
  };

  const submitHandler = () => {
    console.log(search, ' submitted');
  };

  return (
    <form className="search-container" onSubmit={submitHandler}>
      <input
        className="search-bar"
        value={search}
        onChange={searchQuery}
        type="text"
        placeholder="Search"
      />
      <button type="submit" />
    </form>
  );
};

export default SearchBar;
