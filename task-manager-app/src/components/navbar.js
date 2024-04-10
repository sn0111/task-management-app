import { Link } from "react-router-dom";
import logoutIcon from "../svg/logout.svg"
import { useContext } from "react";
import { AuthContext } from "../authContext";

export const Navbar = ()=>{

    const {setLogoutContext} = useContext(AuthContext)

    return <nav class="navbar navbar-expand-lg custom-navbar">
    <div className="container-fluid">
    <a class="navbar-brand" href="#">Task Manager</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse d-flex justify-content-between" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                {/* <a class="nav-link" href="#">Home</a> */}
                <Link to="/task-manager-app/home" className="nav-link">Home</Link>
            </li>
            {localStorage.getItem("role") === "ADMIN" && <li class="nav-item">
                {/* <a class="nav-link" href="/users">Users</a> */}
                <Link to="/task-manager-app/users" className="nav-link">Users</Link>
            </li>}
            <li class="nav-item">
                {/* <a class="nav-link" href="#">Profile</a> */}
                <Link to="/task-manager-app/profile" className="nav-link">Profile</Link>
            </li>
        </ul>
        <div className="text-center">
            <img src={logoutIcon} className="img-icon" onClick={setLogoutContext}/>
        </div>
    </div>
    </div>
</nav>
}