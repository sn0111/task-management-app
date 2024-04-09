import { useEffect, useState } from "react";

export const Navbar = ()=>{
    const [role, setRole] = useState("")


    useEffect(() => {   
        setRole(localStorage.getItem("role")) 
    },[]);

    return <nav class="navbar navbar-expand-lg custom-navbar">
    <div className="container-fluid">
    <a class="navbar-brand" href="#">Task Manager</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home</a>
            </li>
            {role === "ADMIN" && <li class="nav-item">
                <a class="nav-link" href="/users">Users</a>
            </li>}
            <li class="nav-item">
                <a class="nav-link" href="#">Profile</a>
            </li>
        </ul>
    </div>
    </div>
</nav>
}