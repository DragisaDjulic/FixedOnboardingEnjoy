import React from 'react';
import {AiFillCloseCircle} from "react-icons/ai";
import { IoIosAddCircle } from 'react-icons/io';

function CreateTaskForm({  addTaskGroup, taskOnCurrOnb, setProperty, dellTask, subTaskDesc, tasks, setSubTaskDesc, addTasks }) {
  return (
    <form onSubmit={addTaskGroup} 
                    className='flex w-full space-y-12 space-x-4'>
                {/* Inputs for task */}
                <div className='flex flex-col w-full
                                md:w-full'>

                    <div className='flex flex-col justify-center mb-9'>
                        <input 
                            type='text'
                            placeholder='Task name'
                            required
                            value={taskOnCurrOnb?.name}
                            name="name"
                            onChange={(e) => setProperty(e)}
                            className='font-bold text-xl mb-4 text-black w-full p-1
                                        md:w-[40%]
                                        lg:w-[40%]
                                        xl:w-[30%]'
                        />
                        <label htmlFor="taskDescBox"
                                className='text-left'>
                                Task Description
                        </label>
                        <textarea name="description" 
                                id="taskDescBox" 
                                cols="30" 
                                rows="10"
                                value={taskOnCurrOnb?.description}
                                onChange={(e) => setProperty(e)}
                                placeholder="Describe task here"
                                className=' text-black p-5'></textarea>
                    </div>
                    
                    {/* Maping added subtasks after ADD TASK input */}

                    <div className='w-[80%]'>

                        {taskOnCurrOnb?.children?.map((subTask, i) =>
                            <div key={i} className='flex flex-row mb-5 space-x-5 w-auto'>
                                    <p  className='text-left'
                                        >{subTask.description}</p>

                                <div>
                                    <AiFillCloseCircle  size="30px" 
                                                        cursor="pointer"
                                                        className='hover:text-green-700 ease-in-out duration-300'
                                                        onClick={e => dellTask(e, i)}/>
                                </div>
                            </div>
                        )}

                    </div>

                    {/* Add subtasks funcionality */} 

                    <div className='flex flex-col w-full'>

                        <div className='flex flex-row w-full min-h-10 justify-start space-x-6 items-center'>

                            <textarea className='flex flex-start w-[100%] text-black p-2 rounded
                                                md:w-1/2' 
                                    type="text"
                                    placeholder='subTask'
                                    value={subTaskDesc}
                                    name="subTask"
                                    onChange={(e) => setSubTaskDesc(e.target.value)}
                                    /> 
                            <button>
                                <IoIosAddCircle disabled
                                                size="40px" 
                                                cursor="pointer" 
                                                className='hover:text-green-700 ease-in-out duration-300'
                                                onClick={(e) => addTasks(e)}/>
                            </button>
                        </div>

                    </div>

                    <div className='flex items-center mt-7 space-x-5'>
                        <button onSubmit={addTaskGroup}
                                className='w-fit p-3 rounded border-2 border-gray-300 bg-white hover:bg-green-700 hover:text-white ease-in-out duration-300'
                        >Submit Task</button>
                        <span className='rounded-full h-10 w-10 bg-white flex items-center justify-center text-black'>{tasks?.filter(task => task.color).length}</span>
                    </div>
                </div>
            </form>
  )
}

export default CreateTaskForm