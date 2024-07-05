import React from 'react'
import Popup from '../components/Popup'
import {useState,useEffect, useContext} from 'react'
import styles from "../components/UserPopup.module.css"
import Multiselect from 'multiselect-react-dropdown';
import {BsPersonPlusFill} from 'react-icons/bs'
import {getUsers} from '../service/userApi'
import { addUsersToOnboarding } from "../service/onboardingApi"; 
import {userContext} from "../App"
import Select from 'react-select'
import NotificationAlertType from './NotificationAlertType';




function NewUsers({onboardingId, onUserAdd}) {
  const {onboardings, setOnboardings, currentUser, setCurrentUser} = useContext(userContext);

    const[buttonPopup,setButtonPopup] = useState(false);
    const[selectedUsers, setSelectedUsers] = useState([]);
    // const[users, setUsers] = useState([]);
    const {users, setUsers} = useContext(userContext);


    const[notificationPopUp, setNotificationPopUp] = useState({
      visible: "hidden",
      type: '',
      text: '',
      changesCounter: 0
  })

    useEffect(()=>{
      if(currentUser && currentUser?.role.permission.name != "ROLE_EMPLOYEE")
        getUsers().then(res => setUsers(res));
  },[])
    const handleSubmit = (e) => {
        const data = { selectedUsers };  
        const selectedIds = selectedUsers.map(user => user.value);
        addUsersToOnboarding(onboardingId, selectedIds)
          .then(res => onUserAdd(selectedIds))
          .then(res => getUsers().then(res => setUsers(res)))
          .catch(err => console.log(err));
        setButtonPopup(false);
        setNotificationPopUp(prev => ({...prev,
          visible: "visible",
          type: 'success',
          text: "Users added to onboarding successfully",
          timed:true
  
      }));
      }

  return (
    <>
        <Popup trigger={buttonPopup} setTrigger={setButtonPopup}>
                <div className="flex flex-col p-7">
                    <form onSubmit={handleSubmit} className='flex flex-col space-y-5 justify-center text-center'>
                        <label className='text-black'>Select users</label>
                            <Select  options={users?.filter(u => !u.onboardings?.find(on => on.onboarding == onboardingId)).map(user => ({label: `${user.firstName} ${user.lastName}`,  value: user.email}))} onChange = {(e)=>{(setSelectedUsers(e))}} isMulti />
                            <button onClick={handleSubmit} className={["self-center hover:bg-green-700",styles.savebtn].join(' ')} type="button" value="save">Add</button> 
                    </form>
                </div>
            </Popup>
            <button  className='block ml-3 mr-3'  onClick={()=>setButtonPopup(true)}>
                <BsPersonPlusFill className='hover:text-green-700'
                    size="25px"
                />
            </button>
            <NotificationAlertType bottomPosition={"bottom-24"} notificationPopUp={notificationPopUp} setNotificationPopUp={setNotificationPopUp}/>

    </>
  )
}

export default NewUsers
