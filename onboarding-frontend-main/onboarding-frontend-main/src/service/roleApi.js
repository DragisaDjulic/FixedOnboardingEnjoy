import axios from "axios";


const config = {
    url: "/roles",
    options: {}
};

export const getRoles = () => {
    return axios.get(config.url)
        .then(res => res.data)
        .catch(err => console.log(err));
}

export const getRole = (id) => {
    return axios.get(`${config.url}/${id}`)
        .then(res => res.data)
        .catch(err => console.log(err));
}