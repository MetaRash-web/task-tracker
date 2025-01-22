export class ModalManager {
    static toggleModal(elementId) {
        const element = document.getElementById(elementId);
        if (element) {
            element.style.display = element.style.display === 'block' || element.style.display === 'flex' ? 'none' : 'block';
        }
    }
}