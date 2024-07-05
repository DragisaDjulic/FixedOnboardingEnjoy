import React from 'react'
import { useState, useEffect, useContext } from "react"
import { addTasksToOnboarding } from '../service/onboardingApi';
import {userContext} from "../App"
import TaskPreviewCard from './TaskPreviewCard';
import CreateTaskForm from './CreateTaskForm';
import NotificationAlertType from './NotificationAlertType';

function InputTaskContent({currOnb}) {

        const {currentUser, setCurrentUser}=useContext(userContext);
        const ondId = currOnb?.id;

        const [tasks, setTasks] = useState(currOnb?.tasks);
        
// ------------------------------------------------------------------

        const [subTaskDesc, setSubTaskDesc] = useState('');

        const [subTaskList, setSubTaskList] = useState([]);
        
// ----------Notification popup-----------------------------------------

        // const [changesCounter, setChangesCounter] = useState(0);

        const[notificationPopUp, setNotificationPopUp] = useState({
            visible: "hidden",
            type: '',
            text: '',
            changesCounter: 0
        })

        useEffect(() => {
            if(notificationPopUp?.changesCounter !== 0){
                setNotificationPopUp(prev => ({ ...prev, 
                    nvisible: "visible",
                    type: 'warning',
                    timed: false,
                    text: 'Unsaved Changes!!'
                    }));
            }
        }, [notificationPopUp?.changesCounter])
// ------------------------------------------------------------------

        const [taskOnCurrOnb, setTaskOnCurrOnb] = useState({
            createdBy: currentUser?.email,
            name: "",
            description: "",
            children: [],
            color: "bg-green-50"
        })

// ------------------------------------------------------------------
        const user1 = currOnb?.users?.find(user => user?.user?.email == currentUser?.email);
        const userActions = user1?.permission?.actions;
        
    useEffect(() => {

        setTasks(currOnb?.tasks);

    }, [currOnb])

    useEffect(() => {
            let maxSerialNo = tasks?.length ? tasks?.sort((a, b) => a - b)[tasks?.length-1]?.serialNo+1 : 1;
            setTaskOnCurrOnb(prev => ({...prev, serialNo: maxSerialNo}))

    }, [tasks])
    
        const addTaskGroup = (e) => {
            e.preventDefault();

            setTasks(prev => [...prev, taskOnCurrOnb]);
            setTaskOnCurrOnb(prev => ({...prev,
                name: "",
                description: "",
                children: [],
                color: "bg-green-50",
            }));

            setNotificationPopUp(prev => ({...prev,
                visible: "visible",
                type: 'warning',
                text: "Task deleted successfully",
                changesCounter: prev.changesCounter+1
            }));
        }
        
        const dellGroupTask = (e, i) => {
            e.preventDefault();
            if(currOnb?.tasks?.some(task => {
                return task.id == tasks[i].id
            }))
            {
                setNotificationPopUp(prev =>({...prev, changesCounter: prev.changesCounter+1}));
            }

            let newTasks = tasks.slice();
            newTasks.splice(i, 1);
            setTasks([...newTasks]);

            setNotificationPopUp(prev => ({...prev,
                visible: "visible",
                type: 'warning',
                text: "Task deleted successfully"
            }));
        }

        const turnin = () => {
            addTasksToOnboarding(ondId, tasks).then(res => {
                setTasks(res?.tasks)
            });

            setNotificationPopUp(prev =>({ ...prev,
                    visible : "visible",
                    opacityChange : "opacity-1",
                    type: 'success',
                    text:'Changes saved!',
                    timed: true
            }))
            setNotificationPopUp(prev =>({...prev, changesCounter: 0}));
        }
        
        const addTasks = (e, edit) => {
            e.preventDefault();
            if(subTaskDesc.length != 0 && edit == null){
                setTaskOnCurrOnb(prev => ({...prev, children: [...prev.children, {description: subTaskDesc}]}));
            }
            setSubTaskDesc('');
        }

        const onSaveEdit = (i, task) => {
            let newTask = tasks;
            newTask.splice(i, 1, task);
            setTasks([...tasks]);
            if(currOnb?.tasks?.some(task => {
                return task.id == tasks[i].id
            })){
                setNotificationPopUp(prev =>({...prev, changesCounter: prev.changesCounter+1}));
            }
            setNotificationPopUp(prev => ({...prev,
                visible: "visible",
                type: 'warning',
                text: "Task deleted successfully"
            }));
        }


        const dellTask = (e, i) => {
            e.preventDefault();
                let newList = taskOnCurrOnb.children;
                newList.splice(i, 1);
                setSubTaskList([...newList]);
        }

        const setProperty = (e) => {
            setTaskOnCurrOnb(prev => ({...prev, [e.target.name]: e.target.value}));
        }

        tasks?.sort((a, b) => a.serialNo - b.serialNo);


  return (

    <div className=''>
        {/* MAPING STAGED TASKS */}
        {tasks?.map((task, index) =>

         (
            
            <TaskPreviewCard key={task?.id} action={userActions}
                task={task} index={index}
                onSubmit={addTaskGroup} onDelete={dellGroupTask}
                onSaveEdit={onSaveEdit} dellTask={dellTask}
                bgColor="bg-gray-100" />              
        ))}

        {/* ADD TASK FORM */}

        {(userActions?.includes("create_task") || (currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN"))) && 
                      
            <CreateTaskForm addTaskGroup={addTaskGroup} taskOnCurrOnb={taskOnCurrOnb} 
                            setProperty={setProperty} dellTask={dellTask}
                            subTaskDesc={subTaskDesc} tasks={tasks}
                            setSubTaskDesc={setSubTaskDesc} addTasks={addTasks}/>            

        }

        <div className='w-full flex justify-end items-center'>
            <span className='rounded-full h-10 w-10 bg-white flex items-center justify-center text-black'>
                {notificationPopUp?.changesCounter}
            </span>
            <button onClick={(e) => turnin(e)}
                    disabled={((tasks?.length == 0) && (notificationPopUp.changesCounter == 0)) ? true : false} 
                    className="p-3 enabled:bg-white enabled:hover:bg-green-700 border-2 enabled:border-gray-300 enabled:hover:text-white ease-in-out duration-300 w-fit rounded enabled:text-black 
                               disabled:transform-none disabled:transition-none disabled:bg-gray-500">
                Turn in
            </button>
        </div>

        <NotificationAlertType bottomPosition={"bottom-10"} notificationPopUp={notificationPopUp} setNotificationPopUp={setNotificationPopUp} />
    </div>
  )
}

export default InputTaskContent