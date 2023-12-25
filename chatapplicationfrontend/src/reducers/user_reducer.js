import { LOGIN_USER } from "../action"

const user_reducer = (state, action) => {

    if(action.type === LOGIN_USER){

        const {username, firstName, lastName, permission} = action.payload
        return {...state, username:username, firstName:firstName, lastName:lastName, permission:permission}
    }

}

export default user_reducer