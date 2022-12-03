package uk.ac.tees.b1515396.teezapplication.api;

public class CourseModel {

    //Courses variables
    private String courseName;
    private String courseImg;
    private String courseMode;
    private String courseTracks;

    // Create constructor
    public CourseModel(String courseName, String courseImg, String courseMode, String courseTracks) {
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.courseMode = courseMode;
        this.courseTracks = courseTracks;
    }

    // Create Get and Setter Class.

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseMode() {
        return courseMode;
    }

    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }

    public String getCourseTracks() {
        return courseTracks;
    }

    public void setCourseTracks(String courseTracks) {
        this.courseTracks = courseTracks;
    }
}
