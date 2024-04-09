import { useState } from "react"
import { login } from "./task-api-service"
import { useNavigate } from "react-router-dom"

export const Login = ()=>{
    const [newAccount,setNewAccount] = useState(false)
    const [userName,setUserName] = useState("")
    const [userEmail,setUserEmail] = useState("")
    const [password,setPassword] = useState("")

    const navigate = useNavigate()
    

    const submit = (e)=>{
        e.preventDefault()
        if(newAccount){

        }else{
            const body = {
                userEmail,
                password
            }
            login(body)
            .then(res=>{
                if(res!=null){
                    localStorage.setItem("role",res.data.data)
                    var token = userEmail+":"+password
                    console.log(token)
                    var encode = btoa(token);
                    localStorage.setItem("token",encode)
                    navigate("/home")
                }
            })
            .catch(err=>console.log(err))
        }
    }

    return <div className='fluid-container home'>
    <div className='row'>
      <div className='col-sm-6'>
        <div className='header'>
          TASK MANAGEMENT 
        </div>
        <div className='header-desc pt-4'>
          Web Application
        </div>
      </div>
      <div className='col-sm-6'>
        <div className='right'>
          <div className='right-header'>{newAccount ? "Register to create your tasks":"Login to access your tasks"}</div>
          <form className='pt-3'>
            <div class="form-group">
              <label for="exampleInputEmail1">Email address</label>
              <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email"
              value={userEmail}
              onChange={(e)=>setUserEmail(e.target.value)}
              />
            </div>
            {newAccount && <div class="form-group pt-2">
              <label for="exampleInputEmail1">Username</label>
              <input type="text" class="form-control" id="username" placeholder="Enter username" 
                value={userName}
                onChange={(e)=>setUserName(e.target.value)}
              />
            </div>}
            <div class="form-group pt-2">
              <label for="exampleInputPassword1">Password</label>
              <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"
              value={password}
              onChange={(e)=>setPassword(e.target.value)}
              />
            </div>
            {!newAccount && <div>Click <span className="link" onClick={()=>setNewAccount(true)}>here</span> to register</div>}
            {newAccount && <div>Click <span className="link" onClick={()=>setNewAccount(false)}>here</span> to login</div>}
            <div className='pt-2'><button type="submit" class="btn btn-primary" onClick={submit}>Submit</button></div>
          </form>
        </div>
      </div>
    </div>
  </div>
}