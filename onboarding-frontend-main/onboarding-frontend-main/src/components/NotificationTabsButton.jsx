import React from 'react'

function NotificationTabsButton({setCurrentCard, text, value, width, focused, notificationCount}) {

  return (
    <button className={`${width} ${focused == value ? "border-b-2 border-black font-bold": ""} px-5 py-3 group`} 
            type="buton" 
            onClick={() => {setCurrentCard(value)}}>
        {text}
        <span className={`ml-2 px-[8px] rounded bg-gray-200 text-gray-600
                        group-focus:bg-black group-focus:text-white`}>{notificationCount}</span>
    </button>
  )
}

export default NotificationTabsButton