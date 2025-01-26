import {ThemeManager} from "./ThemeManager.js";

export class UIManager {
    static initializeUI() {
        new ThemeManager();
    }

    static setUi(userData) {
        if(userData) {
            document.getElementById("form-container").style.display = "none";
            document.getElementById("account-info").style.display = "block";
            document.getElementById("username").textContent = userData.username || 'пользователь';
            document.getElementById("user-email").textContent = userData.email;
        } else {
            document.getElementById("form-container").style.display = "block";
            document.getElementById("account-info").style.display = "none";
        }
    }
}