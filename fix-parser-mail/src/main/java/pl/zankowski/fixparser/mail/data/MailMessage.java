package pl.zankowski.fixparser.mail.data;

public class MailMessage {

    private final String recipient;
    private final String subject;
    private final String text;

    public MailMessage(final String recipient, final String subject, final String text) {
        this.recipient = recipient;
        this.subject = subject;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailMessage that = (MailMessage) o;

        if (getRecipient() != null ? !getRecipient().equals(that.getRecipient()) : that.getRecipient() != null)
            return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        return getText() != null ? getText().equals(that.getText()) : that.getText() == null;

    }

    @Override
    public int hashCode() {
        int result = getRecipient() != null ? getRecipient().hashCode() : 0;
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        return result;
    }

    public static class Builder {

        private String recipient = "";
        private String subject = "";
        private String text = "";

        public Builder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public MailMessage build() {
            return new MailMessage(recipient, subject, text);
        }

    }
}
