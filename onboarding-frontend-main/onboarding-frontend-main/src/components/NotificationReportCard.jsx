import { Avatar } from '@mui/material'
import moment from 'moment';

function NotificationReportCard({notification, changeOpen, visible}) {

  let date = moment(notification?.date).format("dddd, MMMM Do YYYY, h:mm a");

  return (
    <div  onClick={ () => changeOpen(notification.id)}
          className={`flex flex-row cursor-pointer border-b-2 border-b-black space-x-5 py-3 
                    ${(notification.opened)? "bg-gray-50" : "bg-green-50"} px-5`}>
      <div className='flex justify-center items-center'>
        <Avatar />
      </div>
      <div className='flex flex-col'>
        <div className='text-left pb-2'>
          <p className={`${(visible == 3)? "line-clamp-3" : ""}`}>
            {notification?.text}
          </p>
        </div>
        <div>{date}</div>
      </div>
    </div>
  )
}

export default NotificationReportCard