import React from 'react'
import styles from "../components/Task.module.css"
import InputTaskContent from './InputTaskContent'
import ProgressCircle2 from './ProgressCircle2'
import TaskContent from './TaskContent'
import TaskHead from './TaskHead'
import {userContext} from "../App"
import { useState,useEffect, useContext, useRef } from 'react'
import { useParams } from 'react-router'
import { Transition } from "@headlessui/react";
import TaskCardStepper from '../components/TaskCardStepper'
import Mentors from '../components/Mentors'
import { getHelp } from '../service/taskApi';

function TaskCard() {
    const [currentTaskNmbr, setCurrentTaskNmbr] = useState(1);

    const params = useParams();
    const ref = useRef();    
    const {onboardings, setOnboardings, currentUser} = useContext(userContext);
    
    const onb = onboardings?.find(onboarding => onboarding?.id == params.id);
    const mentorPerm = (onb?.isMentor);
    const users = onb?.users;
    const allTasks = onb?.tasks;
    const mods = users?.filter(user => user.isMentor).map(u => u.user);
    const mentors = onb?.mentors;

    const [currentTask, setCurrentTask] = useState(null);
    const [notDoneTask, setNotDoneTask] = useState(currentTask?.serialNo);


    useEffect(()=>{
        //TODO CHECK IF EMPLOYEE OR ADMIN
        //tj ako je edit mode onda prikazuje sve taskove ne samo current jer current ne postoji
        setCurrentTask((allTasks?.find(task => !task.completed) || (allTasks && allTasks[allTasks.length-1])));
        setNotDoneTask((allTasks?.find(task => !task.completed) || (allTasks && allTasks[allTasks.length-1]))?.serialNo);
    },[allTasks])


    //IDE STEPER GAS
    const [currentIndex, setCurrentIndex] = useState(allTasks?.indexOf(currentTask));
    const totalSteps = allTasks?.length;
    const completedTask = allTasks?.map(t => t.completed);
    const completedSteps = completedTask?.length;
    const isLastStep = allTasks?.indexOf(currentTask) == allTasks?.length-1;
    const allStepsCompleted = completedSteps == totalSteps;

    const handleNext = () => {
        setCurrentIndex(prev => prev+1);
    };

    const handleBack = () => {
        setCurrentIndex(prev => prev-1);  
    };

    useEffect(() => {
        setCurrentIndex(allTasks?.indexOf(currentTask));
    }, [currentTask])

  return (
    <>
        <div className='flex flex-col w-full
                    md:flex-col md:justify-center md:items-center
                    xl:justify-center xl:items-center xl:flex-col'>
            <div className='flex flex-col
                        md:flex-col
                        xl:flex-row xl:w-[70%] xl:space-x-5 xl:justify-center xl:items-center'>
                <TaskHead onbinfo={onb}/>


                {((currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN")) && mentorPerm == false) && 
                    <div className='flex flex-col justify-center items-center mb-5 mt-5'>
                        <p>Tasks {allTasks?.reduce((a,b) => b.completed ? a+1: a, 0)}/{onb?.tasks.length}</p>
                        <ProgressCircle2 proginfo={allTasks} />
                    </div>
                }

            </div>
        
                {((currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN")) && mentorPerm == false) &&
                        
                    <TaskCardStepper tasks={allTasks} curIndex={currentIndex} setCurIndex={setCurrentIndex}/>    
                }
        </div>
        <div className={['flex flex-col-reverse w-full md:w-full lg-w-[70%] xl:w-[70%] justify-center md:flex-col-reverse lg:flex-row xl:flex-row', styles.bottomdiv].join(' ')}>
            {/* kad user gleda taskove */}
            {((currentUser && (currentUser?.role.permission.name != "ROLE_ADMIN")) && mentorPerm == false) && 
            <div className='flex p-12 flex-col w-full mt-5 bg-gray-100 rounded items-center
                            lg:w-full'>

                    <div className='flex flex-col w-full sm:flex-row'>
                        <TaskContent firstNotDone = {notDoneTask} task = {allTasks && allTasks[currentIndex]}/>
                    </div>

                    <div className='flex flex-col items-center justify-center space-x-0 space-y-5
                                    sm:space-y-0 sm:space-x-40
                                    md:w-fit md:flex-row'>
                        {(currentIndex != 0)&& 
                        <button onClick={() => handleBack()} className={['w-fit p-3 rounded border-2 border-gray-300 bg-white hover:bg-black hover:text-white ease-in-out duration-300',styles.taskbtn].join(' ')}>Previous</button>
                        }
                        <button onClick={() => getHelp(currentTask?.task)} className={['w-fit p-3 rounded border-2 border-gray-300 bg-white hover:bg-green-700 hover:text-white ease-in-out duration-300',styles.taskbtn].join(' ')}>Help</button>
                        {(currentIndex != allTasks?.length-1)&& 
                        <button onClick={() => handleNext()} className={['w-fit p-3 rounded border-2 border-gray-300 bg-white hover:bg-black hover:text-white ease-in-out duration-300',styles.taskbtn].join(' ')}>Next</button>
                        }
                    </div>

            </div>                   
            }
            {/* za kreiranje taskova */}
            {(currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN") || mentorPerm == true) &&        
                <div className='flex flex-col self-center justify-center mb-10 w-full shadow-md h-auto bg-gray-100
                                md:w-full
                                lg:w-full
                                xl:w-full'>

                        <div className='flex flex-col justify-center m-5'>
                            <InputTaskContent currOnb={onb}/>
                        </div>
                    
                </div>
            }
            <div className='w-full flex justify-center items-center 
                            mb-10 mt-5 md:justify-center md:items-start 
                            lg:w-[30%]'>
                    <Mentors moderators={mods} ment={mentors} mentPermission={mentorPerm}/>                    
            </div>   
        </div>
    </>
    
  )
}

export default TaskCard
