package com.example.anhvt86_3.data.datasource.remote.response;

import com.example.anhvt86_3.domain.model.CastMember;
import com.example.anhvt86_3.domain.model.CrewMember;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsResponse {
    @SerializedName("cast")
    private List<CastMember> cast;

    @SerializedName("crew")
    private List<CrewMember> crew;

    public List<CastMember> getCast() {
        return cast;
    }

    public void setCast(List<CastMember> cast) {
        this.cast = cast;
    }

    public List<CrewMember> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewMember> crew) {
        this.crew = crew;
    }
}
