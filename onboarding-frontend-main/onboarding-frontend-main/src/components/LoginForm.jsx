import React, {useState, useContext} from "react";
import BrandHeading from "./BrandHeading";
import ForgotPassword from "./ForgotPassword";
import { validEmail,validPassword } from "../service/Regex";
import {loginUser} from "../service/userApi";
import {userContext} from "../App"
import LodingWrapper from "./LodingWrapper";

export default function LogInForm()
{
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [emailErr, setEmailErr] = useState(false);
    const [pwdError, setPwdError] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState("");
    const {setUserToken} = useContext(userContext);

    const hanndleSubmit= (e) =>{
   e.preventDefault();
   /**Since there is only error message response to display on this component,it will be set to one style*/
   /**if email is ivalid based on Regex service,set the error state to true to show the message */
    if (!validEmail.test(email)) {
        setEmailErr(true);
     }
     /**if password is ivalid based on Regex service,set the error state to true and show the message */
     if (!validPassword.test(password)) {
        setPwdError(true);
     }
     /**if the email and password are valid,continue the request  */
     if(validEmail.test(email) && validPassword.test(password))
     {
        setMessage("");
        const logedUser = {username:email, password};
        /**loginUser from userApi.js,sending the request to the server where our database is */ 
        setIsLoading(true);
        loginUser(logedUser)
            .then(res => {

                localStorage.setItem("accessToken", res);
                setUserToken(res);
                /**isLoding is set to false for the spinner to stop showing */
                setIsLoading(false);
            })
            /**catch any errors */
            .catch(err => {
                setIsLoading(true);
                setMessage(err.response.data.message);
                setIsLoading(false)
                
            })
            .finally(()=>{
            setEmail("");
            setPassword("");
            setEmailErr(false);
            setPwdError(false);
            });
    }
}

    return(
            <>
            {
                isLoading?(
                    <LodingWrapper loading={isLoading}/>
                ):
                (
            <div className=" flex justify-center items-center h-full">
        
                <form className="max-w-[400px] w-full mx-auto  bg-white p-8 rounded-xl z-0 " onSubmit={hanndleSubmit} >
                    <BrandHeading title={"Sign In"}></BrandHeading>
                    <div className=" flex flex-col mb-4">
                        <label className="text-black p-2" >Email:</label>
                            <input className="border relative bg-gray-100 p-2 rounded-lg" type="text"
                            placeholder='Email' value={email} onChange={(e)=> setEmail(e.target.value)} required/>
                    </div>
                    <div className=" flex flex-col mb-4">
                        <label className="text-black p-2" >Password:</label>
                            <input className="border relative bg-gray-100 p-2 rounded-lg " type="password" 
                                placeholder="Password" value={password} onChange={(e)=> setPassword(e.target.value)} required />
                    </div>
                        {emailErr&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">"Email must contain @enjoying.rs"</div>}
                        {pwdError&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">Password must contain at least 5 characters</div>}
                        {(message==="Invalid credentials!")&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">Wrong Password!</div>}
                        {(message==="User not found!")&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">User with this email does not exist!</div>}
                    <button className='w-full py-3 mt-8  bg-black hover:bg-black/90 relative text-white rounded-xl  disabled:bg-gray-500'
                            disabled={email==='' &&  password===''} type="submit"  >Sign In</button>
                        <ForgotPassword />
                </form>
            </div>
                )
            }
            </>
    )
}