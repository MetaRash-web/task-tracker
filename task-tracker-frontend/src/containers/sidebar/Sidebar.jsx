import 'react';
import UserModal from "../userContainer/UserModal.jsx";
import './sidebar.css'
import {useState} from "react";

export default function Sidebar() {
    const [isModalVisible, setIsModalVisible] = useState(false);
    return (
        <aside className="sidebar">
            <div className="avatar" onClick={() => setIsModalVisible(!isModalVisible)}></div>
            <hr/>
            <button>Добавить задачу</button>
            {isModalVisible && <UserModal isModalVisible={isModalVisible} />}
        </aside>
    );
}