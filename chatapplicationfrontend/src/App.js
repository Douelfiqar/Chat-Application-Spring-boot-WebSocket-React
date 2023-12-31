import { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import Login from './component/Login';
import ChatRoom from './component/ChatRoom';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Dashboard from './component/Dashboard';

function App() {


  return (
    <div className="App">
      <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<Login />} /> 
        <Route exact path="/chatroom" element={<ChatRoom />} />
        <Route exact path="/dashboard" element={<Dashboard />} />
      </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
