import { IoIosAddCircle } from 'react-icons/io';
import Popup from './Popup'
import {useState,useEffect,useContext} from 'react'
import styles from "../components/Popup.module.css"
import { getRoles } from '../service/roleApi';
import {addUser, getUsers} from '../service/userApi'
import {userContext} from "../App"
import NotificationAlertType from './NotificationAlertType';


function AddUserBtn(){
    const [buttonPopup,setButtonPopup] = useState(false);
    const [roles, setRoles] = useState([]);
    const [newUser, setNewUser] = useState({
        firstName : '',
        lastName : '',
        role : '',
        email : '',
        phone : '',
        password : ''

    });
    const[notificationPopUp, setNotificationPopUp] = useState({
        visible: "hidden",
        type: '',
        text: '',
        changesCounter: 0
    })

   const {users, setUsers, currentUser} = useContext(userContext);

    useEffect(()=>{
        if(currentUser)
            getRoles().then(res => setRoles(res));
    },[currentUser])

    

    const handleSubmit = (e) => {
      if(!newUser.firstName || !newUser.lastName || !newUser.role || !newUser.email || !newUser.phone || !newUser.password){
        alert("Fill user info");          
      }
      else 
      {
        addUser(newUser).then(res => setUsers(prev => [...prev, res]));
      }
      setButtonPopup(false);
      setNotificationPopUp(prev => ({...prev,
        visible: "visible",
        type: 'success',
        text: "User added successfully",
        timed:true

    }));
    }
    


    return(
        <div>
            <Popup trigger={buttonPopup} setTrigger={setButtonPopup}>
                <div className={["flex flex-col p-7",styles.formuser].join(' ')}>
                    <form onSubmit={handleSubmit} className='flex flex-col space-y-5 justify-center text-center'>
                        <label>First name</label>
                        <input 
                        required
                        value={newUser.firstName}
                        onChange={(e) => setNewUser(prev => ({...prev, firstName:e.target.value}))}
                        className={[styles.inputbox].join(' ')} type="text"></input>
                        <label>Last name</label>
                        <input 
                        required
                        value={newUser.lastName}
                        onChange={(e) => setNewUser(prev => ({...prev, lastName:e.target.value}))}
                        className={[styles.inputbox].join(' ')} type="text"></input>

                        <label>Select user role</label>
                        <select 
                            
                            onChange={(e) => setNewUser(prev => ({...prev, role:e.target.value}))}
                        >
                            <option defaultValue="" disabled selected>Choose role</option>
                            {roles && roles.map(el => (<option key={el.id} value={el.id}>{el.name}</option>))}

                        </select>
                        <label>Email</label>
                        <input 
                        required
                        value={newUser.email}
                        onChange={(e) => setNewUser(prev => ({...prev, email:e.target.value}))}
                        className={[styles.inputbox].join(' ')} type="email"></input>
                        <label>Initial password</label>
                        <input 
                        required
                        value={newUser.password}
                        onChange={(e) => setNewUser(prev => ({...prev, password:e.target.value}))}
                        className={[styles.inputbox].join(' ')} type="password"></input>
                        <label>Phone number</label>
                        <input 
                        required
                        value={newUser.phone}
                        onChange={(e) => setNewUser(prev => ({...prev, phone:e.target.value}))}
                        className={[styles.inputbox].join(' ')} type="text"></input>
                            <button onClick={handleSubmit} className={["self-center",styles.savebtn].join(' ')} type="button" value="save">Create</button> 
                    </form>

                </div>
            </Popup>            
            <button className='fixed bottom-3 right-3 hover:text-green-700' onClick={()=>setButtonPopup(true)}>
                <IoIosAddCircle
                    size="80px"
                />
            </button>
            <NotificationAlertType bottomPosition={"bottom-24"} notificationPopUp={notificationPopUp} setNotificationPopUp={setNotificationPopUp}/>

        </div>
    );
}

export default AddUserBtn;