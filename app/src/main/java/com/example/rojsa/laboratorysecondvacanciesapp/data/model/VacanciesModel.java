
package com.example.rojsa.laboratorysecondvacanciesapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VacanciesModel implements Parcelable {

    public VacanciesModel() {
    }

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("header")
    @Expose
    private String header;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("site_address")
    @Expose
    private String siteAddress;
    @SerializedName("salary1")
    @Expose
    private String salary1;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("count1")
    @Expose
    private Object count1;
    @SerializedName("image_src")
    @Expose
    private Object imageSrc;
    @SerializedName("raiting")
    @Expose
    private Integer raiting;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("post_created")
    @Expose
    private String postCreated;
    @SerializedName("au_post_id")
    @Expose
    private Object auPostId;
    @SerializedName("paid")
    @Expose
    private String paid;

    protected VacanciesModel(Parcel in) {
        pid = in.readString();
        header = in.readString();
        profile = in.readString();
        salary = in.readString();
        telephone = in.readString();
        data = in.readString();
        profession = in.readString();
        siteAddress = in.readString();
        salary1 = in.readString();
        link = in.readString();
        body = in.readString();
        if (in.readByte() == 0) {
            raiting = null;
        } else {
            raiting = in.readInt();
        }
        updateDate = in.readString();
        term = in.readString();
        postCreated = in.readString();
        paid = in.readString();
    }

    public static final Creator<VacanciesModel> CREATOR = new Creator<VacanciesModel>() {
        @Override
        public VacanciesModel createFromParcel(Parcel in) {
            return new VacanciesModel(in);
        }

        @Override
        public VacanciesModel[] newArray(int size) {
            return new VacanciesModel[size];
        }
    };

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getSalary1() {
        return salary1;
    }

    public void setSalary1(String salary1) {
        this.salary1 = salary1;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getCount1() {
        return count1;
    }

    public void setCount1(Object count1) {
        this.count1 = count1;
    }

    public Object getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Object imageSrc) {
        this.imageSrc = imageSrc;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPostCreated() {
        return postCreated;
    }

    public void setPostCreated(String postCreated) {
        this.postCreated = postCreated;
    }

    public Object getAuPostId() {
        return auPostId;
    }

    public void setAuPostId(Object auPostId) {
        this.auPostId = auPostId;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pid);
        parcel.writeString(header);
        parcel.writeString(profile);
        parcel.writeString(salary);
        parcel.writeString(telephone);
        parcel.writeString(data);
        parcel.writeString(profession);
        parcel.writeString(siteAddress);
        parcel.writeString(salary1);
        parcel.writeString(link);
        parcel.writeString(body);
        if (raiting == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(raiting);
        }
        parcel.writeString(updateDate);
        parcel.writeString(term);
        parcel.writeString(postCreated);
        parcel.writeString(paid);
    }
}
