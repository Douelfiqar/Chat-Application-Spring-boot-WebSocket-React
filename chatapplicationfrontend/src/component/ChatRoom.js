import axios from 'axios';
import React, { useContext, useEffect, useState } from 'react'
import { useUserContext } from '../context/user_provider';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

const ChatRoom = () => {
    const [users, setUsers] = useState([]);
    const [conversationSelected, setConversationSelected] = useState("")
    const {username, firstName, lastName, permission} = useUserContext()
    

    const [message, setMessage] = useState([]);
    const [stompClient, setStompClient] = useState(null);
    const [inputMessage, setInputMessage] = useState('');
    const [messagePublicRoom, setMessagePublicRoom] = useState([]);

    const connection = () => {
        const socket = new SockJS('http://localhost:8080/gs-guide-websocket');
        const client = Stomp.over(socket);


        client.connect({}, () => {

          client.subscribe(`/user/${username}/queue/messages`, (message) => {
            const receivedMessage = JSON.parse(message.body);
            setMessage((prevMessage) => [...prevMessage, receivedMessage]);
          });

          client.subscribe("/chatroom/public", (message) => {
            console.log(message);

            // const receivedMessage1 = JSON.parse(message.body);
            handleReceivedPublicRoomMessage(JSON.parse(message.body));
            // setMessagePublicRoom((prevMessage) => [...prevMessage, receivedMessage1]);
          });

        });

        const handleReceivedPublicRoomMessage = (receivedMessage) => {
          setMessagePublicRoom((prevMessage) => [...prevMessage, receivedMessage]);
        };
    
    
        setStompClient(client);
    
        return () => {
          // Disconnect only if the client is connected
          if (client && client.connected) {
            client.disconnect();
          }
        };
    
      }


      const sendMessage = () => {
        if (stompClient && stompClient.connected) {
          if(conversationSelected === "publicRoom"){
            var messageToSave = {
              content: inputMessage,
              usernameSender : username,
              usernameReceiver : "publicRoom"
            }
            
            stompClient.send('/app/publicRoomLink', {}, JSON.stringify(messageToSave));
            
          }else{

              var newMessageValues = {
                content: inputMessage,
                usernameSender: username,
                usernameReceiver: conversationSelected
              };
        
              stompClient.send('/app/chat', {}, JSON.stringify(newMessageValues));
              var messageToSave = {
                id: 1,
                userSender : {
                  username: username,
                  firstName: firstName,
                  lastName: lastName
                },
                userReceiver : {
                  username: conversationSelected
                },
                message:{
                  content: inputMessage
                }
              }

              setMessage((prevMessage) => [...prevMessage, messageToSave]);
            }
            
          }
      };



    const getUsersConnected = async () => {
          await axios.get("http://localhost:8080/api/users/connectedUsers").then(resp=>{
              let tmp_users = resp.data
              tmp_users = tmp_users.filter(user => user.username !== username)
              setUsers(tmp_users)
              
          })
    }

    useEffect( () => {
        getUsersConnected()
        setTimeout(function() {
          // Code to be executed after a 0.5-second delay
      }, 50);
        connection()   
    }, [])

    useEffect(()=>{
        setConversationSelected(users.length > 0 ? users[0].username : null)
    }, [users])



  return (
    <div className="font-sans bg-gray-100">

  <div className="flex h-screen antialiased text-gray-900">
   
    <div className="w-1/4 bg-white border-r border-gray-200 p-4">
      <div className="text-lg font-semibold mb-4">Hello {username}</div>
      <div className="text-lg font-semibold mb-4">Chat Rooms</div>
      <ul>
          <li className="mb-2">
              <a href="#" onClick={(e)=>setConversationSelected("publicRoom")} className='text-blue-500 hover:underline' >Public Room</a>
          </li>
        {users.map((user) => (
          <li className="mb-2" key={user.id}>
              <a href="#" onClick={(e)=>setConversationSelected(user.username)} className={username !== user.username ? 'text-blue-500 hover:underline' : 'text-blue-500 hover:underline hidden'} >{user.username}</a>
          </li>
        ))}

      </ul>
    </div>

  
    <div className="flex-1 flex flex-col overflow-hidden">
      
      <div className="flex justify-between items-center bg-gray-200 p-4">
        <div className="text-xl font-semibold">{conversationSelected}</div>
        <div className="flex items-center space-x-2">
          <div className="bg-green-500 w-3 h-3 rounded-full"></div>
          <div className="text-sm">Online</div>
        </div>
      </div>

  
      <div className="flex-1 overflow-y-scroll p-4">
          
        {
        conversationSelected !== "publicRoom" && message
          .filter((msg) => 
            (msg.userSender.username === username && msg.userReceiver.username === conversationSelected) ||
            (msg.userSender.username === conversationSelected && msg.userReceiver.username === username)
          )
          .map((msg, index) => (
            <div key={index} className={msg.userSender.username === username ? "flex items-end justify-end mb-4" : "flex items-start mb-4"}>
              {msg.userSender.username !== username && (
                <div className="flex-shrink-0 mr-2">
                  <img src="https://placekitten.com/40/40" alt="User Avatar" className="w-8 h-8 rounded-full" />
                </div>
              )}
              <div key={index} className={msg.userSender.username === username ? "bg-gray-300 p-2 rounded-md" : "bg-blue-500 text-white p-2 rounded-md"}>
                {msg.message.content}
              </div>
              {msg.userSender.username === username && (
                <div className="flex-shrink-0 ml-2">
                  <img src="https://placekitten.com/40/40" alt="User Avatar" className="w-8 h-8 rounded-full" />
                </div>
              )}
            </div>
          ))
          }


        {conversationSelected === "publicRoom" &&
         messagePublicRoom
          .map((msg, index) => (

            <div key={index} className={msg.usernameSender === username ? "flex items-end justify-end mb-4" : "flex items-start mb-4"}>
              {msg.usernameSender !== username && (
                <div className="flex-shrink-0 mr-2">
                  <img src="https://placekitten.com/40/40" alt="User Avatar" className="w-8 h-8 rounded-full" />
                </div>
              )}
              <div key={index} className={msg.usernameSender === username ? "bg-gray-300 p-2 rounded-md" : "bg-blue-500 text-white p-2 rounded-md"}>
                {msg.content}
              </div>

              {msg.usernameSender === username && (
                <div className="flex-shrink-0 ml-2">
                  <img src="https://placekitten.com/40/40" alt="User Avatar" className="w-8 h-8 rounded-full" />
                </div>
              )}

            </div>
          )
          )}


      </div>

  
      <div className="bg-gray-200 p-4">
        <div className="flex">
          <input onChange={(e) => setInputMessage(e.target.value)} type="text" placeholder="Type your message..." className="flex-1 border rounded-md p-2 focus:outline-none focus:border-blue-500" />
          <button onClick={sendMessage} className="ml-2 bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300">Send</button>
        </div>
      </div>
    </div>
  </div>

</div>
  )
}

export default ChatRoom