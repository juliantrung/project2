package pro.trung.findjob.model;



/**
 * Created by Trung on 25/03/2017.
 */

public class Job {
    private String image;
    private String jobName;
    private String salary;
    private String company;
    private String address;
    private String tags;
    private String link;
    private Boolean isCheck;


    public Job() {
    }

    public Job(String jobName, String salary, String company, String address, String tags) {
        this.jobName = jobName;
        this.salary = salary;
        this.company = company;
        this.address = address;
        this.tags = tags;
        this.isCheck = false;
    }

    public Job(String jobName, String salary, String company, String address, String tags, String link) {
        this.jobName = jobName;
        this.salary = salary;
        this.company = company;
        this.address = address;
        this.tags = tags;
        this.link = link;
        this.isCheck = false;
    }

    public Job(String image, String jobName, String salary, String company, String address, String tags, Boolean isCheck) {
        this.image = image;
        this.jobName = jobName;
        this.salary = salary;
        this.company = company;
        this.address = address;
        this.tags = tags;
        this.isCheck = false;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
