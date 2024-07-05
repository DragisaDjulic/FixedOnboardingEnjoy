import React from 'react'
import '../Css/profile.css'
import css from '../Css/profile.module.css'
import Userpic from '../components/userpic'
import UserInfo from '../components/user_info'
import Navbar from '../components/Navbar'
import { useParams } from 'react-router'
import {AiFillDelete} from 'react-icons/ai'
import {useState,useEffect, useContext} from 'react'
import { getUsers, deleteUser } from '../service/userApi';
import {userContext} from "../App"
import '../index.css'
import { useNavigate } from "react-router-dom";
import { eventWrapper } from '@testing-library/user-event/dist/utils'
import { Redirect } from "react-router";
import {getUserOnboardings} from '../service/onboardingApi'
import {AiOutlineCheck, AiOutlineUser} from 'react-icons/ai'
import LoadingSpinner from "../components/LoadingSpinner";
import ProfileImage from "../components/ProfileImage"
import NotificationAlertType from '../components/NotificationAlertType';

function Profile() {
  const [modal, setModal] = useState(false);
  
  const navigate = useNavigate();
  
  const toggleModal = () => {
    setModal(!modal);
  };


  const params = useParams();
  console.log(params);

  const {users, setUsers, currentUser} = useContext(userContext);
  const {onboardings, setOnboardings} = useContext(userContext);
  const [userOnboardings, setUserOnboardings] = useState([]);
  const [isLoading, setIsLoading] = useState(false);



  useEffect(()=>{
    setIsLoading(true);
    getUserOnboardings(params.id).then(res => {
      setTimeout(() => {
        setUserOnboardings(res)
        setIsLoading(false);

      },500)
      
    });
  },[])


  const ActiveUser = users;

  const curOnb = onboardings?.filter(onb => onb.users?.find(usr => usr.user.email == params.id));
  const user = users?.find(user => user?.email == params.id);

  const deleteThisUser = () => {
    deleteUser(user?.email).then(res => {
        // console.log(res);
        setUsers(users?.filter(usr => usr?.email != user?.email));
        navigate("/users", {state: {deletedUser: true}} );
    }).catch(err => console.log(err));

  }

  return (
    <>  
          <Navbar />

        <div className={['w-full flex items-start justify-center bg-black', css.gradient].join(' ')}>
          <div className='w-[45%] h-full flex justify-center flex-col items-center py-10
                        md:items-start md:flex-row md:w-[100%]
                        xl:w-[75%] xl:items-start xl:flex-row'>
                          

              {currentUser?.role.permission.name != "ROLE_EMPLOYEE" && <UserInfo profile={user}/>}

             
              {currentUser?.role.permission.name == "ROLE_EMPLOYEE" &&  <UserInfo profile={currentUser}/>}




          </div>
          {currentUser?.role.permission.name != "ROLE_EMPLOYEE" && <button onClick={toggleModal}>
          <AiFillDelete
                className='w-8 h-8 text-white absolute left-10 top-28
                          hover:text-red-500 transition duration-200 cursor-pointer'
            />
          </button>}
          {modal && (
            <div className="modal">
              <div onClick={toggleModal} className="overlay"></div>
              <div className="modal-content">
                <h2>ARE YOU SURE YOU WANT TO DELETE THIS USER?</h2>
                <div className='mt-10 flex flex-row space-x-8'>
                  <button onClick={deleteThisUser} className="text-red-600">
                    DELETE
                  </button>
                  <button className="close-modal" onClick={toggleModal}>
                    CLOSE
                  </button>
                </div>

              </div>
            </div>
          )}

        </div>
        <div className='w-full p-4 flex flex-col justify-center items-center'>
          <ul className='w-[50%] flex flex-col justify-center items-center text-left'>
            <p className='text-2xl mb-5'>Onboarding history</p>
            {isLoading ? <LoadingSpinner/> : (userOnboardings && userOnboardings.map(on =>  {
              console.log(on);
              return(
                <div key={on.id} className={`${on.isMentor ? "bg-yellow-100 text-black" : on.completed ? "bg-green-100 text-black" : "bg-gray-200"} border-solid border-black space-y-5 border-2 w-full mb-5 p-5 shadow-md`}>
                  <li><p className='text-xl text-center'>{on.name}</p></li>
                  <li><p className='text-justify'>{on.description}</p></li>
                  {on.completed && <div className='text-green-700  flex flex-row justify-end'><AiOutlineCheck className='self-center'/><p className='ml-1'>Completed</p></div>}
                  {on.isMentor && <div className='text-yellow-700  flex flex-row justify-end'><AiOutlineUser className='self-center'/><p className='ml-1'>Mentor</p></div>}
                </div>              
              )
            }))
            }
          </ul>
        </div>

        </>
  )
}

export default Profile