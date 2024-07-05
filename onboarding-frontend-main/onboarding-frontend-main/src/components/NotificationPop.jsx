import React, { useState, useEffect, useContext } from "react";
import NotificationTabsButton from "./NotificationTabsButton";
import NotificationReportCard from "./NotificationReportCard";
import { userContext } from "../App";
import {getNotifications, readNotification, openAllNotifications} from '../service/notificationsApi';



function NotificationPop() {

  const {currentUser, setCurrentUser}=useContext(userContext);
  const [notifications, setNotfications] = useState([]);

  const [onboardingNotifications, setOnboardingNotifications] = useState([]);
  const [mentorNotifications, setMentorNotifications] = useState([]);

  const [showMoreButton, setShowMoreButton] = useState(true);

  const[visible, setVisible] = useState(3)


  //-------Load more/hide 3 notifications at a time
  const showMoreItems = () =>{
    if(visible < notifications.length){
      setVisible((prev) => prev +3); 
    }
    else if(visible >= notifications.length){
      setVisible(3);
    }
  }

  let text;

    //-----------Filtering notificaions to show in different tabs
    useEffect(() => {
      
      if(currentUser)
        getNotifications().then(res => {
          setOnboardingNotifications(res.filter(el => el.isMentor == false).map(el =>
            ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})
          ));
          setMentorNotifications(res.filter(el => el.isMentor == true).map(el => 
            ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})
          ));
          setNotfications(res.map(el => ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})));
      });

    },[currentUser]);

    //----------Timer for interval base call to load new notifications

    // setInterval( () => {
    //   if(currentUser)
    //     getNotifications().then(res => {
    //       setOnboardingNotifications(res.filter(el => el.isMentor == false).map(el =>
    //         ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})
    //       ));
    //       setMentorNotifications(res.filter(el => el.isMentor == true).map(el => 
    //         ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})
    //       ));
    //       setNotfications(res.map(el => ({...el, color: !el.opened? "bg-green-50":"bg-gray-50"})));
    //   });
    // }
    // , 5000);

    //----------Set the opened property of the notification when it is clicked
    const changeOpen = (id) => {
        readNotification(id).then(
          setNotfications(el => el.map(notif => notif.id == id ? ({...notif, opened: true}) : notif))
        )
    }

    // ------Count unopened notifications
    let newNotifications = notifications.filter(el => el.opened == false).length;
    let UnOpenedOnboardingNotifications = onboardingNotifications.filter(el => el.opened == false).filter(el => el.isMentor == false).length;
    let UnOpenedMentorNotifications = mentorNotifications.filter(el => el.opened == false).filter(el => el.isMentor == true).length;

    //---------Set all notifications to opened based on loged user
    const openAll = () => {
      openAllNotifications(currentUser.email).then( res => 
        setNotfications(prev => prev.map(el => ({...el, opened: true})))
    )}

    // NOTIFICATION SORT
    const [currentCard, setCurrentCard] = useState(1);

  return (
    <div className="flex flex-col w-full ">

      <div className="w-full flex justify-between items-center px-2 py-3">
        <h1 className="font-bold text-2xl">Notification</h1>
        <p onClick={openAll} className="text-black shadow-sm text-sm underline font-bold cursor-pointer">Mark all as read</p>
      </div>

      {/* Tabs for different notification types */}
      <div className="flex w-auto px-5 border-b-2">
        {/* All notifications */}
        <NotificationTabsButton setCurrentCard={setCurrentCard} text={"All"} value={1} notificationCount={newNotifications} 
                                width={"w-auto"}
                                focused={currentCard}/>
        {/* Oboardings notifications */}
        <NotificationTabsButton setCurrentCard={setCurrentCard} text={"Onboaring"} value={2} notificationCount={UnOpenedOnboardingNotifications}  
                                width={"w-auto"}
                                focused={currentCard}/>
        {/* Mentor notifications */}
        <NotificationTabsButton setCurrentCard={setCurrentCard} text={"Mentor"} value={3} notificationCount={UnOpenedMentorNotifications}  
                                width={"w-auto"}
                                focused={currentCard}/>
      </div>


      {/* Showing notifications on selected tab */}
      <div className="w-ull">
        <div className="">
          {/* All notifications */}
          {currentCard == 1 && 
          <>
              <div className={`w-full overflow-y-auto ${(notifications.length > 3)? "h-[25rem]" : "h-auto"}`}>
                {notifications?.slice(0, visible).map((notification) => (
                  <NotificationReportCard key={notification.id} notification={notification} changeOpen={changeOpen} visible={visible}/>
                ))}
              </div>

              {/* Show more/less notifications */}
              {(((visible >= notifications.length)? text = 'Show less' : text = 'Show more') && notifications.length > 3) &&
                <button onClick={showMoreItems}>{text}</button>
              }
              {(notifications.length == 0) &&
                <div>No notificaions</div>
              }
          </>
          }
          {/* Notifications on active onboaring */}

          {currentCard == 2 && 
            <>
              <div className={`w-full overflow-y-auto ${(onboardingNotifications.length > 3)? "h-[25rem]" : "h-auto"}`}>
                  {onboardingNotifications?.slice(0, visible).map((notification) =>
                    <NotificationReportCard key={notification.id} notification={notification} changeOpen={changeOpen} visible={visible} />
                  )}
              </div>
              {/* Show more/less notifications */}
              {(((visible >= notifications.length)? text = 'Show less' : text = 'Show more') && onboardingNotifications.length > 3) &&
                <button onClick={showMoreItems}>{text}</button>

              }
              {(onboardingNotifications.length == 0) &&
                <div>No notificaions</div>
              }
            </>
          }
          {/* Notifications on mentoring onboardings */}
          {(currentCard == 3) && 
            <>
              <div className={`w-full overflow-y-auto ${(mentorNotifications.length > 3)? "h-[25rem]" : "h-auto"}`}>
                  {mentorNotifications?.slice(0, visible).map((notification) => (
                    <NotificationReportCard key={notification.id} notification={notification} changeOpen={changeOpen} visible={visible} />
                  ))}
              </div>
              {/* Show more/less notifications */}
              {(((visible >= mentorNotifications.length)? text = 'Show less' : text = 'Show more') && mentorNotifications.length > 3) &&
                <button onClick={showMoreItems}>{text}</button>
              }
              {(mentorNotifications.length == 0) &&
                <div>No notificaions</div>
              }
            </>
          }

        </div>
      </div>

    </div>
  )
}

export default NotificationPop