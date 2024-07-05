import React, { useEffect } from 'react'
import { BsCheck2Circle } from "react-icons/bs";
import {TiWarningOutline} from "react-icons/ti";


function NotificationAlertType( {notificationPopUp, setNotificationPopUp, bottomPosition}) {
    
    useEffect(() => {
        if(notificationPopUp?.visible == "visible"){
            setNotificationPopUp(prev =>({ ...prev,
                opacityChange : "opacity-1",
            }));
            if(notificationPopUp?.timed){
                setTimeout(() => {
                    setNotificationPopUp(prev =>({ ...prev,
                    visible : "hidden",
                    opacityChange : "opacity-0",
                    type: ''
                    }));
                }, 3000);
            }
        }

    }, [notificationPopUp.visible, notificationPopUp.timed])

    let icon;
    let color;
    let text;

    switch (notificationPopUp?.type) {
        case "success":
            icon = <BsCheck2Circle className='self-center'/>
            color = "bg-green-100";
            text = notificationPopUp?.text;
            break;
        case "warning":
            icon = <TiWarningOutline className='self-center'/>
            color = "bg-red-100";
            text = "You have " + notificationPopUp?.changesCounter + " unsaved " + ((notificationPopUp?.changesCounter > 1) ? "changes!!" : "change!!!");
            break;
        case "delete":
            icon = <TiWarningOutline className='self-center'/>
            color = "bg-red-100";
            text = notificationPopUp?.text;
            break;
        default:
            break;
    }
  return (

    <div className={`${notificationPopUp?.opacityChange} ${notificationPopUp?.visible}
                      flex justify-center items-center rounded space-x-3 fixed ${bottomPosition} right-10
                      animate-bounce transition ease-in-out delay-150
                    ${color}  w-fit py-5 px-3 text-gray-500`}>
        {/* Setovanje ikonica */}
        {icon}
        <p className='pb-1'>
            {/* Setovanje texta */}
            {text}
        </p>
    </div>


  )
}

export default NotificationAlertType