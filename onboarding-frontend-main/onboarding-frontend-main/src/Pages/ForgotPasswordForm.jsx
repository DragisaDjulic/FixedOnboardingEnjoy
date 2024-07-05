import {useState} from 'react';
import BrandHeading from '../components/BrandHeading';
import BackroundgImage from '../images/bglog.png'
import { validEmail } from '../service/Regex';
import { resetPassword } from '../service/userApi';
import LodingWrapper from '../components/LodingWrapper'
import { useNavigate } from 'react-router';
const ForgotPasswordForm = () => {
    
    const [email, setEmail] = useState('');
    const [emailErr, setEmailErr] = useState(false);
    const [message, setMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate=useNavigate();
   /**function that is called from the onSubmit event from the button in the form to hanndle our reset password request */
    const hanndleSubmit=(event)=>
    {
        event.preventDefault();
        /**function that checks if email is invalid based on Regex service and sets emain error to true */
        if (!validEmail.test(email)) {
            setEmailErr(true);
         }
         /**if the email is valid procced with the password reset request */
         if(validEmail.test(email))
         {
            const reset = {email};
            setEmailErr(false);
            /**resetPassword from userApi.js,sending the request to the server where our database is,checks if the email exist in the database for the reset and returns a response */
            setIsLoading(true);
             resetPassword(reset)
             .then(res =>{
                    setMessage(res);
                    /**isLoding is set to false for the spinner to stop showing */
                    setIsLoading(false)
        }) 
        /**catch any errors */
        .catch(err =>    
            {
              setIsLoading(true)
              setMessage(err.response.data.message);
              setIsLoading(false);
            })
           .finally(()=>{
            setEmail("");
           });
         }
    }
    return (  
        <>
            {
                 isLoading?(
                    <LodingWrapper loading={isLoading}/>
                 ):(
                    <div className="relative w-full h-screen  bg-zinc-500/10 " style={{backgroundImage:`url(${BackroundgImage})`}}>
                 <div className=" flex justify-center items-center h-full">
 
                     <form className="max-w-[400px] w-full mx-auto  bg-white p-8 rounded-xl z-0 " onSubmit={hanndleSubmit}>
                             <BrandHeading title={"Send a request"}></BrandHeading>
                         <div className=" flex flex-col mb-4">
                             <label className="text-black p-2" >Email:</label>
                                 <input className="border relative bg-gray-100 p-2 rounded-lg" type="text"
                                 placeholder='Email' value={email} onChange={(e)=> setEmail(e.target.value)} required/>
                         </div>
                                                    {/**conditional rendering error messages */}
                        {emailErr&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">"Email must contain @enjoying.rs"</div>}
                        {(message==="User not found!")&&<div className="relative text-red-400/90 text-center mt-4 text-lg p-2">User with this email does not exist!</div>}
                        {(message==="Reset email sent")&&<div className="relative text-green-600 text-center mt-4 text-lg p-2">Email has been sent!</div>}
                         <button className='w-full py-3 mt-8  bg-black hover:bg-black/90 relative text-white rounded-xl  disabled:bg-gray-500'
                                 disabled={email===''} type="submit" >Send Request</button>
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
export default ForgotPasswordForm;