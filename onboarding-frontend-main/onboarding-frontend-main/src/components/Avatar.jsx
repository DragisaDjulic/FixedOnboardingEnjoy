import React, { useState } from "react";
import styles from "./Avatar.module.css"

function Avatar({name}){

    console.log(name);
    return(
        <div className="flex items-center">
            <div className="flex-shrink-0">
                {name?.value?.avatar ? <img className="h-10 w-10 object-cover rounded-full" src={name?.value?.avatar} alt="user"/> : <img className="h-10 w-10 object-cover rounded-full" src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png?20150327203541" alt="user"/>}
                
            </div>
            <div className="block">
                <div className="ml-5 flex items-baseline space-x-4 ">
                  <p className='text-black'>{name?.label}</p>
                </div>
            </div>
        </div>
    )


}

export default Avatar;