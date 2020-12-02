package com.example.ca2_ella_burke;

public class note {

    private String tagText;
    private String dateText;
    private String noteText;
    private String userId;

    public note() {
        tagText = null;
        dateText = null;
        noteText = null;
    }

//    public note(String text1, String text2, String text3, String userId) {
//
//        tagText = text1;
//        dateText = text2;
//        noteText = text3;
//        this.userId = userId;
//    }

    public note(String tagText, String dateText, String noteText, String userId) {
        this.tagText = tagText;
        this.dateText = dateText;
        this.noteText = noteText;
        this.userId = userId;
    }

    public String getTagText() {
        return tagText;
    }

    public String getDateText() {
        return dateText;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getUserId() {
        return userId;
    }


    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "note{" +
                "tagText='" + tagText + '\'' +
                ", dateText='" + dateText + '\'' +
                ", noteText='" + noteText + '\'' +
                '}';
    }
}
