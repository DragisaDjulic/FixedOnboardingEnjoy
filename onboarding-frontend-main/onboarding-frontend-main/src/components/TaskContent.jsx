import { ConnectingAirportsOutlined } from '@mui/icons-material';
import React, { useContext } from 'react'
import {useState} from 'react'
import { doTask } from "../service/taskApi";
import { userContext } from "../App"
import LoadingSpinner from './LoadingSpinner';
import { useEffect } from 'react';

function TaskContent({task, firstNotDone}) {
    const [checked, setChecked] = useState([]);
    const { setOnboardings } = useContext(userContext);
    const [isLoading, setIsLoading] = useState(false);


    const checkList = task?.children?.map(t=>t.description);
    console.log(checkList);
    const handleCheck = (task) => {
        setIsLoading(true);
            doTask(task.task).then(res => {
                setOnboardings(prev => prev.map(onb => onb?.id == task?.onboarding ? res : onb));
                setIsLoading(false);            
        })
    };

  return (
    <div className='flex flex-col sm:w-[90%] mb-10 space-y-10'>
        <div className='flex flex-col justify-center'>
            <h2 className='font-bold text-xl mb-4'>{task?.name}</h2>
            <p className='text-justify'>{task?.description}</p>
        </div>
        <div className='flex flex-col text-left'>
            <form className='flex flex-col text-xl space-y-3'>
                {isLoading ? <LoadingSpinner/> : (
                    checkList?.length !=0 && checkList?.map((item,index) =>(
                        <div key={index} className='flex flex-row space-x-3'>
                            <input value={index} className=' mt-2 flex self-start' disabled = {task?.serialNo != firstNotDone ? true : (task?.children[index].completed ? true : false)} checked={task?.children[index].completed ? "checked" : ""}  type="checkbox" name='task1' onChange={(e) => handleCheck(task.children[e.target.value])}/>
                            <span>{item}</span>
                        </div>
                    )))

                }
                {checkList?.length == 0 && 
                        <div className='flex flex-row space-x-3'>
                            <input  className='mt-2 flex self-start' type="checkbox" onChange={() => handleCheck(task)} disabled = {task?.serialNo != firstNotDone ? true : (task?.completed ? true : false)} checked={task?.completed ? "checked" : ""}/>
                            <span>Done</span>
                        </div>
                    }
                
            </form>
        </div>
    </div>
  )
}

export default TaskContent
