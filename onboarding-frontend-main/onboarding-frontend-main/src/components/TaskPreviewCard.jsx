import React from 'react'

import { useState, useEffect, useContext } from "react";
import {userContext} from "../App"
import TaskPreviewShowTask from './TaskPreviewShowTask';
import TaskPreviewCreateTask from './TaskPreviewCreateTask';
import NotificationAlertType from './NotificationAlertType';

function TaskPreviewCard({task, index, onDelete, onSaveEdit, addTaskGroup, action, ondId}) {

    const bgColor = task.color;
    const [edit, setEdit] = useState(false);
    const [editedTask, setEditedTask] = useState({...task, children: task.children.slice()});
    const [newChild, setNewChild] = useState("")
    const {currentUser, setCurrentUser}=useContext(userContext);

    const [editSelectedTask, setEditSelectedTask] = useState({
        onboarding: ondId,
        name: "",
        description: "",
        children: []

    })

    useEffect(() => {

        setEditedTask({...task, children: task.children.slice()});

    }, [task])
   
    const saveEditTask = (e) => {
        e.preventDefault();
        
        onSaveEdit(index, editSelectedTask);
        setEditedTask(editSelectedTask);
        setEdit(false);
        setEditSelectedTask({
            name: "",
            description: "",
            children: []
        });
    }

    const cancelEdit = (e) => {
        setEdit(false);
        setEditedTask(task);
        onSaveEdit(index, task);
    }

    const EditGroupTask = () => {
        setEdit(true);
        setEditSelectedTask({
            id: (editedTask?.id && editedTask.id) || null,  
            name: (editedTask && editedTask.name) || task?.name,
            description: (editedTask && editedTask.description) || task?.description,
            children: (editedTask && editedTask.children) || task?.children.slice(),
            color:"bg-yellow-50"
        });
    }

    const dellSubTask = (e, i) => {
        e.preventDefault();
        let newList = editedTask.children;
        newList.splice(i, 1);
        setEditedTask(prev => ({...prev, children: newList}));
    }

    const setProperty = (e) => {
        setEditSelectedTask(prev => ({...prev, [e.target.name]: e.target.value}));
    }

  return (
    <>
        {!(edit) ?
        /* RENDER TASK PREVIEW */
        (<TaskPreviewShowTask editedTask={editedTask} action={action} EditGroupTask={EditGroupTask}
                            onDelete={onDelete} index={index} task={task}
                            bgColor={bgColor} currentUser={currentUser}/>)
        : 
        /* ELSE RENDER FOR EDIT STAGED TAKS */
        (<TaskPreviewCreateTask setEditSelectedTask={setEditSelectedTask} task={task} newChild={newChild}
                            setNewChild={setNewChild} editSelectedTask={editSelectedTask} saveEditTask={saveEditTask}
                            setProperty={setProperty} cancelEdit={cancelEdit} dellSubTask={dellSubTask} />)
        }
    </>

  )
}

export default TaskPreviewCard