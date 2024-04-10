import { useContext, useState } from "react"
import { login, signup } from "./task-api-service"
import { useNavigate } from "react-router-dom"
import { AuthContext } from "../authContext"

export const Login = ()=>{
    const [newAccount,setNewAccount] = useState(false)
    const [userName,setUserName] = useState("")
    const [userEmail,setUserEmail] = useState("")
    const [password,setPassword] = useState("")

    const navigate = useNavigate()

    const { setLoginContext } = useContext(AuthContext);
    

    const submit = (e)=>{
        e.preventDefault()
        const body = {
          userEmail,
          userName,
          password
      }
        if(newAccount){
          signup(body)
          .then(res=>{
            if(res!=null){
                var role = res.data.data
                localStorage.setItem("role",role)
                var token = btoa(userEmail+":"+password)
                localStorage.setItem("token",token)
                setLoginContext(res.data.data,token);
                navigate("/task-manager-app/home")
            }
        })
        .catch(err=>console.log(err))
        }else{
            login(body)
            .then(res=>{
                if(res!=null){
                    var role = res.data.data
                    localStorage.setItem("role",role)
                    var token = btoa(userEmail+":"+password)
                    localStorage.setItem("token",token)
                    setLoginContext(res.data.data,token);
                    navigate("/task-manager-app/home")
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