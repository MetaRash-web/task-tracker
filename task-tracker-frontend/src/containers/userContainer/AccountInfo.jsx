import PropTypes from "prop-types";

export default function AccountInfo({ user, onLogout }){
  return (
    <div className="account-info" id="account-info">
      <h2>Привет, <span id="username">{user?.username}</span>!</h2>
      <p>Email: <span id="user-email">{user?.email}</span></p>
      <button id="logout-button" onClick={onLogout}>Выйти</button>
    </div>
  );
};

AccountInfo.propTypes = {
    onLogout: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired
};