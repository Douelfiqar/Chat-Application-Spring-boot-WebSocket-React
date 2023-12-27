import React from 'react'

const ButtonRecord = ({id, funString, text}) => {
  return (
    <div>
        <button id={id} onClick={()=>funString}>{text}</button>
    </div>
  )
}

export default ButtonRecord