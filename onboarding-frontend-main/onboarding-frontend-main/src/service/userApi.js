import axios from "axios";


const config = {
    url: "/users"
};

export const getUsers = () => {
    return axios.get(config.url)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getUser = (id) => {
    return axios.get(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const addUser = (user) => {
    return axios.post(config.url, user)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const editUser = (id, user) => {
    return axios.put(`${config.url}/${id}`, user)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const deleteUser = (id) => {
    return axios.delete(`${config.url}/${id}`)
        .then(res => res.data)
}

export const loginUser = (credentials) => {
    return axios.post(`${config.url}/login`, credentials)
        .then(res => res.data)
}

export const getCurrentUser = () => {
    return axios.get(`${config.url}/getcurrent`)
        .then(res => res.data)
}

export const resetPassword = (reset) => {
    return axios.post(`${config.url}/reset`, reset)
        .then(res => res.data);
}