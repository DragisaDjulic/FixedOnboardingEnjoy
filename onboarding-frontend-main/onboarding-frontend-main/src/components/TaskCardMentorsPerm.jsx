import React from 'react'
import { FormControlLabel, FormGroup, Switch } from '@mui/material'
import { useState } from 'react'
import Avatar from './Avatar';
import { useEffect } from 'react';
import UserInfo from './user_info';

function TaskCardMentorsPerm({modsinfo, onUpdate}) {


    const [actions, setActions] = useState(modsinfo.value?.actions);


    const email = modsinfo?.value?.email;
    
    const perms = {email, actions};

    // console.log(perms);

    const editActions = (e, action) => {
      if(e.target.checked) {
        setActions(prev => [...prev, action]);
      }else {
        setActions(prev => prev.filter(act => act != action));
      }
    }

  return (
    <>
        <FormGroup>
            <Avatar name={modsinfo}/>
            <FormControlLabel control={<Switch
                              checked={actions?.includes("create_task")}
                              onChange={(e) => {
                                // setCanDell(e.target.checked);
                                editActions(e, "create_task");
                                onUpdate({label: modsinfo.label, value: {...perms, actions: e.target.checked ? [...actions, "create_task"] : actions.filter(act => act != "create_task"), avatar: modsinfo.value.avatar}})
                              }}
                                        />} 
                                label="Can Add" 
                              />                
            <FormControlLabel control={<Switch
                              checked={actions?.includes("edit_task")}
                              onChange={(e) => {
                                // setCanEdit(e.target.checked);
                                editActions(e, "edit_task");
                                onUpdate({label: modsinfo.label, value: {...perms, actions: e.target.checked ? [...actions, "edit_task"] : actions.filter(act => act != "edit_task"), avatar: modsinfo.value.avatar}})
                              }}
                                        />} 
                                label="Can Edit" 
                              />
            <FormControlLabel control={<Switch
                              checked={actions?.includes("delete_task")}
                              onChange={(e) => {
                                // setCanDell(e.target.checked);
                                editActions(e, "delete_task");
                                onUpdate({label: modsinfo.label, value: {...perms, actions: e.target.checked ? [...actions, "delete_task"] : actions.filter(act => act != "delete_task"), avatar: modsinfo.value.avatar}})
                              }}
                                        />} 
                                label="Can Delete" 
                              />
                          
        </FormGroup>
    </>
    
  )
}

export default TaskCardMentorsPerm