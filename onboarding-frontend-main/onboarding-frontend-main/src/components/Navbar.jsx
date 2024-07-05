import NotificationBell from "./NotificationBell"
import Burger from "./Burger"
import { Link } from "react-router-dom";
import { useContext, useState, useEffect } from "react";
import { userContext } from "../App";
import {RiDashboardFill} from 'react-icons/ri'
import {getNotifications} from '../service/notificationsApi';





function Navbar() {
  const {currentUser, setCurrentUser}=useContext(userContext);

  return (
    <>
      <header className="bg-gray-100 relative z-10">
          <div className="px-8 py-6 mx-auto ">

            <div className="flex flex-row sm:justify-between sm:gap-4 ">
              
              <div className='flex relative self-center'>
                    <Link to="/dashboard"><RiDashboardFill size="30px"/></Link>
              </div>
              <div className="relative hidden sm:block">

                <h1 className="text-2xl font-bold text-gray-900 sm:text-3xl">
                  Welcome {currentUser?.firstName}!
                </h1>
              </div>


              <div className="flex items-center justify-end flex-1  sm:justify-end">
                

                <div className="flex gap-4 mr-6">
                  <NotificationBell/>
                </div>

                <Burger/>

              </div>
            </div>
          </div>
      </header>

    </>

  );
}

export default Navbar;
