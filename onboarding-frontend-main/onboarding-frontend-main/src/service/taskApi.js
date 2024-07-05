import axios from "axios";


const config = {
    url: "/tasks",
    options: {}
};

export const getTasks = () => {
    return axios.get(config.url)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getTask = (id) => {
    return axios.get(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const addTask = (task) => {
    return axios.post(config.url, task)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const editTask = (id, task) => {
    return axios.put(`${config.url}/${id}`, task)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const deleteTask = (id) => {
    return axios.delete(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const doTask = (id) => {
    return axios.get(`${config.url}/${id}/done`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getHelp = (id) => {
    return axios.get(`${config.url}/${id}/help`)
        .then(res => res.data)
        .catch(err => console.log(err));
}