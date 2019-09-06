import React, { useState } from 'react';

const SearchBar = ({ filterByTerm }) => {
	const [search, setSearch] = useState('');

    const searchQuery = (event) => {
        setSearch(event.target.value);
        filterByTerm(event.target.value);
    }

	return (
        <div className='search-container'>
        <input
            className='search-bar'
            value={search}
            onChange={searchQuery}
            type='text'
            placeholder='Search'
        />
        </div>
	);
};

export default SearchBar;


