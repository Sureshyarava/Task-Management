import React, { useState, useEffect } from "react";

export default function TaskSearch({ onSearch }) {
  const [text, setText] = useState("");

  useEffect(() => {
  const delayDebounce = setTimeout(() => {
    onSearch(text);
  }, 400);

  return () => clearTimeout(delayDebounce);
}, [text, onSearch]);

  return (
    <input
      type="text"
      placeholder="Search by Title or Description"
      className="searchBar"
      value={text}
      onChange={(e) => setText(e.target.value)}
    />
  );
}