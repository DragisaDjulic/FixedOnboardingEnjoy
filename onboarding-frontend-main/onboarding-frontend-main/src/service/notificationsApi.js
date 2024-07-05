import axios from "axios";

const config ={
    url: "/notifications"
}

export const getNotifications = () => {
    return axios.get(config.url)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const readNotification = (id) => {
    return axios.get(`${config.url}/${id}/opened`)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const openAllNotifications = (id) => {
    return axios.get(`${config.url}/${id}/openedall`)
        .then(res => res.data)
        .catch(err => console.log(err));
}