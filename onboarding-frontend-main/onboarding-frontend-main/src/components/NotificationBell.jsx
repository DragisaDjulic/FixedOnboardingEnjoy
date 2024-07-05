import React, { useState, useRef, useEffect, useContext } from "react";
import { Transition } from "@headlessui/react";
import styles from "./Notification.module.css"
import NotificationPop from "./NotificationPop";
import { userContext } from "../App";


function NotificationBell(){
    const ref = useRef();

    // Getting notifications based on loged user from context
    const {newNotifications, setNewNotifications} = useContext(userContext);

    // ------Count unopened notifications
    let unOpenedNotifications = newNotifications.filter(el => el.opened == false).length;

    const [isMenuOpen, setIsMenuOpen] = useState(false);

    useEffect(() => {
        const checkIfClickedOutside = (e) => {
      // If the menu is open and the clicked target is not within the menu,
      // then close the menu
      if (isMenuOpen && ref.current && !ref.current.contains(e.target)) {
        setIsMenuOpen(false);
      }
    };

    document.addEventListener("click", checkIfClickedOutside);

    return () => {
      // Cleanup the event listener
      document.removeEventListener("click", checkIfClickedOutside);
    };
    }, [isMenuOpen]);


  


    return(
        <>
            <div className="flex items-center relative" ref={ref}>
                
            {/* New Notification indicatior red bubble */}
            {(unOpenedNotifications != 0) &&
                <div className="h-4 w-4 bg-red-600 border-2 border-white absolute right-0 top-0 rounded-xl"></div>
            }

            <button
                type="button"
                onClick={()=>setIsMenuOpen((oldState)=>!oldState)}
                className="block p-2.5 text-gray-600 bg-white rounded-lg hover:text-gray-700 shrink-0 shadow-sm"
                >
                <svg
                xmlns="http://www.w3.org/2000/svg"
                className="w-5 h-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth="2"
                >
                <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
                />
                </svg>
            </button>    
            <Transition 
            show={isMenuOpen}
            enter="transition ease-out duration-100 transform"
            enterFrom="opacity-0 scale-95"
            enterTo="opacity-100 scale-100"
            leave="transition ease-in duration-75 transform"
            leaveFrom="opacity-100 scale-100"
            leaveTo="opacity-0 scale-95"
            >
            {isMenuOpen && (
                <div className={["w-[26rem] bg-gray-100 absolute -right-[240px] top-10", styles.notifbox].join(' ')} id="mobile-menu">
                    <div ref={ref} className="w-auto pt-2 text-center ">

                        <NotificationPop />
                                               
                    </div>
                </div>
            )}
            </Transition>
       </div>   
        </>
       
    )


}

export default NotificationBell;
