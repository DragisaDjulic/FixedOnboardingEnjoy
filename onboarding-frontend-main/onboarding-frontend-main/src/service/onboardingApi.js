import axios from "axios";


const config = {
    url: "/onboardings"
};

export const getOnboardings = () => {
    return axios.get(config.url)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getOnboarding = (id) => {
    return axios.get(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const addOnboarding = (onboarding) => {
    return axios.post(config.url, onboarding)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const editOnboarding = (id, onboarding) => {
    return axios.put(`${config.url}/${id}`, onboarding)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const deleteOnboarding = (id) => {
    return axios.delete(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const addUsersToOnboarding = (id, userIds) => {
    return axios.post(`${config.url}/${id}/addusers`, userIds)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const addTasksToOnboarding = (id, tasks) => {
    return axios.post(`${config.url}/${id}/addtasks`, tasks)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getUserOnboardings = (id) => {
    return axios.get(`${config.url}/user/${id}`)
    .then(res => res.data)
    .catch(err => console.log(err));
}



