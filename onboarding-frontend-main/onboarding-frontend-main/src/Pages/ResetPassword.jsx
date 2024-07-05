import React from 'react';
import {useState} from 'react';
import BrandHeading from '../components/BrandHeading';
import BackroundgImage from '../images/bglog.png'
import { validPassword } from '../service/Regex';
import { useNavigate, useParams } from 'react-router'
import { resetPassword } from '../service/userApi';
import LodingWrapper from '../components/LodingWrapper';
const ResetPassword = () => {
    const [password, setPassword] = useState('');
    const [passwordConf, setPasswordConf] = useState('');
    const [pwdConfError, setPwdConfError] = useState(false);
    const [pwdError, setPwdError] = useState(false);
    const [PasswordMatchErr, setPasswordMatchErr] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState("");
    const navigate=useNavigate();
    const {token} = useParams();
     /**function that is called from the onSubmit event from the button in the form to hanndle our reset password request */
    const hanndleSubmit=(event)=>
    {
        event.preventDefault();
        setMessage("");
        /**if password and confrim password are invalid,based on the Regex service, set the error state to true to show the error messages */
        if (!(validPassword.test(password) && validPassword.test(passwordConf))) {
            setPwdError(true);
            setPwdConfError(true);
        }
        /**if the password and confirm password do not match set the error state to true to show the error message */
        if (!(password===passwordConf)) {
            setPasswordMatchErr(true);   
        }
       /**if the password and confirm password are valid and they match procead with the reset */
         if((password===passwordConf) && (validPassword.test(password) && validPassword.test(passwordConf)))
        {   

            setPasswordMatchErr(false);
            setPwdError(false);
            setPwdConfError(false);
            let reset = {token, password};
            setIsLoading(true);
            /**resetPassword from userApi.js,sending the reset request*/
            resetPassword(reset)
            .then(res =>{
                /**isLoding is set to false for the spinner to stop showing */
                setMessage(res);
                console.log(res);
                /**Styling the message bassed on response */
                setIsLoading(false);        
                })
                /**catch any errors */
            .catch(err => {
                setIsLoading(true)
                setMessage(err.response.data.message);
                setIsLoading(false);
            })
            .finally(()=>{
            setPassword('');
            setPasswordConf('');
            });
        }
    }
    return ( 
        <>
           {
           isLoading?(
                <LodingWrapper loading={isLoading} />
           ):(
            <div className="relative w-full h-screen  bg-zinc-500/10" style={{backgroundImage:`url(${BackroundgImage})`}}>
                <div className=" flex justify-center items-center h-full">

                    <form className="max-w-[400px] w-full mx-auto  bg-white p-8 rounded-xl z-0 " onSubmit={hanndleSubmit}>
                        <BrandHeading title={"Set your new password"}></BrandHeading>
                        <div className=" flex flex-col mb-4">
                            <label className="text-black p-2" >Password:</label>
                            <input className="border relative bg-gray-100 p-2 rounded-lg" type="password"
                                placeholder='Password' value={password} onChange={(e)=> setPassword(e.target.value)} required/>

                        </div>
                        <div className=" flex flex-col mb-4">
                            
                            <label className="text-black p-2" >Confirm Password:</label>
                                <input className="border relative bg-gray-100 p-2 rounded-lg" type="password"
                                placeholder='Confirm Password' value={passwordConf} onChange={(e)=> setPasswordConf(e.target.value)} required/>
                        </div>
                                                     {/**conditional rendering error messages */}
                        {(pwdError&&pwdConfError)&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">Bouth input fileds must have at least 5 characters</div>}
                        {PasswordMatchErr&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">Password does not match!</div>}
                        {(message==="Password changed successfully!")&&<div className="relative text-green-600 text-center mt-4 text-lg p-2">Password has been reset!</div>}
                                <button className='w-full py-3 mt-8  bg-black hover:bg-black/90 relative text-white rounded-xl  disabled:bg-gray-500'
                                    disabled={password===''} type="submit"  >Confirm</button>
                                <button className='w-full py-3 mt-8  bg-black hover:bg-black/90 relative text-white rounded-xl ' 
                                 type='button' onClick={()=>navigate('/')}>Go back to Login</button>
                    </form>
                </div>
            </div>
           )
           }        
        </>
     );
}
export default ResetPassword;