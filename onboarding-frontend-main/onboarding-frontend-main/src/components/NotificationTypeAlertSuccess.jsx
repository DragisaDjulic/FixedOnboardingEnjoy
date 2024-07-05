import React from 'react'
import { BsCheck2Circle } from "react-icons/bs";

function NotificationTypeAlertSuccess({noticationComponentAlert, text}) {

  console.log(noticationComponentAlert)
  console.log(text);

  return (
    <div className={`${noticationComponentAlert?.opacityChange} ${noticationComponentAlert?.visible}
                      flex justify-center items-center rounded space-x-3 fixed right-10 bottom-10
                      animate-bounce transition ease-in-out delay-150
                    bg-green-100  w-fit py-5 px-3 text-gray-500`}>
      <BsCheck2Circle className='self-center'/>
      <p className='pb-1'>
          {text}
      </p>
  </div>
  )
}

export default NotificationTypeAlertSuccess