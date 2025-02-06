import {useState} from "react";
import AuthForms from "./AuthForms.jsx";
import AccountInfo from "./AccountInfo.jsx"
import "./UserModal.css"
import * as prototype from "prop-types";

export default function UserModal({isModalVisible}) {
    const [isLoginVisible, setIsLoginVisible] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(true);
    const [user, setUser] = useState(null);

    const handleSignup = (userData) => {
        // Логика регистрации
        setUser(userData);
        setIsModalOpen(false);
    };

    const handleLogin = (userData) => {
        // Логика входа
        setUser(userData);
        setIsModalOpen(false);
    };

    const handleLogout = () => {
        setUser(null);
        setIsModalOpen(true);
    };

    return (
        isModalVisible ?
        <div className="user-modal" id="user-modal">
            {isModalOpen ? (
                <AuthForms
                    isLoginVisible={isLoginVisible}
                    toggleForm={() => setIsLoginVisible(!isLoginVisible)}
                    onSignup={handleSignup}
                    onLogin={handleLogin}
                />
            ) : (
                <AccountInfo user={user} onLogout={handleLogout} />
            )}
        </div> : null
    );
};

UserModal.propTypes = {
    isModalVisible: prototype.bool.isRequired
}