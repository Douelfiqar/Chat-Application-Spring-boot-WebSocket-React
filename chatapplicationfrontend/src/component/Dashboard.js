// App.js

import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useUserContext } from '../context/user_provider';

const Dashboard = () => {
    const url = "http://localhost:8080/api/users"
  const [users, setUsers] = useState([]);
  const [admins, setAdmins] = useState([]);
  const { username } = useUserContext()


  const getAllUsers = async () => {
    const response = await axios.get(url)
    setUsers(response.data)
  }

  const getAllAdmins = async () => {
    const response = await axios.get(url+"/getAdmins")
    var admins = response.data;
    admins = admins.filter(user => user.username !== username)
    setAdmins(admins)
  }

  const handleDeleteUser = async (user) => {
    setUsers((prevUsers) => prevUsers.filter((userLoop) => userLoop.username !== user.username));
    try {
        var usernameAxios = user.username 
        const response = await axios.post(`${url}/moveUserToAdmin`, { usernameAxios });
        console.log('User moved to admin:', response.data);
      } catch (error) {
        console.error('Error moving user to admin:', error);
      }
  };

  const handleDeleteAdmin = async (user) => {
    setUsers((prev)=>[...prev, user])
    setAdmins((prevUsers) => prevUsers.filter((userLoop) => userLoop.username !== user.username));

    try {
        var usernameAxios = user.username 
        const response = await axios.post(`${url}/moveAdminToUser`, { usernameAxios });
        console.log('Admin moved to user:', response.data);
      } catch (error) {
        console.error('Error moving admin to user:', error);
      }
  };
  const handleAddUserToAdmin = async (user) => {
    setAdmins((prev)=>[...prev, user])
    setUsers((prevUsers) => prevUsers.filter((userLoop) => userLoop.username !== user.username));
    try {
        var usernameAxios = user.username 
        const response = await axios.delete(`${url}/${usernameAxios}`);
        console.log('User deleted:', response.data);
      } catch (error) {
        console.error('Error deleting user:', error);
      }
  };
  
useEffect(()=>{
    getAllUsers()
    getAllAdmins()
}, [])

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Dashboard</h1>

      {/* Users list for ADMIN */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
        {users
          .filter((user) => user.role !== 'ADMIN') // Exclude ADMIN users from the list
          .map((user) => (
            <div
              key={user.username}
              className="bg-white p-4 rounded-md shadow-md flex flex-col justify-between"
            >
              <div>
                <div className="mb-2">
                  <span className="font-bold">Username:</span> {user.username}
                </div>
                <div className="mb-2">
                  <span className="font-bold">First Name:</span> {user.firstName}
                </div>
                <div className="mb-2">
                  <span className="font-bold">Last Name:</span> {user.lastName}
                </div>
                <div className="mb-2">
                  <span className="font-bold">Role:</span> {user.role}
                </div>
              </div>
              <button
                className="bg-red-500 text-white p-2 rounded-md hover:bg-red-600 focus:outline-none focus:ring focus:border-red-300"
                onClick={() => handleDeleteUser(user)}
              >
                Delete
              </button>
              <button
          className="bg-green-500 text-white mt-2 p-2 rounded-md hover:bg-green-600 focus:outline-none focus:ring focus:border-green-300"
          onClick={()=>handleAddUserToAdmin(user)}
        >
          Add ADMIN
        </button>
            </div>
          ))}
      </div>

      {/* Add ADMIN button for MOD */}
      <div className="mt-8">
        <h2 className="text-2xl font-bold mb-2">Add ADMIN (MOD)</h2>
       
       
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
        {admins
          .filter((user) => user.role !== 'ADMIN') // Exclude ADMIN users from the list
          .map((user) => (
            <div
              key={user.username}
              className="bg-white p-4 rounded-md shadow-md flex flex-col justify-between"
            >
              <div>
                <div className="mb-2">
                  <span className="font-bold">Username:</span> {user.username}
                </div>
                <div className="mb-2">
                  <span className="font-bold">First Name:</span> {user.firstName}
                </div>
                <div className="mb-2">
                  <span className="font-bold">Last Name:</span> {user.lastName}
                </div>
                <div className="mb-2">
                  <span className="font-bold">Role:</span> {user.role}
                </div>
              </div>
              <button
                className="bg-yellow-200 text-white p-2 rounded-md hover:bg-red-600 focus:outline-none focus:ring focus:border-red-300"
                onClick={() => handleDeleteAdmin(user)}
              >
                Remove Admin
              </button>
            </div>
          ))}
      </div>

      </div>
    </div>
  );
};

export default Dashboard;
