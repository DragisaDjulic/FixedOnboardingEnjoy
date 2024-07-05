import React from 'react'
import {AiFillCloseCircle} from "react-icons/ai"
import { IoIosAddCircle } from 'react-icons/io';

function TaskPreviewCreateTask({newChild, setNewChild,  cancelEdit, setProperty, saveEditTask, dellSubTask, editSelectedTask, setEditSelectedTask, task }) {

     const addSubTask = (e) => {
        e.preventDefault();
        let tempChild;
        if(newChild){
            tempChild = {
                description: newChild,
            }
                setEditSelectedTask(prev => ({...prev, children: 
                    ((prev && [...prev?.children, tempChild]) || [...task?.children.slice(), tempChild])}));
            setNewChild('');
        }
    }

    return (
    <form  onSubmit={(e) => saveEditTask(e)}
                className='flex flex-col w-full space-y-12 space-x-4 bg-yellow-100 text-black p-2 mb-2'>
            {/* Inputs for task */}
            <div className='flex flex-col w-full
                            md:w-full'>

                <div className='flex flex-col justify-center mb-9'>
                    <input 
                        type='text'
                        placeholder='Task name'
                        required
                        value={editSelectedTask?.name}
                        name="name"
                        onChange={(e) => setProperty(e)}
                        className='font-bold text-xl mb-4 text-black w-full p-1
                                    md:w-[40%]
                                    lg:w-[40%]
                                    xl:w-[30%]'
                    />
                    <label htmlFor="taskDescBox"
                            className='text-left'>
                    </label>
                    <textarea
                            id="taskDescBox" 
                            cols="30" 
                            rows="10"
                            name="description"
                            value={editSelectedTask?.description}
                            onChange={(e) => setProperty(e)}
                            placeholder="Describe task here"
                            className=' text-black p-5'></textarea>
                </div>
                {/* EDITED */}
                {/* Maping added subtasks after ADD TASK input */}

                <div className='w-[80%]'>

                    {editSelectedTask?.children?.map((subTask, i) =>
                        <div key={subTask.id} className='flex flex-row mb-5 space-x-5 w-auto'>
                                <p  className='text-left'
                                    >{subTask?.description}</p>

                            <div>
                                <AiFillCloseCircle  size="30px" 
                                                    cursor="pointer"
                                                    className='hover:text-green-700 ease-in-out duration-300'
                                                    onClick={e => dellSubTask(e, i)}
                                                    />
                            </div>
                        </div>
                    )}

                </div>
                {/* EDIT */}
                {/* Add subtasks funcionality */} 

                <div className='flex flex-col w-full'>

                    <div className='flex flex-row w-full min-h-10 justify-start space-x-6 items-center'>

                        <textarea className='flex flex-start w-[100%] text-black p-2 rounded
                                            md:w-1/2' 
                                type="text"
                                placeholder='subTask'
                                name="subTask"
                                value={newChild}
                                onChange={(e) => setNewChild(e.target.value)}
                                /> 
                        
                        <IoIosAddCircle size="40px" 
                                        cursor="pointer" 
                                        className='hover:text-green-700 ease-in-out duration-300'
                                        onClick={e => addSubTask(e)}/>
                    </div>

                </div>

                </div>
                <div className='flex items-center mt-7 space-x-5'>
                    <button onClick={(e) => saveEditTask(e)}
                            className='w-fit p-3 rounded border-2 border-gray-300 bg-white hover:bg-green-700 hover:text-white ease-in-out duration-300'
                    >Save</button>
                    <div    onClick={(e) => cancelEdit(e)}
                            className='cursor-pointer w-fit p-3 rounded bg-black text-white border-2 border-gray-300 hover:bg-gray-500 hover:text-white ease-in-out duration-300'
                    >Cancel</div>
                </div>
            </form>
  )
}

export default TaskPreviewCreateTask