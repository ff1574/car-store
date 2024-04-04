import React from 'react';
import ManufacturerComponent from './components/ManufacturerComponent.js';
import './App.css';
import CarsComponent from './components/CarsComponent.js';
import CustomersComponent from './components/CustomersComponent.js';
import OrdersComponent from './components/OrdersComponent.js';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <ManufacturerComponent />
        <CarsComponent />
        <CustomersComponent/>
        <OrdersComponent/>
      </header>
    </div>
  );
}

export default App;
