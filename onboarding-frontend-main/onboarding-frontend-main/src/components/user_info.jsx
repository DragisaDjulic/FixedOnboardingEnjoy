import React from 'react'
import Button from './UserButton'
import { AiFillEdit } from 'react-icons/ai';
import { useState } from "react"
import { useEffect, useContext } from 'react';
import {editUser} from '../service/userApi'
import {userContext} from "../App"
import { parsePath, useParams } from 'react-router';
import Onboardings from '../Pages/Onboardings';
import Userpic from '../components/userpic'
import NotificationAlertType from './NotificationAlertType';

function UserInfo({profile}) {

  const [userProfile, setUserProfile] = useState(profile);
  const {users, setUsers, currentUser} = useContext(userContext);
  const [image, setImage] = useState('');  

  console.log(userProfile);

  const [editUserFlags, setEditUserFlags] = useState({
    firstName: false,
    lastName: false,
    phone: false,
    role: false
  })
  const[notificationPopUp, setNotificationPopUp] = useState({
    visible: "hidden",
    type: '',
    text: '',
    changesCounter: 0
});

  useEffect(() => {
    if(profile) {
        setUserProfile(profile);
    }
  }, [profile]);

  const handleSubmit = () =>{
    setEditUserFlags({
        firstName: false,
        lastName: false,
        phone: false,
        role: false,
    })
    editUser(profile?.email, userProfile).then(res => setUsers(prev => prev.map(prof => prof?.email != profile?.email ? prof : res)));
    setNotificationPopUp(prev => ({...prev,
      visible: "visible",
      type: 'success',
      text: "User edited successfully",
      timed:true

  }));
  }

  const setProperty = (e) => {
    setUserProfile(prev => ({...prev, [e.target.name]: e.target.value}));
  }

  const setEditProp= (e, name) => {
    setEditUserFlags(prev => ({...prev, [name]: !prev[name]}));
    console.log(name);
  }

  const setProfileImg = (img) => {
    setUserProfile(prev => ({...prev, avatar: img}));
  }

  return (
    <>
      <Userpic onUpdate={setProfileImg} profpic={userProfile?.avatar}/> 
      <form 
              className='mt-10 w-[100%] justify-center text-lg
              md:m-0 md:flex md:flex-row md:justify-start md:flex-wrap md:w-[55%]
              xl:w-[30%]'>
          <div className='w-[100%] flex flex-wrap justify-center items-center
                          md:w-[50%] md:justify-between md:pr-3'>
              { editUserFlags.firstName ? 
              <input
                  type="text"
                  placeholder='First name'
                  required 
                  name="firstName"
                  value={userProfile?.firstName}
                  onChange={(e) => setProperty(e)}
                  className='text-black w-[100%]
                  md:w-[50%]'
              /> : 
              <p className='text-white w-[100%] text-center
                  md:w-[50%] md:text-left'>
                  {userProfile?.firstName}
              </p>
              }
              <AiFillEdit onClick={(e) => setEditProp(e, "firstName")}
                          className='w-[100%] h-8 mt-3 text-white
                                      cursor-pointer
                                      md:w-[20%] md:m-0'/>
          </div>
          <div className='w-[100%] flex flex-wrap justify-center items-center mt-10
                          md:w-[50%] md:mt-0 md:justify-between md:pr-3'>
              { editUserFlags.lastName ? 
              <input
                  type="text"
                  placeholder= "Last name"
                  required
                  name="lastName"
                  value={userProfile?.lastName}
                  onChange={(e) => setProperty(e)}
                  className='text-black w-[100%]
                  md:w-[50%]'
              /> 
              : 
              <p className='text-white w-[100%] text-center
                  md:w-[50%] md:text-left'>
                  {userProfile?.lastName}
                  </p>
              }
              <AiFillEdit onClick={(e) => setEditProp(e, "lastName")}
                          className='w-[100%] h-8 mt-3 text-white
                                      cursor-pointer
                                      md:w-[20%] md:m-0'/>
          </div>

          <div className='w-[100%] flex flex-wrap justify-center items-center mt-10
                          md:w-[50%] md:justify-between md:mt-4 md:pr-3'>
              { editUserFlags.role.name ? 
              <input
                  type="text"
                  placeholder= "role"
                  required
                  name="role"
                  value={userProfile?.role}
                  onChange={(e) => setProperty(e)}
                  className='text-black w-[100%]
                  md:w-[50%] mb:text-left'
              /> 
              : 
              <p className='text-white w-[100%] text-center
                  md:w-[50%] md:text-left'>
                  {userProfile?.role.name}
                  </p>
              }
          </div>

          <div className='w-[100%] flex flex-wrap justify-center items-center mt-10 text-white
                          md:w-[50%] md:justify-between md:mt-4'>
            <p>{userProfile?.email}</p>
          </div>

          <div className='w-[100%] flex flex-wrap justify-center items-center mt-10
                          md:w-[50%] md:justify-between md:mt-4 md:pr-3'>
              { editUserFlags.phone ? 
              <input
                  type="text"
                  placeholder= "phone"
                  required
                  name="phone"
                  value={userProfile?.phone}
                  onChange={(e) => setProperty(e)}
                  className='text-black w-[100%]
                  md:w-[50%]'
              /> 
              : 
              <p className='text-white w-[100%] text-center
                  md:w-auto md:text-left'>
                  {userProfile?.phone}
                  </p>
              }
              <AiFillEdit onClick={(e) => setEditProp(e, "phone")} 
                          className='w-[100%] h-8 mt-3 text-white
                                      cursor-pointer
                                      md:w-[20%] md:m-0'/>
          </div>
          <Button submit={handleSubmit} type={"button"} txt={"Save"}/>
          </form>
          <NotificationAlertType bottomPosition={"bottom-24"} notificationPopUp={notificationPopUp} setNotificationPopUp={setNotificationPopUp}/>    

    </>
  )
}

export default UserInfo