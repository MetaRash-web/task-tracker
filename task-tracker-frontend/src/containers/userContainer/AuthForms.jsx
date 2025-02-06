import PropTypes from "prop-types";

export default function AuthForms({isLoginVisible, toggleForm, onSignup, onLogin}) {
    const handleSignupSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const userData = {
            username: formData.get('username'),
            email: formData.get('email'),
            password: formData.get('password')
        };
        onSignup(userData);
    };

    const handleLoginSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const userData = {
            email: formData.get('email'),
            password: formData.get('password')
        };
        onLogin(userData);
    };

    return (
        <div className="form-container" id="form-container">
            <div className={`signup ${!isLoginVisible ? 'active' : ''}`}>
                <form id="signup-form" onSubmit={handleSignupSubmit}>
                    <label htmlFor="chk" aria-hidden="true" onClick={toggleForm}>
                        Sign up
                    </label>
                    <input type="text" name="username" placeholder="Username" required />
                    <input type="email" name="email" placeholder="Email" required />
                    <input type="password" name="password" placeholder="Password" required />
                    <button type="submit">Sign up</button>
                </form>
            </div>

            <div className={`login ${isLoginVisible ? 'active' : ''}`}>
                <form id="login-form" onSubmit={handleLoginSubmit}>
                    <label htmlFor="chk" aria-hidden="true" onClick={toggleForm}>
                        Login
                    </label>
                    <input type="email" name="email" placeholder="Email" required />
                    <input type="password" name="password" placeholder="Password" required />
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

AuthForms.propTypes = {
    isLoginVisible: PropTypes.bool.isRequired,
    toggleForm: PropTypes.func.isRequired,
    onSignup: PropTypes.func.isRequired,
    onLogin: PropTypes.func.isRequired
};