export class ThemeManager {
    constructor() {
        this.isDarkTheme = localStorage.getItem('isDarkTheme') === 'true';
        this.checkbox = document.querySelector('.theme-toggle');
        this.checkbox.checked = this.isDarkTheme;
        this.checkbox.addEventListener('change', () => this.toggleTheme());
        this.updateTheme();
    }

    updateTheme() {
        const body = document.body;
        if (this.isDarkTheme) {
            body.classList.add('dark-theme');
            body.classList.remove('light-theme');
        } else {
            body.classList.add('light-theme');
            body.classList.remove('dark-theme');
        }
    }

    toggleTheme() {
        this.isDarkTheme = !this.isDarkTheme;
        localStorage.setItem('isDarkTheme', this.isDarkTheme);
        this.updateTheme();
    }
}