import 'react';
import { useState } from "react";
import "./MainContent.css";
import TaskList from "./task/TaskList.jsx"
import FilterModal from "./filter/FilterModal.jsx"
import Topbar from "./topbar/Topbar.jsx";

export default function MainContent() {

    return (
        <div className="main-content">
            <Topbar />
            <div className="content">
                <TaskList />
            </div>
        </div>
    );
}