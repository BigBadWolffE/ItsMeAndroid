package com.indocyber.itsmeandroid.model;

/*
 *
 *
 *@Author
 *@Version
 */
public class Billing extends Notification {

    private String attachmentName;
    private String attachmentPassword;

    public Billing(int id, String title, String body, String date, int status,
                   String attachmentName, String attachmentPassword) {
        super(id, title, body, date, status);
        this.attachmentName = attachmentName;
        this.attachmentPassword = attachmentPassword;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentPassword() {
        return attachmentPassword;
    }

    public void setAttachmentPassword(String attachmentPassword) {
        this.attachmentPassword = attachmentPassword;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Billing{" +
                "attachmentName='" + attachmentName + '\'' +
                ", attachmentPassword='" + attachmentPassword + '\'' +
                '}';
    }
}
