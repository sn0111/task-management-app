import { useEffect, useState } from "react"
import { Navbar } from "./navbar"
import { adminUsers } from "./task-api-service"

export const Users = ()=>{
    const [users,setUsers] = useState([])
    useEffect(()=>{
        adminUsers()
        .then(res=>{
            if(res!=null){
                setUsers(res.data.data)
            }
        })
    },[])
    return <>
        <Navbar/>
        <div className="container">
        <div className="task-header pt-4 text-center">All users in the task manager</div>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Useremail</th>
                        <th scope="col">Username</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user,key)=><tr key={key}>
                        <th scope="row">{key+1}</th>
                        <td>{user.userEmail}</td>
                        <td>{user.userName}</td>
                    </tr>)}
                </tbody>
            </table>
        </div>
    </>
}