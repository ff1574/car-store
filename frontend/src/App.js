import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ManufacturerComponent from './components/ManufacturerComponent';
import CarsComponent from './components/CarsComponent';
import CustomersComponent from './components/CustomersComponent';
import OrdersComponent from './components/OrdersComponent';
import NavbarComponent from './components/NavbarComponent';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <header className="App-header">
          <NavbarComponent />
          <Routes>
            <Route path="/" element={<ManufacturerComponent />} />
            <Route path="/cars" element={<CarsComponent />} />
            <Route path="/customers" element={<CustomersComponent />} />
            <Route path="/orders" element={<OrdersComponent />} />
          </Routes>
        </header>
      </div>
    </BrowserRouter>
  );
}

export default App;
