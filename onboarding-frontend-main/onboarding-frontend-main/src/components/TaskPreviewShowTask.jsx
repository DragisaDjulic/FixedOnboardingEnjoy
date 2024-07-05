import React, { useEffect, useState } from 'react'
import {AiFillCloseCircle, AiOutlineEdit} from "react-icons/ai"

function TaskPreviewShowTask({ editedTask, action, EditGroupTask, onDelete, index, task, bgColor, currentUser}) {

  const[modal, setModal] = useState(false);

  const toggleModal = () => {
      setModal(!modal);
    };

  return (

    <>
      <div className={`flex flex-col self-center justify-center p-3 mb-10 w-[100%] rounded-lg shadow-md h-auto ${bgColor} text-black
                      `}>
              <div className='flex flex-col'>
                      <h2 className='font-bold text-left text-xl mb-4'>{(editedTask && editedTask.name) || task?.name}</h2>
                      <p className='text-justify mb-5'>{(editedTask && editedTask.description) || task?.description}</p>
                  { editedTask?.children?.map((subTask, i) => (
                      <p className='text-left' key={i}>{subTask?.description}</p>
                  ))}
              </div>
              <div className='w-[100%] flex justify-end space-x-5'>

                  {(action?.includes("edit_task") || (currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN"))) &&
                      <AiOutlineEdit size="40px" 
                                      cursor="pointer"
                                      color='rgba(0, 0, 0)' 
                                      onClick={() => EditGroupTask()}/>
                  }
                  {(action?.includes("delete_task") || (currentUser && (currentUser?.role.permission.name == "ROLE_ADMIN"))) && 
                      <AiFillCloseCircle size="40px" 
                                      cursor="pointer"
                                      color='rgba(0, 0, 0)' 
                                      onClick={toggleModal}
                                      />
                  }
          </div>
      </div>

      {modal && (
          <div className="modal text-black">
              <div onClick={toggleModal} className="overlay"></div>
              <div className="modal-content">
                  <h2>ARE YOU SURE YOU WANT TO DELETE THIS TASK?</h2>
                  <div className='mt-10 flex flex-row space-x-8'>
                  <button onClick={e => {onDelete(e, index)}} className="text-red-600">
                      DELETE
                  </button>
                  <button className="close-modal" onClick={toggleModal}>
                      CLOSE
                  </button>
                  </div>

              </div>
          </div>
      )}
    </>

    
  )
}

export default TaskPreviewShowTask