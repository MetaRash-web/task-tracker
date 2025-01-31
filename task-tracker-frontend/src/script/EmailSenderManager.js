
export class EmailSenderManager {
    async sendEmail(event) {
        event.preventDefault();
        console.log('token: ' + localStorage.getItem("accessToken"))
        try {
            const response = await fetch('/send-email/send', {
                method: 'POST',
                headers: { 'Authorization': `Bearer ${localStorage.getItem("accessToken")}` },
                body: JSON.stringify({
                    recipient: "test@example.com",
                    subject: "Test email",
                    text: "This is a test email."
                })
            });
            console.log(response.text())
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }
}