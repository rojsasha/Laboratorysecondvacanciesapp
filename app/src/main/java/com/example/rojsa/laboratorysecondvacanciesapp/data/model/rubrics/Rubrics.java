
package com.example.rojsa.laboratorysecondvacanciesapp.data.model.rubrics;

import android.os.Parcel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

public class Rubrics extends ExpandableGroup<Subrubric> {
    public Rubrics(List<Subrubric> items, String title) {
        super(title, items);
        this.rubric = title;
        this.subrubrics = items;
    }

    @SerializedName("rubric")
    @Expose
    private String rubric;
    @SerializedName("rubric_id")
    @Expose
    private String rubricId;
    @SerializedName("count_of_vac")
    @Expose
    private String countOfVac;
    @SerializedName("count_of_new_vac")
    @Expose
    private String countOfNewVac;
    @SerializedName("subrubrics")
    @Expose
    private List<Subrubric> subrubrics = null;

    public Rubrics(String title, List<Subrubric> items) {
        super(title, items);
    }

    protected Rubrics(Parcel in) {
        super(in);
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public String getRubricId() {
        return rubricId;
    }

    public void setRubricId(String rubricId) {
        this.rubricId = rubricId;
    }

    public String getCountOfVac() {
        return countOfVac;
    }

    public void setCountOfVac(String countOfVac) {
        this.countOfVac = countOfVac;
    }

    public String getCountOfNewVac() {
        return countOfNewVac;
    }

    public void setCountOfNewVac(String countOfNewVac) {
        this.countOfNewVac = countOfNewVac;
    }

    public List<Subrubric> getSubrubrics() {
        return subrubrics;
    }

    public void setSubrubrics(List<Subrubric> subrubrics) {
        this.subrubrics = subrubrics;
    }

}
