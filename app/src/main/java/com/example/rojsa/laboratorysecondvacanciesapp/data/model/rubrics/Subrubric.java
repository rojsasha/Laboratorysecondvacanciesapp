
package com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subrubric implements Parcelable {

    @SerializedName("subrubric_id")
    @Expose
    private String subrubricId;
    @SerializedName("subrubric")
    @Expose
    public String subrubric;
    @SerializedName("count_vac_subrub")
    @Expose
    private String countVacSubrub;
    @SerializedName("count_new_vac_subrub")
    @Expose
    private String countNewVacSubrub;

    protected Subrubric(Parcel in) {
        subrubricId = in.readString();
        subrubric = in.readString();
        countVacSubrub = in.readString();
        countNewVacSubrub = in.readString();
    }

    public static final Creator<Subrubric> CREATOR = new Creator<Subrubric>() {
        @Override
        public Subrubric createFromParcel(Parcel in) {
            return new Subrubric(in);
        }

        @Override
        public Subrubric[] newArray(int size) {
            return new Subrubric[size];
        }
    };

    public String getSubrubricId() {
        return subrubricId;
    }

    public void setSubrubricId(String subrubricId) {
        this.subrubricId = subrubricId;
    }

    public String getSubrubric() {
        return subrubric;
    }

    public void setSubrubric(String subrubric) {
        this.subrubric = subrubric;
    }

    public String getCountVacSubrub() {
        return countVacSubrub;
    }

    public void setCountVacSubrub(String countVacSubrub) {
        this.countVacSubrub = countVacSubrub;
    }

    public String getCountNewVacSubrub() {
        return countNewVacSubrub;
    }

    public void setCountNewVacSubrub(String countNewVacSubrub) {
        this.countNewVacSubrub = countNewVacSubrub;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subrubricId);
        parcel.writeString(subrubric);
        parcel.writeString(countVacSubrub);
        parcel.writeString(countNewVacSubrub);
    }
}
