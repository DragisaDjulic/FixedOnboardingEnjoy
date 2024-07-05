import React from 'react'
import { Routes, Route, Link } from "react-router-dom";
import Login from "./Pages/Login";
import Dashboard from './Pages/Dashboard';
import NF from './Pages/NF';
import Onboarding from './Pages/Onboardings';
import Profile from './Pages/Profile';
import Templates from './Pages/Templates';
import UPS from './Pages/UPS';
import Users from './Pages/Users';
import ForgotPasswordForm from './Pages/ForgotPasswordForm';
import ResetPassword from './Pages/ResetPassword';

function RoutesCont() {
  return (
    <Routes>
        <Route path='/' element={<Login />}></Route>
        <Route path='/dashboard' element={<Dashboard />}></Route>
        <Route path='/nf' element={<NF />}></Route>
        <Route path='/onboarding/:id' element={<Onboarding />}></Route>
        <Route path='/profile/:id' element={<Profile />}></Route>
        <Route path='/templates' element={<Templates />}></Route>
        <Route path='/ups' element={<UPS />}></Route>
        <Route path='/users' element={<Users />}></Route>
        <Route path='/forgotpassword' element={<ForgotPasswordForm/>}></Route>
        <Route path='/reset/:token' element={<ResetPassword/>}></Route>
        <Route path='*' element={<NF />}></Route>
    </Routes>
  )
}

export default RoutesCont