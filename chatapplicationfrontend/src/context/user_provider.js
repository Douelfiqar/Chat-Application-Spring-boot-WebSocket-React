import axios from "axios"
import React, { useContext, useReducer } from "react"
import { LOGIN_USER } from "../action"
import reducer from "../reducers/user_reducer"

const initialState = {
    username: "",
    firstName: "",
    lastName: "",
    permission: ""
}
const UserContext = React.createContext()

export const UserProvider = ({children}) => {
    const [state, dispatch] = useReducer(reducer, initialState)

    const loginFunction = async (username, password) => {
        
        const response = await axios.post("http://localhost:8080/api/users",{
            username: username,
            password: password
          }, {
            headers: {
                'Content-Type': 'application/json',
            }
        })

        console.log(response);
        if(response.status == 200){
            var username = response.data.username
            var firstName = response.data.firstName
            var lastName = response.data.lastName
            var permission = response.data.permission

            dispatch({type: LOGIN_USER, payload: {username, firstName, lastName, permission}})
        }
    }

    return (
        <UserContext.Provider value={{...state, loginFunction}}>
            {children}
        </UserContext.Provider>
    )
}

export const useUserContext = () => {
    return useContext(UserContext)
}