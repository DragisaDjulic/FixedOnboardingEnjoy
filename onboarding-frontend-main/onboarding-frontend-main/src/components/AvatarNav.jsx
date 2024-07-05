import React, { useState } from "react";
import styles from "./Avatar.module.css"

function AvatarNav({avat}){


    return(
        <div className="flex items-center">
            <div className="flex-shrink-0">
                <img
                  className="h-10 w-10 rounded-full"
                  src="https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png?20150327203541"
                  alt="user"
                />
            </div>
            <div className="block">
                <div className="ml-5 flex items-baseline space-x-4">
                  <p className={styles.usernametxt}>{avat?.firstName} {avat?.lastName}</p>
                </div>
            </div>
        </div>
    )


}

export default AvatarNav;