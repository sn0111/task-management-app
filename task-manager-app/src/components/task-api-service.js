import axios from "axios";

const userUrl = "/users"
const taskUrl = "/tasks"

const basicAuth ="Basic "+ localStorage.getItem("token")


export const login =async (obj)=>{
    const request = await axios.post(`${userUrl}/login`,obj)
    return request;
}

export const adminUsers =async ()=>{
    const request = await axios.get(`${userUrl}`, {headers:{"Authorization":basicAuth}})
    return request;
}


export const userTasks =async (userId)=>{
    const request = await axios.get(`${taskUrl}?userId=${userId}`, {headers:{"Authorization":basicAuth}})
    return request;
}

export const addUserTask =async (obj)=>{
    const request = await axios.post(`${taskUrl}/add`, obj, {headers:{"Authorization":basicAuth}})
    return request;
}

export const updateUserTask =async (obj)=>{
    const request = await axios.put(`${taskUrl}/update`,obj,  {headers:{"Authorization":basicAuth}})
    return request;
}

export const deleteTask =async (taskId)=>{
    const request = await axios.delete(`${taskUrl}/delete/${taskId}`, {headers:{"Authorization":basicAuth}})
    return request;
}