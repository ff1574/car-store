// src/components/Settings.js
import React from 'react';

function Settings({ show, onBackgroundChange, onFontSizeChange }) {
  if (!show) {
    return null; // Do not render anything if show is false
  }

  return (
    <div style={{ padding: '10px', border: '1px solid #ccc', marginTop: '10px' }}>
      <div>
        <label>Background color: </label>
        <input type="color" onChange={(e) => onBackgroundChange(e.target.value)} />
      </div>
      <div>
        <label>Font size: </label>
        <select onChange={(e) => onFontSizeChange(e.target.value)}>
          <option value="14px">Small</option>
          <option value="18px">Medium</option>
          <option value="22px">Large</option>
        </select>
      </div>
    </div>
  );
}

export default Settings;
