import React from "react";
import LogInForm from "./LoginForm";

import BackroundgImage from '../images/bglog.png'

export default function LogIn()
{   
    
    return(
        <div className="relative w-full h-screen  bg-zinc-500/10" style={{backgroundImage:`url(${BackroundgImage})`}}>
            <LogInForm/>
        </div>

    )
}