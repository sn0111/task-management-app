import { useContext, useEffect, useState } from "react"
import deleteIcon from "../svg/delete.svg"
import updateIcon from "../svg/update.svg"
import { addUserTask, deleteTask, updateUserTask, userTasks } from "./task-api-service"
import { Navbar } from "./navbar"
import { AuthContext } from "../authContext"

export const Home = ()=>{

    const [tasks, setTasks] = useState([])
    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")
    const [status, setStatus] = useState("")
    const [taskId, setTaskId] = useState("")
    const [edit, setEdit] = useState(false)
    const [search, setSearch] = useState("")

    const { userId } = useContext(AuthContext);



    useEffect(() => {   
        getUserTasks()
    },[]);

    const getUserTasks = ()=>{
        userTasks(localStorage.getItem("userId"))
        .then(res=>{
            if(res!=null){
                setTasks(res.data.data)
            }
        })
        .catch(err=>console.log(err))
    }

    const saveUserTask = (e)=>{
        e.preventDefault()
        const body ={
            title,
            description,
            status,
            user:{
                userId:1,
            }
        }
        addUserTask(body)
        .then(res=>{
            if(res!=null){
                // setTasks(res.data.data)
                getUserTasks()
            }
        })
        .catch(err=>console.log(err))
    }

    const updateTask = (e)=>{
        e.preventDefault()
        const body ={
            taskId,
            title,
            description,
            status
        }
        updateUserTask(body)
        .then(res=>{
            if(res!=null){
                // setTasks(res.data.data)
                getUserTasks()
            }
        })
        .catch(err=>console.log(err))
    }

    const deleteUserTask = (taskId)=>{
    
        deleteTask(taskId)
        .then(res=>{
            if(res!=null){
                // setTasks(res.data.data)
                getUserTasks()
            }
        })
        .catch(err=>console.log(err))
    }

    const editUserTask =(task)=>{
        setEdit(true)
        setTaskId(task.taskId)
        setTitle(task.title)
        setDescription(task.description)
        if(task.status==="To Do"){
            setStatus("TODO")
        }else if(task.status==="In Pending"){
            setStatus("IN_PENDING")
        }else if(task.status==="Done"){
            setStatus("DONE")
        }
    }

    const clear = ()=>{
        setDescription("")
        setTitle("")
        setStatus("")
        setEdit("")
    }

    const filterTasks = ()=>{
        return search==="" ?tasks :tasks.filter(o=>o.status===search)
    }

    return <>
        <Navbar/>
        <div className="row">
            <div className="col-lg-6">
                <div className="container">
                    <div className="task-header pt-4 text-center">Create your task</div>
                    <form className='pt-2'>
                        <div class="form-group">
                        <label className="pb-1">Title</label>
                        <input type="text" class="form-control" id="title" placeholder="Enter title"
                        value={title}
                        onChange={(e)=>setTitle(e.target.value)}
                        />
                        </div>
                        <div class="form-group pt-2">
                        <label className="pb-1">Description</label>
                        <textarea className="form-control"
                            value={description}
                            onChange={(e)=>setDescription(e.target.value)}
                            />
                        </div>
                        <div class="form-group pt-2">
                        <label for="status" className="pb-1">Status</label>
                        <select class="form-control custom-select"
                            value={status}
                            onChange={(e)=>setStatus(e.target.value)}
                        >
                            <option value="">Select</option>
                            <option value="TODO">To Do</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="DONE">Done</option>
                        </select>
                        </div>
                        <div className='pt-3 text-center buttons'>
                            <button type="submit" class="btn btn-light" onClick={clear}>Clear</button>
                            <button type="submit" class="btn btn-primary" onClick={!edit ? saveUserTask:updateTask}>{edit ? "Update":"Save"}</button>
                        </div>
                    </form>
                </div>
            </div>
            <div className="col-lg-6">
                <div className="container pt-4 tasks">
                    <div class="card">
                        <div class="card-header">
                            <ul class="nav nav-tabs card-header-tabs" id="tab">
                                <li class="nav-item">
                                    <a class="nav-link active" data-bs-toggle="tab" onClick={()=>setSearch("")} href="#">All</a>
                                </li>
                                <li class="nav-item" data-bs-toggle="tab" onClick={()=>setSearch("To Do")}>
                                    <a class="nav-link" href="#">To Do</a>
                                </li>
                                <li class="nav-item" data-bs-toggle="tab"  onClick={()=>setSearch("In Progress")}>
                                    <a class="nav-link" href="#">In Progress</a>
                                </li>
                                <li class="nav-item" data-bs-toggle="tab" onClick={()=>setSearch("Done")}>
                                    <a class="nav-link" href="#" onClick={()=>setSearch("Done")}>Done</a>
                                </li>
                            </ul>
                        </div>
                        <div class="card-body">
                            {filterTasks().map((task,key)=><div key={key} class="col-sm-12 pb-2">
                                <div class="card">
                                    <div class="card-body">
                                        <div className="card-title d-flex justify-content-between">
                                            <div>{task.title} <span class="badge rounded-pill bg-primary">{task.status}</span></div>
                                            <div>
                                                <img className="img-icon" src={updateIcon} onClick={()=>editUserTask(task)}/>
                                                <img className="img-icon" src={deleteIcon} onClick={()=>deleteUserTask(task.taskId)}/>
                                            </div>
                                        </div>
                                        <p class="card-text">{task.description}</p>
                                    </div>
                                </div>
                            </div>)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
}